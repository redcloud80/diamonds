package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.types.DroppableType.GEM;
import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.Pattern;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.iteration.CanMoveDownQuery;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.StoneFallState;
import it.diamonds.grid.state.WaitBeforeNewGemsPairState;
import it.diamonds.tests.PlayFieldTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestStoneFallState extends PlayFieldTestCase
{
    private Pattern pattern;

    private StoneFallState state;


    public void setUp()
    {
        super.setUp();

        pattern = new Pattern(0);
        state = new StoneFallState(environment, pattern);
    }


    public void testNextState()
    {
        AbstractControllerState newState = state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertNotNull((WaitBeforeNewGemsPairState)newState);
    }


    public void testSameStateWithIncomingStones()
    {
        controller.setIncomingStones(3);
        assertSame(state, state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator()));
    }


    public void testOneStoneAdded()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertTrue((!grid.isCellFree(Cell.create(1, 0))));
    }


    public void testTwoStonesAdded()
    {
        controller.setIncomingStones(2);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertTrue((!grid.isCellFree(Cell.create(1, 0))));
        assertTrue((!grid.isCellFree(Cell.create(1, 1))));
    }


    public void testSameStateWithStonesUnderControl()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertSame(state, state.update(environment.getTimer().getTime(), controller, null, null));
    }


    public void testStateChangesWhenStoneDrops()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        Droppable stone = grid.getDroppableAt(Cell.create(1, 0));
        stone.drop();
        AbstractControllerState newState = state.update(environment.getTimer().getTime(), controller, null, null);
        
        assertNotNull((WaitBeforeNewGemsPairState)newState);
    }


    public void testSameStateWhenOneOfTwoStonesDrops()
    {
        controller.setIncomingStones(2);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        Droppable stone1 = grid.getDroppableAt(Cell.create(1, 0));
        stone1.drop();
        assertSame(state, state.update(environment.getTimer().getTime(), controller, null, null));
        Droppable stone2 = grid.getDroppableAt(Cell.create(2, 1));
        stone2.drop();
        AbstractControllerState newState = state.update(environment.getTimer().getTime(), controller, null, null); 
        assertNotNull((WaitBeforeNewGemsPairState)newState);
    }


    public void testStonesAreUpdated()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertFalse((!grid.isCellFree(Cell.create(0, 0))));
        assertTrue((!grid.isCellFree(Cell.create(1, 0))));
    }


    public void testStoneGravity()
    {
        controller.setIncomingStones(4);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }


    public void testGravityIsResetAfterStonesHaveFallen()
    {
        controller.setIncomingStones(4);
        while (state == state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator()))
        {
            ;
        }
        assertEquals(getDeltaYGravity(), grid.getActualGravity(), 0.001f);
    }


    public void testStoneColors()
    {
        controller.setIncomingStones(8);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        for (int i = 0; i < 8; i++)
        {
            assertEquals(pattern.getDroppableColor(i), grid.getDroppableAt(Cell.create(1, i)).getHiddenColor());
        }
    }


    public void testNoReinsertionOfPreviousStones()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        controller.setIncomingStones(1);
        try
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }
        catch (IllegalArgumentException e)
        {
            fail("attempt to reinsert already inserted stones");
        }
    }


    public void testStonesDuringInsertionWillInsertedNext()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        assertEquals("there must be present only a stone", 1, controller.getGridRenderer().getGrid().getNumberOfDroppables());
    }


    private boolean isFirstRowFreeForFalling(GridController controller)
    {
        int columns = grid.getNumberOfColumns();
        for (int i = 0; i < columns; i++)
        {
            if (!controller.getGridRenderer().getGrid().isCellFree(Cell.create(1, i)))
            {
                return false;
            }
        }
        return true;
    }


    public void testMoreThanEightStoneInsertion()
    {
        controller.setIncomingStones(9);

        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        while (!isFirstRowFreeForFalling(controller))
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }
        state.update(environment.getTimer().getTime(), controller, null, null);

        assertEquals("The number of stones in the grid must be 9", 9, grid.getNumberOfDroppables());
        assertTrue("The ninth stone must be inserted in the first column", (!grid.isCellFree(Cell.create(1, 0))));
        assertTrue("The ninth stone must be falling", grid.getDroppableAt(Cell.create(1, 0)).isFalling());
    }


    public void testMoreThanEightStoneColor()
    {
        controller.setIncomingStones(9);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        while (!isFirstRowFreeForFalling(controller))
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }
        state.update(environment.getTimer().getTime(), controller, null, null);

        Droppable stone = grid.getDroppableAt(Cell.create(1, 0));

        assertEquals("The ninth stone color must be the first of the pattern", pattern.getDroppableColor(0), stone.getHiddenColor());
    }


    private void insertAndUpdate(Droppable gem, int row, int column)
    {
        Cell cell = Cell.create(row, column);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        grid.updateDroppable(gem);
    }


    public void testStoneInsertionIfGemsPresent()
    {
        int rows = grid.getNumberOfRows();
        int columns = grid.getNumberOfColumns();

        for (int row = rows - 1; row >= 1; row--)
        {
            for (int column = 0; column < columns; column++)
            {
                insertAndUpdate(gemFactory.create(GEM, DIAMOND, 0), row, column);
            }
        }

        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        Droppable stone = grid.getDroppableAt(Cell.create(0, 0));

        assertEquals("The number of droppable in the grid must be equals", 8 * 13 + 1, grid.getNumberOfDroppables());

        assertNotNull("The stone must be inserted", stone);
    }


    public void testStoneInsertionIfStonesPresent()
    {
        insertStoneRows(13);

        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        Droppable stone = grid.getDroppableAt(Cell.create(0, 0));

        assertEquals("The number of stones in the grid must be equals", 8 * 13 + 1, grid.getNumberOfDroppables());

        assertNotNull("The stone must be inserted", stone);
    }


    public void testMoreThanEightStoneInsertionIfStonesPresent()
    {
        insertStoneRows(12);
        controller.setIncomingStones(9);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        while (droppedGemCanMoveDown(grid))
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }
        state.update(environment.getTimer().getTime(), controller, null, null);

        Droppable stone = grid.getDroppableAt(Cell.create(0, 0));

        assertEquals("The number of stones in the grid must be equals", 8 * 12 + 9, grid.getNumberOfDroppables());
        assertNotNull("The stone must be inserted", stone);
    }


    public void testNoStoneInsertion()
    {
        int rows = grid.getNumberOfRows();
        insertStoneRows(rows);
        controller.setIncomingStones(1);

        try
        {
            state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());
        }
        catch (Exception e)
        {
            fail("The stone must not be insert");
        }
    }


    public void testStoneFallFaster()
    {
        controller.setIncomingStones(1);

        controller.update(environment.getTimer().getTime(), new ScoreCalculator(0), grid);

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());

        loop.getPlayerOneInput().notify(Event.create(Code.DOWN, State.PRESSED));

        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());

        loop.getPlayerOneInput().notify(Event.create(Code.DOWN, State.RELEASED));

        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }


    public void testStoneDontReactToInput()
    {
        controller.setIncomingStones(1);
        controller.update(environment.getTimer().getTime(), new ScoreCalculator(0), grid);

        loop.getPlayerOneInput().notify(Event.create(Code.RIGHT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        assertSame("Stone moved", grid.getDroppableAt(Cell.create(1, 0)).getType(), DroppableType.STONE);
    }


    private Droppable insertAndGetSingleStone()
    {
        controller.setIncomingStones(1);
        state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), new StoneCalculator());

        return grid.getDroppableAt(Cell.create(1, 0));
    }


    private void insertStoneRows(int numberOfRows)
    {
        controller.setIncomingStones(numberOfRows * grid.getNumberOfColumns());
        state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), new StoneCalculator());

        while (droppedGemCanMoveDown(grid))
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }

        state.update(environment.getTimer().getTime(), controller, null, null);
    }


    public void testStoneFrameInFirstSegment()
    {
        Droppable stone = insertAndGetSingleStone();

        assertEquals("Stone must be using the first frame", 0, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneFrameInSecondSegment()
    {
        insertStoneRows(2);
        Droppable stone = insertAndGetSingleStone();

        assertEquals("Stone must be using the first frame", 1, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneFrameInThirdSegment()
    {
        insertStoneRows(4);

        Droppable stone = insertAndGetSingleStone();

        assertEquals("Stone must be using the third frame", 2, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneFrameInFourthSegment()
    {
        insertStoneRows(7);
        Droppable stone = insertAndGetSingleStone();

        assertEquals("Stone must be using the fourth frame", 3, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneFrameInFifthSegment()
    {
        insertStoneRows(10);
        Droppable stone = insertAndGetSingleStone();

        assertEquals("Stone must be using the fifth  frame", 4, stone.getAnimatedSprite().getCurrentFrame());
    }


    protected boolean droppedGemCanMoveDown(Grid grid)
    {
        CanMoveDownQuery action = new CanMoveDownQuery(grid);
        grid.runIteration(action);
        return action.getResult();
    }


    public void testStoneFrameIfFalling()
    {
        controller.setIncomingStones(2 * 8 + 1);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        while (droppedGemCanMoveDown(grid))
        {
            state.update(environment.getTimer().getTime(), controller, null, null);
        }

        state.update(environment.getTimer().getTime(), controller, null, null);

        Droppable stone = grid.getDroppableAt(Cell.create(11, 0));

        assertEquals("Stone must be using the first frame", 1, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneInsertionNotInFirstColumnIfSecondFree()
    {
        insertAndUpdate(gemFactory.create(GEM, EMERALD, 0), 13, 0);

        Droppable stone = insertAndGetSingleStone();

        assertNull("The stone must not be insert in the first column", stone);

        stone = grid.getDroppableAt(Cell.create(1, 1));

        assertNotNull("The stone must be insert in the second column", stone);
    }


    public void testStoneInsertionNotInFirstColumnIfThirdFree()
    {
        insertAndUpdate(gemFactory.create(GEM, EMERALD, 0), 13, 0);
        insertAndUpdate(gemFactory.create(GEM, EMERALD, 0), 13, 1);

        Droppable stone = insertAndGetSingleStone();

        assertNull("The stone must not be insert in the first column", stone);

        stone = grid.getDroppableAt(Cell.create(1, 2));

        assertNotNull("The stone must be insert in the third column", stone);
    }


    public void testStoneInsertionNotInSecondAndFourthColumn()
    {
        insertAndUpdate(gemFactory.create(GEM, EMERALD, 0), 13, 0);
        insertAndUpdate(gemFactory.create(GEM, EMERALD, 0), 13, 2);

        controller.setIncomingStones(2);
        state.update(environment.getTimer().getTime(), controller, null, new StoneCalculator());

        Droppable stone0 = grid.getDroppableAt(Cell.create(1, 0));
        Droppable stone1 = grid.getDroppableAt(Cell.create(1, 1));
        Droppable stone2 = grid.getDroppableAt(Cell.create(1, 2));
        Droppable stone3 = grid.getDroppableAt(Cell.create(1, 3));

        assertNull("The stone must not be insert in the first column", stone0);
        assertNotNull("The stone must be insert in the second column", stone1);
        assertNull("The stone must not be insert in the third column", stone2);
        assertNotNull("The stone must be insert in the fourth column", stone3);
    }


    public void testPassToGameOverState()
    {
        insertBigGem(DIAMOND, 1, 0, 13, 7);

        controller.setIncomingStones(5);

        controller.update(environment.getTimer().getTime(), new ScoreCalculator(0), grid);
        controller.update(environment.getTimer().getTime(), new ScoreCalculator(0), grid);

        assertTrue(controller.isGameOver());
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }


    protected void insertBigGem(DroppableColor color, int startRow, int startColumn, int endRow, int endColumn)
    {
        for (int row = endRow; row >= startRow; --row)
        {
            for (int column = startColumn; column <= endColumn; ++column)
            {
                Gem gem = new Gem(environment.getEngine(), color, 3500, 100);
                gem.drop();
                insertAndUpdate(gem, row, column);
            }
        }
    }

}
