package it.diamonds.tests.grid.state;


import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.WaitNextCrushState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;
import it.diamonds.tests.mocks.MockTimer;


public class TestWaitNextCrushState extends GridTestCase
{
    private AbstractControllerState state;


    public void setUp()
    {
        super.setUp();

        GameLoop loop = new GameLoop(environment);
        PlayField field = loop.getPlayFieldOne();
        controller = field.getGridController();
        grid = controller.getGrid();
        state = new WaitNextCrushState(environment, environment.getTimer().getTime());
    }


    public void testCurrentStateWrongName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testCurrentStateRightName()
    {
        assertTrue(state.isCurrentState("WaitNextCrush"));
    }


    public void testInitState()
    {
        int normalGravity = getDeltaYGravity();
        for (int t = 1; t <= getDelayBetweenCrushes(); t++)
        {
            assertEquals("not correct gravity", normalGravity, grid.getActualGravity());
            assertTrue(state.isCurrentState("WaitNextCrush"));

            environment.getTimer().advance(1);
            state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, stoneCalculator);
        }
        assertFalse(state.isCurrentState("WaitNextCrush"));
    }


    public void testInitState2()
    {
        state = new WaitNextCrushState(environment, 12345);
        ((MockTimer)environment.getTimer()).setTime(0);

        environment.getTimer().advance(12345 + getDelayBetweenCrushes() - 1);
        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, stoneCalculator);
        assertTrue(state.isCurrentState("WaitNextCrush"));

        environment.getTimer().advance(1);
        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, stoneCalculator);
        assertFalse(state.isCurrentState("WaitNextCrush"));
    }


    public void testCrushAfterDelayBeforeNextCrush()
    {
        Droppable chest = createChest(DroppableColor.DIAMOND);
        chest.drop();
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(13, 4);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        Cell cell1 = Cell.create(12, 4);
        chest.getRegion().setRow(cell1.getRow());
        chest.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(chest);

        environment.getTimer().advance(getDelayBetweenCrushes());

        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, stoneCalculator);

        assertNull(grid.getDroppableAt(Cell.create(13, 4)));
        assertNull(grid.getDroppableAt(Cell.create(12, 4)));
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
