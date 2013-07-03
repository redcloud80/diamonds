package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GemsPairOnControlState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestGemsPairOnControlState extends GridTestCase
{
    private AbstractControllerState state;

    private GameLoop loop;

    private int strongestGravity;


    public void setUp()
    {
        super.setUp();

        loop = new GameLoop(environment);
        PlayField field = loop.getPlayFieldOne();
        controller = field.getGridController();
        state = new GemsPairOnControlState(environment);
        grid = controller.getGrid();

        controller.getGemsPair().getPivot().drop();
        controller.getGemsPair().getSlave().drop();
        grid.removeDroppable(grid.getDroppableAt(Cell.create(0, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(1, 4)));

        strongestGravity = getDeltaYStrongestGravity();
    }


    public void testCurrentStateWrongName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testCurrentStateRightName()
    {
        assertTrue(state.isCurrentState("GemsPairOnControl"));
    }


    public void testBothGemsAreFalling()
    {
        controller.insertNewGemsPair();

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);

        assertTrue(state.isCurrentState("GemsPairOnControl"));
        assertEquals(grid.getActualGravity(), getDeltaYGravity());
    }


    public void testOneGemsAreFalling()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 1);

        setGemsPair(createGem(DIAMOND), 11, 1, createGem(DIAMOND), 11, 2);

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);

        assertTrue(state.isCurrentState("GemsPairOnControl"));
        assertEquals(grid.getActualGravity(), strongestGravity);
    }


    public void testBothGemsAreNotFalling()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 1);

        setGemsPair(createGem(DIAMOND), 11, 1, createGem(DIAMOND), 10, 1);

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), stoneCalculator);

        assertTrue(state.isCurrentState("WaitBeforeNewGemsPair"));
    }


    public void testThereAreSomeStone()
    {
        Droppable stone = createStone(DIAMOND);
        stone.getAnimatedSprite().setCurrentFrame(6);
        insertAndUpdate(stone, 13, 2);

        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 1);

        setGemsPair(createGem(DIAMOND), 11, 1, createGem(DIAMOND), 10, 1);

        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, null);

        assertTrue(state.isCurrentState("StoneTurn"));
    }


    private void setGemsPair(Gem gemPivot, int rowPivot, int columnPivot, Gem gemSlave, int rowSlave, int columnSlave)
    {
        DroppablePair gemsPair = controller.getGemsPair();
        insertAndUpdate(gemPivot, rowPivot, columnPivot);
        insertAndUpdate(gemSlave, rowSlave, columnSlave);
        gemsPair.setSlave(gemSlave);
        gemsPair.setPivot(gemPivot);
    }


    public void testThisStateIsReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertTrue(input.hasReacted());
    }


    public void testChainCounterIsResetWhenUpdating()
    {
        state.update(environment.getTimer().getTime(), controller, scoreCalculator, stoneCalculator);

        assertEquals(0, scoreCalculator.getChainCounter());
    }
}
