package it.diamonds.tests;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.tests.helper.ComponentHelperForTest.cloneRegion;
import static it.diamonds.tests.helper.ComponentHelperForTest.getPivotStartingColumn;
import static it.diamonds.tests.helper.ComponentHelperForTest.getPivotStartingRow;
import static it.diamonds.tests.helper.ComponentHelperForTest.getSlaveStartingColumn;
import static it.diamonds.tests.helper.ComponentHelperForTest.getSlaveStartingRow;
import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.LayerManager;
import it.diamonds.grid.Region;
import it.diamonds.gui.GameOverBox;

import java.io.IOException;


public class TestGameOnGameOver extends GridTestCase
{

    private GameLoop loop;

    private PlayField field1;

    private PlayField field2;

    private LayerManager layerManager;

    private int restartGameDelay;

    private Image gameOverImage;


    public void setUp()
    {
        super.setUp();

        loop = new GameLoop(environment);

        field1 = loop.getPlayFieldOne();
        field2 = loop.getPlayFieldTwo();
        layerManager = loop.getLayerManager();

        controller = field1.getGridController();
        grid = controller.getGrid();
        setDiamondsGemsPair(grid, controller.getGemsPair());

        this.restartGameDelay = environment.getConfig().getInteger("RestartGameDelay");

        gameOverImage = environment.getEngine().createImage(GameOverBox.TEXTURE_PATH);
    }


    public void testPassToGameOverOnColumnFull() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();
        assertTrue(loop.getPlayFieldOne().getGridController().isGameOver());
    }


    public void testPassToGameOverNotRestartGame() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        assertEquals(layerManager, loop.getLayerManager());
        assertEquals(field1, loop.getPlayFieldOne());
        assertEquals(field2, loop.getPlayFieldTwo());
    }


    public void testDelayBeforeRestart() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        environment.getTimer().advance(restartGameDelay - 2);
        loop.loopStep();

        assertEquals(layerManager, loop.getLayerManager());
        assertEquals(field1, loop.getPlayFieldOne());
        assertEquals(field2, loop.getPlayFieldTwo());
    }


    public void testRestartAfterDelay() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        environment.getTimer().advance(restartGameDelay);
        loop.loopStep();

        assertNotSame("The layer must be different from the old one", layerManager, loop.getLayerManager());
        assertNotSame("field1 must be different from the old one", field1, loop.getPlayFieldOne());
        assertNotSame("field2 must be different from the old one", field2, loop.getPlayFieldTwo());
    }


    public void testNotShowGameOverImageBeforeGameOver() throws IOException
    {
        assertFalse(mockEngine.wasImageDrawn(gameOverImage));
    }


    public void testShowGameOverImageOnGameOver() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        assertTrue(mockEngine.wasImageDrawn(gameOverImage));
    }


    public void testNotShowGameOverAfterRestart() throws IOException
    {
        fillFourthColumn();
        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        environment.getTimer().advance(restartGameDelay);
        loop.loopStep();

        assertFalse(mockEngine.wasImageDrawn(gameOverImage));
    }


    // TODO This test check a behavior not necessary for the game. Remove it?
    public void testGemsPairOnFieldTwoMoveWhenGameOverOccurs() throws IOException
    {
        Droppable slaveGem = field2.getGridController().getGemsPair().getSlave();
        Droppable pivotGem = field2.getGridController().getGemsPair().getPivot();

        fillFourthColumn();

        Region slaveNextRegion = cloneRegion(slaveGem.getRegion(), 0, 1);
        Region pivotNextRegion = cloneRegion(pivotGem.getRegion(), 0, 1);

        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        assertEquals(field2.getGridController().getGemsPair().getSlave().getRegion(), slaveNextRegion);
        assertEquals(field2.getGridController().getGemsPair().getPivot().getRegion(), pivotNextRegion);
    }


    public void testGemsPairOnFieldTwoNotMoveOnWaitRestart() throws IOException
    {
        Droppable slaveGem = field2.getGridController().getGemsPair().getSlave();
        Droppable pivotGem = field2.getGridController().getGemsPair().getPivot();

        fillFourthColumn();

        loop.getPlayerTwoInput().notify(Event.create(Code.RIGHT, State.PRESSED));
        loop.getPlayerTwoInput().notify(Event.create(Code.DOWN, State.PRESSED));
        loop.getPlayerTwoInput().notify(Event.create(Code.BUTTON1, State.PRESSED));

        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        Region slaveRegion = cloneRegion(slaveGem.getRegion());
        Region pivotRegion = cloneRegion(pivotGem.getRegion());

        environment.getTimer().advance(restartGameDelay - 1);
        loop.loopStep();

        assertEquals(slaveGem.getRegion(), slaveRegion);
        assertEquals(pivotGem.getRegion(), pivotRegion);
    }


    public void testControllerDontReactForKeyPressedOnDelay() throws IOException
    {

        fillFourthColumn();

        loop.getPlayerTwoInput().notify(Event.create(Code.RIGHT, State.PRESSED));
        loop.getPlayerTwoInput().notify(Event.create(Code.DOWN, State.PRESSED));
        loop.getPlayerTwoInput().notify(Event.create(Code.BUTTON1, State.PRESSED));

        environment.getTimer().advance(getNewGemDelay());
        loop.loopStep();

        environment.getTimer().advance(restartGameDelay);
        loop.loopStep();

        environment.getTimer().advance(getInputRate());
        loop.loopStep();

        DroppablePair newGemsPairOne = loop.getPlayFieldOne().getGridController().getGemsPair();
        DroppablePair newGemsPairTwo = loop.getPlayFieldTwo().getGridController().getGemsPair();

        Region pivotStartRegion = new Region(getPivotStartingColumn(), getPivotStartingRow() + 1, 1, 1);
        Region slaveStartRegion = new Region(getSlaveStartingColumn(), getSlaveStartingRow() + 1, 1, 1);

        assertEquals("New pivot of field One is misplaced", pivotStartRegion, newGemsPairOne.getPivot().getRegion());
        assertEquals("New slave of field One is misplaced", slaveStartRegion, newGemsPairOne.getSlave().getRegion());

        assertEquals("New pivot of field Two is misplaced", pivotStartRegion, newGemsPairTwo.getPivot().getRegion());
        assertEquals("New slave of field Two is misplaced", slaveStartRegion, newGemsPairTwo.getSlave().getRegion());
    }


    private void fillFourthColumn()
    {
        makeAllGemsFall();
        for (int row = 11; row >= 0; row--)
        {
            insertAndUpdate(createGem(DIAMOND), row, 4);
        }
    }

}
