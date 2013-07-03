package it.diamonds.tests.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.modifiers.Pulsation;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestSprite extends TestCase
{
    private Engine engine;

    private Image texture;

    private Sprite sprite;

    private Pulsation pulsation;


    protected void setUp()
    {
        engine = MockEngine.create(800, 600);
        texture = engine.createImage("diamond");
        sprite = new Sprite(100, 200, texture);
        pulsation = new Pulsation(20, 4);
    }


    public void testGetTexture()
    {
        assertSame(texture, sprite.getTexture());
    }


    public void testGetPosition()
    {
        Point position = new Point(100f, 200f);

        assertEquals(position, sprite.getPosition());
    }


    public void testMove()
    {
        sprite.translate(10, 20);

        assertEquals(110, sprite.getPosition().getX(), 0.001f);
        assertEquals(220, sprite.getPosition().getY(), 0.001f);

        sprite.translate(-20, -40);

        assertEquals(90, sprite.getPosition().getX(), 0.001f);
        assertEquals(180, sprite.getPosition().getY(), 0.001f);
    }


    public void testCollision()
    {
        sprite.translate(-100, 0);

        assertEquals(0, sprite.getPosition().getX(), 0.001f);
        assertEquals(200, sprite.getPosition().getY(), 0.001f);

        sprite.translate(0, 10);

        assertEquals(210, sprite.getPosition().getY(), 0.001f);
    }


    public void testNullTexture()
    {
        try
        {
            sprite = new Sprite(0, 0, null);
            fail("exception not raised");
        }
        catch (NullPointerException e)
        {
            ;
        }
    }


    public void testGetHeight()
    {
        assertEquals("sprite height is different from texture height", texture.getHeight(), sprite.getTextureArea().getHeight());
    }


    public void testTextureCannotBeNull()
    {
        try
        {
            @SuppressWarnings("unused")
            Sprite mySprite = new Sprite(10, 11, new Rectangle(0, 0, 11, 6), null);
        }
        catch (NullPointerException e)
        {
            return;
        }
        fail("exception not raised");
    }


    public void testHideShowSprite()
    {
        sprite.hide();
        sprite.draw(engine);

        assertEquals(0, ((MockEngine)engine).getNumberOfQuadsDrawn());

        sprite.show();
        sprite.draw(engine);

        assertEquals(1, ((MockEngine)engine).getNumberOfQuadsDrawn());
    }


    public void testHiddenStatus()
    {
        sprite.hide();

        assertTrue(sprite.isHidden());

        sprite.show();

        assertFalse(sprite.isHidden());
    }


    public void testNotPulsing()
    {
        assertFalse(sprite.isPulsing());
    }


    public void testPulse()
    {
        sprite.startPulsation(pulsation);
        assertTrue(sprite.isPulsing());
    }


    public void testNotPulsingAnymore()
    {
        sprite.startPulsation(pulsation);
        sprite.stopPulsation();
        assertFalse(sprite.isPulsing());
    }
}
