package it.diamonds.tests.grid;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGridCounter extends GridTestCase
{
    public void testCounterAfterDiamondInsertion()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 2);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testCounterAfterDiamondRemotion()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        grid.removeDroppable(gem);
        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testCounterAfterTwoDiamondInsertions()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 2);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        Droppable droppable1 = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(3, 0);
        droppable1.getRegion().setRow(cell1.getRow());
        droppable1.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable1);
        assertEquals(2, grid.getNumberOfDroppables());
    }
}
