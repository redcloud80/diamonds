package it.diamonds.tests.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestSpriteAreaInTexture extends TestCase
{
    private Engine engine;

    private Image texture;

    private Sprite sprite;

    private Rectangle rectangle;


    public void setUp()
    {
        rectangle = new Rectangle(0, 0, 12, 12);
        engine = MockEngine.create(800, 600);
        texture = engine.createImage("diamond");
        sprite = new Sprite(new Point(100, 200), rectangle, texture);
    }


    public void testCreateSpriteFromTextureArea()
    {
        assertEquals(12, sprite.getTextureArea().getWidth());
        assertEquals(12, sprite.getTextureArea().getHeight());
    }


    public void testSpriteDrawWithArea()
    {

        sprite.draw(engine);

        assertEquals(11, ((MockEngine)engine).getImageRect().getRight());
        assertEquals(11, ((MockEngine)engine).getImageRect().getBottom());
    }
}
