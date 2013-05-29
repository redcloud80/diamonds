package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GemsPairOnControlState;
import it.diamonds.grid.state.StoneTurnState;
import it.diamonds.grid.state.WaitBeforeNewGemsPairState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestGemsPairOnControlState extends GridTestCase
{
    private AbstractControllerState state;

    private int strongestGravity;


    public void setUp()
    {
        super.setUp();

        state = new GemsPairOnControlState(environment);

        controller.getGemsPair().getPivot().drop();
        controller.getGemsPair().getSlave().drop();
        grid.removeDroppable(grid.getDroppableAt(Cell.create(0, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(1, 4)));

        strongestGravity = getDeltaYStrongestGravity();
    }


    public void testBothGemsAreFalling()
    {
        controller.insertNewGemsPair(grid);

        state = state.update(0, controller, new ScoreCalculator(0), null);

        assertNotNull((GemsPairOnControlState)state);
        assertEquals(grid.getActualGravity(), getDeltaYGravity());
    }


    public void testOneGemsAreFalling()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 1);

        setGemsPair(createGem(DIAMOND), 11, 1, createGem(DIAMOND), 11, 2);

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);

        assertNotNull((GemsPairOnControlState)state);
        assertEquals(grid.getActualGravity(), strongestGravity);
    }


    public void testBothGemsAreNotFalling()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 1);

        setGemsPair(createGem(DIAMOND), 11, 1, createGem(DIAMOND), 10, 1);

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), stoneCalculator);

        assertNotNull((WaitBeforeNewGemsPairState)state);
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

        assertNotNull((StoneTurnState)state);
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
