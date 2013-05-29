package it.diamonds.tests.grid;


import it.diamonds.grid.Cell;
import junit.framework.TestCase;


public class TestCell extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }


    public void testCreateCell()
    {
        Cell cell = Cell.create(10, 5);

        assertEquals(10, cell.getRow());
        assertEquals(5, cell.getColumn());
    }


    public void testCreateDifferentCells()
    {
        Cell cell = null;

        cell = Cell.create(2, 3);
        assertEquals(2, cell.getRow());
        assertEquals(3, cell.getColumn());

        cell = Cell.create(3, 2);
        assertEquals(3, cell.getRow());
        assertEquals(2, cell.getColumn());
    }


    public void testCreateCellWithBadRow()
    {
        Cell cell = Cell.create(-1, 0);

        assertNull(cell);
    }


    public void testCreateCellWithBadColumn()
    {
        Cell cell = Cell.create(0, -1);
        assertNull(cell);
    }
}
