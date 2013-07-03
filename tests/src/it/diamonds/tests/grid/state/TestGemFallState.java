package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GemFallState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestGemFallState extends GridTestCase
{
    private AbstractControllerState state;

    private GameLoop loop;


    public void setUp()
    {
        super.setUp();

        loop = new GameLoop(environment);
        PlayField field = loop.getPlayFieldOne();
        controller = field.getGridController();
        state = new GemFallState(environment);
        grid = controller.getGrid();

        controller.getGemsPair().getPivot().drop();
        controller.getGemsPair().getSlave().drop();
        grid.removeDroppable(grid.getDroppableAt(Cell.create(0, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(1, 4)));
    }


    public void testCurrentStateWrongName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testCurrentStateRightName()
    {
        assertTrue(state.isCurrentState("GemFall"));
    }


    public void testDontReactToDownKeyDuringGemFallState()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        insertAndUpdate(gem, 0, 0);

        controller.update(environment.getTimer().getTime(), scoreCalculator);
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());

        loop.getPlayerOneInput().notify(Event.create(Code.DOWN, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());

        loop.getPlayerOneInput().notify(Event.create(Code.DOWN, State.RELEASED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }


    public void testDroppedGemCanMove()
    {
        Cell cell = Cell.create(6, 1);

        makeAllGemsFall();
        Droppable droppable = createGem(DIAMOND);
        Cell cell1 = Cell.create(5, 1);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        assertNull(grid.getDroppableAt(cell));
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertNotNull("Gem not fallDown", grid.getDroppableAt(cell));
    }


    public void testDroppedGemCantMoveReturnState()
    {
        makeAllGemsFall();
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertTrue(state.isCurrentState("WaitNextCrush"));
    }


    public void testDroppedGemCanMoveReturnState()
    {
        Cell cell = Cell.create(6, 1);

        makeAllGemsFall();
        Droppable droppable = createGem(DIAMOND);
        Cell cell1 = Cell.create(5, 1);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        assertNull(grid.getDroppableAt(cell));
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertTrue(state.isCurrentState("GemFall"));
        assertNotNull(grid.getDroppableAt(cell));
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
