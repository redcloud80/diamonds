package it.diamonds.tests.grid;


import it.diamonds.grid.Grid;
import it.diamonds.tests.GridTestCase;


public class TestGridGravity extends GridTestCase
{

    public void setUp()
    {
        super.setUp();

        grid.setGravity(0);
    }


    public void testDefaultGravity()
    {
        Grid grid = new Grid(environment, gridPosition);
        assertEquals(getDeltaYGravity(), grid.getActualGravity());
    }


    public void testNullGravity()
    {
        assertEquals(0, grid.getActualGravity());
    }


    public void testGravity()
    {
        grid.setGravity(1);
        assertEquals(1, grid.getActualGravity());
    }


    public void testNormalGravity()
    {
        grid.setNormalGravity();
        assertEquals(getDeltaYGravity(), grid.getActualGravity());
    }


    public void testStrongerGravity()
    {
        grid.setStrongerGravity();
        assertEquals(getDeltaYStrongerGravity(), grid.getActualGravity());
    }


    public void testStrongestGravity()
    {
        grid.setStrongestGravity();
        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }
}
