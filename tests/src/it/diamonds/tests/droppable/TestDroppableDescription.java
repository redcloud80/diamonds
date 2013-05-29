package it.diamonds.tests.droppable;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestDroppableDescription extends TestCase
{
    private static final String DROPPABLE_PATH = "gfx/droppables/";

    private MockEngine engine;


    protected void setUp()
    {
        engine = MockEngine.create(0, 0);
    }


    public void testCreateImageForDiamondBigGem()
    {
        DroppableDescription description = new DroppableDescription(DroppableType.BIG_GEM, DroppableColor.DIAMOND);

        description.createImage(engine);

        assertTrue(engine.isImageCreated(DROPPABLE_PATH + "tiles/diamond"));
    }


    public void testCreateImageForRubyBigGem()
    {
        DroppableDescription description = new DroppableDescription(DroppableType.BIG_GEM, DroppableColor.RUBY);

        description.createImage(engine);

        assertTrue(engine.isImageCreated(DROPPABLE_PATH + "tiles/ruby"));
    }


    public void testCreateImageForDiamondChest()
    {
        DroppableDescription description = new DroppableDescription(DroppableType.CHEST, DroppableColor.DIAMOND);

        description.createImage(engine);

        assertTrue(engine.isImageCreated(DROPPABLE_PATH + "boxes/diamond"));
    }


    public void testCreateImageForRubyChest()
    {
        DroppableDescription description = new DroppableDescription(DroppableType.CHEST, DroppableColor.RUBY);

        description.createImage(engine);

        assertTrue(engine.isImageCreated(DROPPABLE_PATH + "boxes/ruby"));
    }
}
