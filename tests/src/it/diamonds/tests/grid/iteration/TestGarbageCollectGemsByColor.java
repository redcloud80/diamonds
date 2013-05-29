package it.diamonds.tests.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.grid.iteration.RemoveByColor;
import it.diamonds.tests.GridTestCase;


public class TestGarbageCollectGemsByColor extends GridTestCase
{
    private RemoveByColor iteration;


    protected void setUp()
    {
        super.setUp();
    }


    private void prepareIterationFor(DroppableColor colorToGarbageCollect)
    {
        iteration = new RemoveByColor(colorToGarbageCollect, grid);
    }


    public void testDifferentColorIsNotCollected()
    {
        prepareIterationFor(DroppableColor.DIAMOND);

        Droppable droppable = createGem(DroppableColor.RUBY);
        Cell cell = Cell.create(13, 0);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        iteration.executeOn(droppable);

        assertNotNull(grid.getDroppableAt(Cell.create(13, 0)));
    }


    public void testSameColorIsCollected()
    {
        prepareIterationFor(DroppableColor.EMERALD);

        Droppable droppable = createGem(DroppableColor.EMERALD);
        Cell cell = Cell.create(13, 0);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        iteration.executeOn(droppable);

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
    }
}
