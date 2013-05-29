package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.PlayField;
import it.diamonds.PlayFieldDescriptor;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.engine.video.LayerManager;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockRandomGenerator;


public class TestPlayField extends GridTestCase
{
    private static final int MOCK_SEQUENCE[] = { 99, 0, 99, 0 };

    private PlayField playField;

    private Input input;

    private Droppable pivot;

    private ScoreCalculator scoreCalculator;

    private int updateRate;


    public void setUp()
    {
        super.setUp();

        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());

        scoreCalculator = new ScoreCalculator(environment.getConfig().getInteger("BonusPercentage"));

        InputReactor inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        playField = PlayField.createPlayField(environment, inputReactor, new MockRandomGenerator(MOCK_SEQUENCE), PlayFieldDescriptor.createForPlayerOne());

        pivot = playField.getGridController().getGemsPair().getPivot();
        updateRate = environment.getConfig().getInteger("UpdateRate");
    }


    public void testGridCreation()
    {
        Droppable slave = playField.getGridController().getGemsPair().getPivot();

        assertEquals("pivot gem must be a diamond", DroppableColor.DIAMOND, pivot.getColor());
        assertEquals("pivot must be a gem", DroppableType.GEM, pivot.getType());

        assertEquals("slave gem must be a diamond", DroppableColor.DIAMOND, slave.getColor());
        assertEquals("pivot must be a gem", DroppableType.GEM, slave.getType());
    }


    public void testNoInputReactionOnTimingNotElapsed()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));

        environment.getTimer().advance(getInputRate() - 1);
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem moved", 4, pivot.getRegion().getLeftColumn());
    }


    public void testInputReactionOnTimingElapsed()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem did not move", 3, pivot.getRegion().getLeftColumn());
    }


    public void testNoSecondInputReactionOnTimingNotElapsed()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate() - 1);
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem must be on column 3", 3, pivot.getRegion().getLeftColumn());
    }


    public void testSecondInputReactionOnTimingElapsed()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem must be on column 2", 2, pivot.getRegion().getLeftColumn());
    }


    public void testDoubleInputReactionOnTwoTimingElapsed()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate() * 2);
        playField.reactToInput(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem must be on column 1", 1, pivot.getRegion().getLeftColumn());
    }


    public void testNoUpdateOnTimingNotElapsed()
    {
        float oldY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        environment.getTimer().advance(updateRate - 1);
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        assertFalse("gem moved", oldY < newY);
    }


    public void testUpdateOnTimingElapsed()
    {
        float oldY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        assertTrue("gem did not move", oldY < newY);
    }


    public void testNoSecondUpdateOnTimingNotElapsed()
    {
        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        float oldY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        environment.getTimer().advance(updateRate - 1);
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        assertFalse("gem moved", oldY < newY);
    }


    public void testSecondUpdateOnTimingElapsed()
    {
        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        float oldY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        assertTrue("gem did not move", oldY < newY);
    }


    public void testDoubleUpdateOnTwoTimingElapsed()
    {
        float oldY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        float yStep = oldY
            - playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();
        oldY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        environment.getTimer().advance(updateRate * 2);
        playField.update(environment.getTimer().getTime());
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getPositionInGridLocalSpace().getY();

        assertEquals("gem did not move two times", 2 * yStep, oldY - newY, 0.001);
    }


    public void testInsertionInLayerManager()
    {
        LayerManager layerManager = new LayerManager();

        playField.fillLayerManager(layerManager);

        assertEquals(7, layerManager.getLayersCount());
    }


    public void testGameOverMessageIsDrawn()
    {
        LayerManager layerManager = new LayerManager();

        playField.fillLayerManager(layerManager);

        layerManager.drawLayers(environment.getEngine());
        int numberOfQuadsDrawn = ((MockEngine)environment.getEngine()).getNumberOfQuadsDrawn();

        playField.showGameOverMessage();

        environment.getEngine().clearDisplay();
        layerManager.drawLayers(environment.getEngine());

        assertEquals("Game Over Box must be drawn", numberOfQuadsDrawn + 1, ((MockEngine)environment.getEngine()).getNumberOfQuadsDrawn());
    }


    public void testGameOverMessageIsDrawnOnce()
    {
        LayerManager layerManager = new LayerManager();

        playField.fillLayerManager(layerManager);

        layerManager.drawLayers(environment.getEngine());
        int numberOfQuadsDrawn = ((MockEngine)environment.getEngine()).getNumberOfQuadsDrawn();

        playField.showGameOverMessage();
        playField.showGameOverMessage();

        environment.getEngine().clearDisplay();
        layerManager.drawLayers(environment.getEngine());

        assertEquals("Game Over Box must be drawn", numberOfQuadsDrawn + 1, ((MockEngine)environment.getEngine()).getNumberOfQuadsDrawn());
    }


    public void testScoreChange()
    {
        Grid grid = playField.getGridController().getGridRenderer().getGrid();
        int initialScore = scoreCalculator.getScore();

        Droppable drop = new Gem(environment.getEngine(), DroppableColor.DIAMOND, 3500, 0);
        drop.drop();
        Cell cell = Cell.create(13, 3);
        drop.getRegion().setRow(cell.getRow());
        drop.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(drop);

        drop = new Chest(environment.getEngine(), DroppableColor.DIAMOND, 3500, 0);
        drop.drop();
        Cell cell1 = Cell.create(13, 2);
        drop.getRegion().setRow(cell1.getRow());
        drop.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(drop);

        grid.updateCrushes(scoreCalculator, stoneCalculator);
        scoreCalculator.closeChain();

        assertTrue("Total score was not correct", initialScore < scoreCalculator.getScore());
    }


    public void testTimeStampsResetOnUpdate()
    {
        environment.getTimer().advance(updateRate);
        playField.update(environment.getTimer().getTime());

        environment.getTimer().advance(1);
        playField.resetTimeStamps(environment.getTimer().getTime());

        float oldY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        environment.getTimer().advance(updateRate - 1);
        playField.update(environment.getTimer().getTime());

        float newY = playField.getGridController().getGemsPair().getPivot().getAnimatedSprite().getSprite().getPosition().getY();

        assertEquals("gem moved", oldY, newY, 0.001);
    }


    public void testTimeStampsResetOnInputReaction()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(1);
        playField.resetTimeStamps(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        environment.getTimer().advance(getInputRate() - 1);
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("gem must be on column 3", 3, pivot.getRegion().getLeftColumn());
    }


    public void testInputServedInitialization()
    {
        assertEquals("inputServed must be 0", 0, playField.wasInputServed());
    }


    public void testInputServedAfterFirstInputReaction()
    {
        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("inputServed must be 1", 1, playField.wasInputServed());
    }


    public void testInputServedBeforeFirstInputReaction()
    {
        environment.getTimer().advance(getInputRate() - 1);
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("inputServed must be 0", 0, playField.wasInputServed());
    }


    public void testInputServedAfterSecondInputReaction()
    {
        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(getInputRate());
        playField.reactToInput(environment.getTimer().getTime());

        assertEquals("inputServed must be 2", 2, playField.wasInputServed());
    }


    public void testSetControllerIncomingStones()
    {
        playField.addIncomingStones(5);
        assertEquals(5, playField.getGridController().getIncomingStones());
    }


    public void testAdditiveIncomingStones()
    {
        playField.addIncomingStones(5);
        assertEquals(5, playField.getGridController().getIncomingStones());
        playField.addIncomingStones(5);
        assertEquals(10, playField.getGridController().getIncomingStones());
    }


    public void testLimitIncomingStonesTo99()
    {
        playField.addIncomingStones(273);
        assertEquals("Always limit the number of the incoming stones to a maximum of 99", 99, playField.getGridController().getIncomingStones());
    }
}
