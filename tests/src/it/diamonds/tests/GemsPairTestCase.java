package it.diamonds.tests;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.pair.DroppablePair;


public class GemsPairTestCase extends GridTestCase
{
    // CheckStyle_Can_You_Stop_Being_So_Pedantic_For_A_Second

    protected DroppablePair gemsPair;


    // CheckStyle_Ok_Now_You_Can_Go_Back_To_Work

    public void setUp()
    {
        super.setUp();

        gemsPair = controller.getGemsPair();

        grid.removeDroppable(gemsPair.getPivot());
        grid.removeDroppable(gemsPair.getSlave());

        gemsPair.setNoPivot();

        controller.insertNewGemsPair();
    }


    public void testDummyTestForWarnings()
    {

    }


    protected Gem createGem(DroppableColor color)
    {
        return new Gem(environment.getEngine(), color, 3500, 0);
    }

}
