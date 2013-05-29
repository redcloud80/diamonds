package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestGem extends GridTestCase
{
    private Gem gem;

    private MockEngine engine;


    @Override
    public void setUp()
    {
        super.setUp();
        engine = (MockEngine)environment.getEngine();
        gem = createGem(DroppableColor.DIAMOND);
    }


    public void testGemIsDrawnCorrectly()
    {
        gem.getAnimatedSprite().getSprite().draw(engine);

        assertEquals("Wrong left texture position", engine.getImageRect().getLeft(), 0);
        assertEquals("Wrong right texture position", engine.getImageRect().getRight(), 31);
    }


    public void testGemViewSize()
    {
        Sprite sprite = gem.getAnimatedSprite().getSprite();
        sprite.draw(engine);

        assertEquals("Wrong textureArea height", engine.getImageRect().getHeight(), sprite.getTextureArea().getHeight());
        assertEquals("Wrong textureArea Width", engine.getImageRect().getWidth(), sprite.getTextureArea().getWidth());
    }


    public void testSetAndGetCurrentFrame()
    {
        gem.getAnimatedSprite().setCurrentFrame(1);
        assertEquals(1, gem.getAnimatedSprite().getCurrentFrame());

        gem.getAnimatedSprite().setCurrentFrame(2);
        assertEquals(2, gem.getAnimatedSprite().getCurrentFrame());
    }
}
