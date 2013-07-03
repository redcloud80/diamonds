package it.diamonds.tests.grid;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.tests.GridTestCase;


public class TestStonesInGrid extends GridTestCase
{

    public void setUp()
    {
        super.setUp();
        createStone(DroppableColor.DIAMOND);
    }

}
