package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.CrushState;
import it.diamonds.grid.state.GemFallState;
import it.diamonds.grid.state.StoneFallState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestCrushState extends GridTestCase
{
    private AbstractControllerState state;


    public void setUp()
    {
        super.setUp();
        state = new CrushState(environment);
        setDiamondsGemsPair(grid, controller.getGemsPair());
    }


    public void testDroppedGemCanMove()
    {
        insertAndUpdate(createGem(DIAMOND), 5, 1);
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertNotNull((GemFallState)state);
    }


    public void testCrushAndDroppedGemCanMove()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        insertAndUpdate(createChest(EMERALD), 11, 1);

        makeAllGemsFall();
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertNull("Not crush appened", grid.getDroppableAt(Cell.create(13, 1)));
        assertNull("Not crush appened", grid.getDroppableAt(Cell.create(12, 1)));
    }


    public void testCrushAndDroppedGemCantMove()
    {
        Gem gem = createGem(DIAMOND);
        insertAndUpdate(gem, 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        makeAllGemsFall();

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), new StoneCalculator());
        assertNull("Not crush appened", grid.getDroppableAt(Cell.create(13, 1)));
        assertNull("Not crush appened", grid.getDroppableAt(Cell.create(12, 1)));

        assertNull("not cleared gemsPair", controller.getGemsPair().getPivot());

        assertEquals("grid chain not closed", gem.getScore(), scoreCalculator.getScore());
    }


    public void testCrushAndDroppedGemCanMoveReturnState()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        insertAndUpdate(createChest(EMERALD), 11, 1);

        makeAllGemsFall();
        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);

        assertNotNull((GemFallState)state);
    }


    public void testCrushAndDroppedGemCantMoveReturnState()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        makeAllGemsFall();

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), new StoneCalculator());
        try
        {
            GemFallState gemFallState = (GemFallState)state;
            
            assertNull(gemFallState);
        }
        catch(ClassCastException exception)
        {
            return;
        }
    }


    public void testCrushAndReturnStoneFallState()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        makeAllGemsFall();

        controller.setIncomingStones(1);

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), new StoneCalculator());

        assertNotNull((StoneFallState)state);
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
