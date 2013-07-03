package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.engine.Engine;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class BigGemTestCase extends TestCase
{
    // CheckStyle_Can_You_Stop_Being_So_Pedantic_For_A_Second

    protected Engine engine;

    protected BigGem bigGem;


    // CheckStyle_Ok_Now_You_Can_Go_Back_To_Work

    public void setUp()
    {
        engine = new MockEngine(0, 0);
        bigGem = new BigGem(DroppableColor.DIAMOND, engine);
    }


    public void testDummyTestForWarnings()
    {

    }
}
