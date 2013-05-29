package it.diamonds.tests.grid;


import it.diamonds.engine.Point;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.tests.EnvironmentTestCase;


public class TestGridNew extends EnvironmentTestCase
{

    private Point gridOrigin = new Point(0, 0);

    private Grid grid;


    public void setUp()
    {
        super.setUp();
        grid = new Grid(environment);
    }


    public void testCreation()
    {
        assertNotNull(grid);
    }


    public void testCreationWithNullEnviroment()
    {
        try
        {
            new Grid(null);
        }
        catch (NullPointerException ex)
        {
            return;
        }
        fail("NullPointerException must be thrown");
    }


    public void testGetRows()
    {
        assertEquals("Grid must return right number of rows", environment.getConfig().getInteger("rows"), grid.getNumberOfRows());
    }


    public void testGetColumns()
    {
        assertEquals("Grid must return right number of columns", environment.getConfig().getInteger("columns"), grid.getNumberOfColumns());
    }


    public void testHeight()
    {
        assertEquals(Cell.SIZE_IN_PIXELS * grid.getNumberOfRows(), grid.getHeight());
    }


    public void testGetRowUpperBound()
    {
        for (int i = 0; i < grid.getNumberOfRows(); i++)
        {
            assertEquals(i * Cell.SIZE_IN_PIXELS + gridOrigin.getY(), grid.getRowUpperBound(i));
        }
    }
}
