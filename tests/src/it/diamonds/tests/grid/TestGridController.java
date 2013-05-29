package it.diamonds.tests.grid;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.GemQueue;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.grid.Cell;
import it.diamonds.grid.GridController;
import it.diamonds.renderer.GridRenderer;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockDroppableGenerator;
import it.diamonds.tests.mocks.MockRandomGenerator;
import it.diamonds.tests.mocks.MockTimer;

import java.io.IOException;


public class TestGridController extends GridTestCase
{

    private Droppable gem;

    private Input input;

    private InputReactor inputReactor;

    private DroppablePair gemsPair;

    private MockDroppableGenerator gemGenerator;


    public void setUp()
    {
        super.setUp();

        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());
        inputReactor = new InputReactor(input, 150, 50);

        gemGenerator = new MockDroppableGenerator(environment.getEngine());
        controller = new GridController(environment, gridRenderer, inputReactor, gemGenerator);

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());
        gemsPair = controller.getGemsPair();
        gemsPair.setNoPivot();
        gemsPair.setNoSlave();

        gem = createGem(DroppableColor.DIAMOND);
    }


    private void fillColumn(int column)
    {
        for (int i = 0; i < 14; i++)
        {
            Droppable newGem = createGem(DroppableColor.DIAMOND);
            Cell cell = Cell.create(i, column);
            newGem.getRegion().setRow(cell.getRow());
            newGem.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(newGem);
            gemsPair.setPivot(newGem);

            newGem.drop();
        }
    }


    public void testGemGeneratorCannotBeNull()
    {
        assertNotNull(controller.getGemGenerator());
    }


    public void testGetGemsPair()
    {
        assertNotNull(controller.getGemsPair());
    }


    public void testGridDoesntInsertNewGems()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        grid.updateDroppable(gem);
        assertEquals(gem, gemsPair.getPivot());
    }


    public void testGridControllerUpdateGrid()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertEquals(13, gem.getRegion().getTopRow());
    }


    public void testNewGemsPairInsertion()
    {

        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNotNull(gemsPair.getPivot());
        assertTrue(gem != gemsPair.getPivot());
    }


    public void testNullGemUnderControlWhileWaiting()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull("gem under control is not null", gemsPair.getPivot());
    }


    public void testGemInsertedAfterTimeout()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        ((MockTimer)environment.getTimer()).setTime(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue(gem != gemsPair.getPivot());
    }


    public void testWaitBeforeInsertionNewGemWithTimeBaseNotZero()
    {
        ((MockTimer)environment.getTimer()).setTime(150);
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay() - 1);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertNull(gemsPair.getPivot());
    }


    public void testAfterSecondDelay()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        gem = gemsPair.getPivot();
        gem.drop();
        grid.removeDroppable(gemsPair.getSlave());
        gemsPair.setNoSlave();

        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNotNull("Grid didn't create a new gem", gemsPair.getPivot());
        assertTrue("Created gem must be a new one", gem != gemsPair.getPivot());
    }


    public void testInputWhileWaiting()
    {

        Cell cell = Cell.create(13, 4);
        Cell cell1 = Cell.create(13, 4);

        gem.getRegion().setRow(cell1.getRow());
        gem.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue((!grid.isCellFree(cell)));

        input.notify(Event.create(Code.RIGHT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue((!grid.isCellFree(cell)));
    }


    public void testQueueNotEmptyBeforeGenerateNewGem()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        ((MockTimer)environment.getTimer()).setTime(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue(!input.isEmpty());
    }


    public void testGridNotGameOver()
    {
        assertFalse(grid.isColumnFull(4));
    }


    public void testGridGameOver()
    {
        fillColumn(4);
        assertTrue(grid.isColumnFull(4));
    }


    public void testIsNotGameOver()
    {
        assertFalse(controller.isGameOver());
    }


    public void testIsGameOver()
    {
        fillColumn(4);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        ((MockTimer)environment.getTimer()).setTime(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue(controller.isGameOver());
    }


    public void testIsNotGameOverOnSingleGemCrush()
    {
        for (int i = 2; i < 14; i++)
        {
            Droppable newGem = createGem(EMERALD);
            Cell cell = Cell.create(i, 4);
            newGem.getRegion().setRow(cell.getRow());
            newGem.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(newGem);
            gemsPair.setPivot(newGem);

            newGem.drop();
        }
        Droppable newGem = createChest(DIAMOND);
        Cell cell = Cell.create(1, 4);
        newGem.getRegion().setRow(cell.getRow());
        newGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(newGem);
        gemsPair.setPivot(newGem);

        newGem.drop();
        controller.insertNewGemsPair(grid);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertFalse("Not here in game over", controller.isGameOver());
    }


    public void testNotGameOverInCrushState()
    {

        for (int i = 1; i < 14; i++)

        {

            Droppable newGem = createGem(DroppableColor.DIAMOND);
            Cell cell = Cell.create(i, 4);

            newGem.getRegion().setRow(cell.getRow());
            newGem.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(newGem);

            gemsPair.setPivot(newGem);

            newGem.drop();

        }

        Droppable chest = createChest(DIAMOND);
        Cell cell = Cell.create(0, 4);

        chest.getRegion().setRow(cell.getRow());
        chest.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(chest);

        grid.updateDroppable(chest);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertFalse("Not here in game over", controller.isGameOver());

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        ((MockTimer)environment.getTimer()).setTime(getNewGemDelay());

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertFalse("Not here in game over", controller.isGameOver());

    }


    public void testIsNotGameOverWithFallingGem()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        assertFalse(controller.isGameOver());
    }


    public void testControllerGameOver()
    {
        try
        {
            fillColumn(4);
            controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
            environment.getTimer().advance(300);
            controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        }
        catch (Exception e)
        {
            fail("GridController can't manage \"game over\" conditions");
        }
    }


    public void testInputMustBeProcessedWhileWaiting()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getNewGemDelay() - 100);

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals("InputReactor not called", environment.getTimer().getTime(), inputReactor.getLastInputTimeStamp());
    }


    public void testInputStateSavedWhileWaiting()
    {
        Cell cell = Cell.create(13, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.RIGHT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        int newGemColumn = gemsPair.getPivot().getRegion().getLeftColumn();

        input.notify(Event.create(Code.RIGHT, State.RELEASED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals("gem wasn't moved left", newGemColumn - 1, gemsPair.getPivot().getRegion().getLeftColumn());
    }


    public void testReactToInput()
    {
        Cell cell = Cell.create(11, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        controller.getGemsPair().setPivot(gem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        inputReactor.reactToInput(environment.getTimer().getTime());

        input.notify(Event.create(Code.LEFT, State.PRESSED));

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertFalse((!grid.isCellFree(Cell.create(12, 4))));
        assertTrue((!grid.isCellFree(Cell.create(12, 3))));
    }


    public void testHorizontalMove()
    {
        grid.setNormalGravity();
        Cell cell = Cell.create(11, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateDroppable(gem);
        inputReactor.reactToInput(environment.getTimer().getTime());

        float oldYPosition = gem.getPositionInGridLocalSpace().getY();
        input.notify(Event.create(Code.LEFT, State.PRESSED));

        grid.updateDroppable(gem);
        inputReactor.reactToInput(environment.getTimer().getTime());

        float newYposition = gem.getPositionInGridLocalSpace().getY();
        assertEquals(oldYPosition + (float)grid.getActualGravity(), newYposition, 0.0001f);
    }


    public void testNoWaitStateWhenPivotGemCollide()
    {
        gem.getRegion().setRow(3);
        gem.getRegion().setColumn(4);

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(2, 4);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        Droppable droppable1 = createGem(DroppableColor.DIAMOND);
        Cell cell2 = Cell.create(2, 5);
        droppable1.getRegion().setRow(cell2.getRow());
        droppable1.getRegion().setColumn(cell2.getColumn());

        grid.insertDroppable(droppable1);

        gemsPair.setPivot(grid.getDroppableAt(Cell.create(2, 5)));

        Droppable slaveGem = grid.getDroppableAt(Cell.create(2, 4));
        gemsPair.setSlave(slaveGem);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertSame(slaveGem, gemsPair.getSlave());
    }


    public void testNoWaitStateWhenSlaveGemCollide()
    {
        gem.getRegion().setRow(3);
        gem.getRegion().setColumn(4);

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);

        Cell cell1 = Cell.create(2, 4);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        Droppable droppable1 = createGem(DroppableColor.DIAMOND);
        Cell cell2 = Cell.create(2, 5);
        droppable1.getRegion().setRow(cell2.getRow());
        droppable1.getRegion().setColumn(cell2.getColumn());

        grid.insertDroppable(droppable1);

        Droppable pivotGem = grid.getDroppableAt(Cell.create(2, 4));
        gemsPair.setPivot(pivotGem);

        gemsPair.setSlave(grid.getDroppableAt(Cell.create(2, 5)));

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertSame(pivotGem, gemsPair.getPivot());
    }


    public void testSetIncomingStones()
    {
        controller.setIncomingStones(3);
        assertEquals(3, controller.getIncomingStones());
        controller.setIncomingStones(7);
        assertEquals(7, controller.getIncomingStones());
    }


    public void testStonesToSend()
    {
        controller.setStonesToSend(1);
        assertEquals("The number of stones to send must be 1", 1, controller.getStonesToSend());

        controller.setStonesToSend(0);
        assertEquals("The number of stones to send must be 0", 0, controller.getStonesToSend());
    }


    public void testCreate() throws IOException
    {
        int chestProb = environment.getConfig().getInteger("ChestProbability");
        int flashProb = environment.getConfig().getInteger("FlashProbability");

        int startChest = flashProb;
        int startGem = flashProb + chestProb;

        int mockValues[] = { startGem, 1, startChest, 1 };
        MockRandomGenerator randomGenerator = new MockRandomGenerator(mockValues);
        GridController controller1 = new GridController(environment, new GridRenderer(environment, new Point(0, 0), grid), inputReactor, GemQueue.create(environment, randomGenerator));

        controller = controller1;
        gemsPair = controller.getGemsPair();
        assertNotNull((Gem)gemsPair.getPivot());
        assertNotNull((Chest)gemsPair.getSlave());
    }
}
