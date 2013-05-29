package it.diamonds.tests.grid.iteration;


import it.diamonds.grid.Grid;
import it.diamonds.grid.iteration.CanMoveDownQuery;


public class TestCanMoveDownQuery extends DroppableIterationTestCase
{
    private CanMoveDownQuery iteration;


    public void setUp()
    {
        super.setUp();

        iteration = new CanMoveDownQuery(null);
    }


    private MockDroppable createAndInsertMockDroppable(final boolean canMoveDown)
    {
        MockDroppable droppable = new MockDroppable();

        droppable.setCanMoveDown(canMoveDown);

        insertIntoFakeGrid(droppable);

        return droppable;
    }


    public void testExecutesOnGrid()
    {
        Grid grid = new Grid(environment);

        MockDroppable droppable = createAndInsertMockDroppable(false);

        iteration = new CanMoveDownQuery(grid);
        iterateFakeGrid(iteration);

        assertSame(grid, droppable.getGridPassedToCanMoveDownMethod());
    }


    public void testReturnsFalseByDefault()
    {
        iterateFakeGrid(iteration);

        final boolean result = iteration.getResult();
        assertFalse(result);
    }


    public void testReturnsIfOneDroppableCantMoveDown()
    {
        createAndInsertMockDroppable(false);

        iterateFakeGrid(iteration);

        final boolean result = iteration.getResult();
        assertFalse(result);
    }


    public void testReturnsTrueIfOneDroppableCanMoveDown()
    {
        createAndInsertMockDroppable(true);

        iterateFakeGrid(iteration);

        final boolean result = iteration.getResult();
        assertTrue(result);
    }


    public void testReturnsTrueIfOneDroppableCanMoveDownOutOfTwo()
    {
        createAndInsertMockDroppable(true);
        createAndInsertMockDroppable(false);

        iterateFakeGrid(iteration);

        final boolean result = iteration.getResult();
        assertTrue(result);
    }
}
