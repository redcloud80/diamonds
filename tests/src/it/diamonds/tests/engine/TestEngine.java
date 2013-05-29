package it.diamonds.tests.engine;


import it.diamonds.engine.Engine;
import it.diamonds.engine.video.Image;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestEngine extends TestCase
{
    private Engine engine;


    public void setUp()
    {
        engine = MockEngine.create(800, 600);
    }


    public void testCreated()
    {
        assertEquals(800, engine.getDisplayWidth());
        assertEquals(600, engine.getDisplayHeight());
    }


    public void testShuttedDown()
    {
        engine.shutDown();
        assertTrue(engine.isWindowClosed());
    }


    public void testCreateTexture()
    {
        Image texture = engine.createImage("diamond");
        assertEquals("diamond", texture.getName());
    }


    public void testCreateSameTextureTwice()
    {
        Image texture = engine.createImage("diamond");
        Image sameTexture = engine.createImage("diamond");
        assertSame(sameTexture, texture);
    }


    public void testCreateTwoDifferentTextures()
    {
        Image texture = engine.createImage("diamond");
        Image differentTexture = engine.createImage("gfx/droppables/gems/ruby");
        assertNotSame(differentTexture, texture);
    }

}
