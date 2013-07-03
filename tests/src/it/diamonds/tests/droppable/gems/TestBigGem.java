package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.grid.Cell;
import it.diamonds.tests.mocks.MockSingleDroppable;


public class TestBigGem extends BigGemTestCase
{
    private static final int TEST_SIZE = 4;

    private DroppableList gems;


    public void setUp()
    {
        super.setUp();

        gems = new DroppableList();

        for (int i = 0; i < TEST_SIZE; i++)
        {
            gems.add(MockSingleDroppable.create(engine));
        }
    }


    public void testSpriteSize()
    {
        assertEquals(Cell.SIZE_IN_PIXELS, bigGem.getSprite().getTextureArea().getHeight());
        assertEquals(Cell.SIZE_IN_PIXELS, bigGem.getSprite().getTextureArea().getWidth());
    }


    public void testGemType()
    {
        assertSame(DroppableColor.DIAMOND, bigGem.getColor());
    }


    public void testDifferentTexturesLoaded()
    {
        AbstractDroppable diamondBigGem = new BigGem(DroppableColor.DIAMOND, engine);
        AbstractDroppable emeraldBigGem = new BigGem(DroppableColor.EMERALD, engine);

        assertEquals("gemGroup Texture is not correct", "gfx/droppables/tiles/diamond", diamondBigGem.getSprite().getTexture().getName());
        assertEquals("gemGroup Texture is not correct", "gfx/droppables/tiles/emerald", emeraldBigGem.getSprite().getTexture().getName());
    }


    public void testIsBigGem()
    {
        assertSame(bigGem.getType(), DroppableType.BIG_GEM);
    }


    public void testSameBigGems()
    {
        AbstractDroppable diamondBigGem = new BigGem(DroppableColor.DIAMOND, engine);
        assertSame(bigGem.getType(), diamondBigGem.getType());
        assertSame(bigGem.getColor(), diamondBigGem.getColor());
    }


    public void testNotSameBigGems()
    {
        AbstractDroppable newBigGem = new BigGem(DroppableColor.RUBY, engine);

        assertSame(bigGem.getType(), newBigGem.getType());
        assertNotSame(bigGem.getColor(), newBigGem.getColor());
    }


    public void testNotSameWithChestOfSameColor()
    {
        Chest chest = new Chest(engine, bigGem.getColor(), 3500, 0);

        assertNotSame(bigGem.getType(), chest.getType());
        assertSame(bigGem.getColor(), chest.getColor());
    }


    public void testDrop()
    {
        assertFalse(bigGem.isFalling());
        bigGem.drop();
        assertFalse(bigGem.isFalling());
    }
}
