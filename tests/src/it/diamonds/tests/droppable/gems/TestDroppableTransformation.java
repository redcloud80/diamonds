package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestDroppableTransformation extends GridTestCase
{
    private MockEngine engine;


    @Override
    public void setUp()
    {
        super.setUp();
        engine = (MockEngine)environment.getEngine();
    }


    public void testGemNoTransform()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Droppable droppable = gem.transform(controller);
        assertSame(droppable, gem);
    }


    public void testBigGemNoTransform()
    {
        AbstractDroppable bigGem = new BigGem(DroppableColor.RUBY, engine);

        Droppable droppable = bigGem.transform(controller);
        assertSame(droppable, bigGem);
    }


    public void testChestNoTransform()
    {
        Droppable chest = createChest(DroppableColor.DIAMOND);
        Droppable droppable = chest.transform(controller);
        assertSame(droppable, chest);
    }


    public void testFlashingGemNoTransform()
    {
        Droppable flashingGem = createFlashingGem();
        Droppable droppable = flashingGem.transform(controller);
        assertSame(droppable, flashingGem);
    }
}
