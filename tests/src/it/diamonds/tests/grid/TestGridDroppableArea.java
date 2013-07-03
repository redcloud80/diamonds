package it.diamonds.tests.grid;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Region;
import it.diamonds.tests.GridTestCase;


public class TestGridDroppableArea extends GridTestCase
{

    public void setUp()
    {
        super.setUp();
    }


    public void testListNotNull()
    {
        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 1, 1));
        assertNotNull(list);
    }


    public void testListEmpty()
    {
        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 1, 1));
        assertTrue("list must be empty", list.isEmpty());
    }


    public void testListNotEmpty()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(0, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 1, 1));

        assertFalse("list must not be empty", list.isEmpty());
        assertTrue("list must contain the gem", list.contains(gem));
    }


    public void testListNotEmptyWithOtherGem()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 1);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        DroppableList list = grid.getDroppablesInArea(new Region(1, 1, 1, 1));

        assertFalse("list must not be empty", list.isEmpty());
        assertTrue("list must contain the gem", list.contains(gem));
    }


    public void testRegionWithBiggerWidth()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(0, 1);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 2, 1));
        assertFalse("list must not be empty", list.isEmpty());
        assertTrue("list must contain the gem", list.contains(gem));
    }


    public void testRegionWithBiggerHeight()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 1, 2));
        assertFalse("list must not be empty", list.isEmpty());
        assertTrue("list must contain the gem", list.contains(gem));
    }


    public void testListConatinsAllGems()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Droppable anotherGem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(0, 0);

        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Cell cell1 = Cell.create(2, 2);
        anotherGem.getRegion().setRow(cell1.getRow());
        anotherGem.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(anotherGem);

        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 3, 3));

        assertSame("list must contain both gems", 2, list.size());
        assertTrue("list must contain the gem", list.contains(gem));
        assertTrue("list must contain the gem", list.contains(anotherGem));
    }


    public void testGemsInsertedOnlyOnce()
    {
        Droppable bigGem = new BigGem(DroppableColor.DIAMOND, environment.getEngine());
        Cell cell = Cell.create(0, 0);
        bigGem.getRegion().setRow(cell.getRow());
        bigGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(bigGem);

        DroppableList list = grid.getDroppablesInArea(new Region(0, 0, 2, 2));
        assertSame("list must contain only one Droppabale", 1, list.size());
        assertTrue("list must contain the bigGem", list.contains(bigGem));
    }


    public void testIntersectingGemAdded()
    {
        Droppable bigGem = new BigGem(DroppableColor.DIAMOND, environment.getEngine());
        Cell cell = Cell.create(0, 0);
        bigGem.getRegion().setRow(cell.getRow());
        bigGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(bigGem);

        DroppableList list = grid.getDroppablesInArea(new Region(1, 1, 2, 2));
        assertFalse("list must not be empty", list.isEmpty());
        assertTrue("list must contain the bigGem", list.contains(bigGem));
    }
}
