package it.diamonds.tests.droppable.gems;


import it.diamonds.tests.GridTestCase;


public class TestGemCreationInGrid extends GridTestCase
{
    public void setUp()
    {
        super.setUp();
    }


    public void testGemInsertion()
    {
        assertNotNull("no gem inserted in grid after creation", controller.getGemsPair().getPivot());
    }
}
