package it.diamonds.tests.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGridRunsIterations extends GridTestCase
{
    private MockDroppableIteration iteration;


    public void setUp()
    {
        super.setUp();

        iteration = new MockDroppableIteration();
    }


    public Droppable createAndInsertDroppable(int row, int column)
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(row, column);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        return droppable;
    }


    public void testIterationCountOnEmptyGrid()
    {
        grid.runIteration(iteration);

        int iterationsCount = iteration.getIterationsCount();

        assertEquals(0, iterationsCount);
    }


    public void testIterationCountOnGridWithOneGem()
    {
        createAndInsertDroppable(0, 0);

        grid.runIteration(iteration);

        int iterationsCount = iteration.getIterationsCount();

        assertEquals(1, iterationsCount);
    }


    public void testIterationCountOnGridWithTwoGems()
    {
        createAndInsertDroppable(0, 0);
        createAndInsertDroppable(0, 1);

        grid.runIteration(iteration);

        int iterationsCount = iteration.getIterationsCount();

        assertEquals(2, iterationsCount);
    }


    public void testOneDroppableWasIterated()
    {
        Droppable droppable = createAndInsertDroppable(0, 0);

        grid.runIteration(iteration);

        assertTrue(iteration.hasBeenIterated(droppable));
    }


    public void testAllDroppablesWereIterated()
    {
        Droppable droppables[] = { createAndInsertDroppable(0, 0),
            createAndInsertDroppable(2, 0), createAndInsertDroppable(4, 0),
            createAndInsertDroppable(6, 0), };

        grid.runIteration(iteration);

        for (Droppable droppable : droppables)
        {
            assertTrue(iteration.hasBeenIterated(droppable));
        }
    }
}
