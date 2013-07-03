package it.diamonds.tests.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.video.Image;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestTexture extends TestCase
{

    private Engine engine;


    protected void setUp()
    {
        engine = MockEngine.create(800, 600);
    }


    public void testLoadDiamondTexture()
    {
        Image texture = engine.createImage("diamond");

        assertTrue("texture has not been loaded", texture.isLoaded());
    }


    public void testSize()
    {
        Image texture = engine.createImage("diamond");

        assertEquals("texture width is wrong", 64, texture.getWidth());
        assertEquals("texture height is wrong", 64, texture.getHeight());
    }


    public void testLoadFailed()
    {
        try
        {
            engine.createImage("this_texture_doesnt_exist");
            fail("TextureNotFoundException not thrown");
        }
        catch (RuntimeException e)
        {
            ;
        }
    }


    public void testMultipleTextureCreation()
    {
        Image texture = engine.createImage("diamond");
        Image texture2 = engine.createImage("grid-background");
        Image texture3 = engine.createImage("diamond");
        Image texture4 = engine.createImage("grid-background");

        assertSame("textures must be the same", texture, texture3);
        assertSame("textures must be the same", texture2, texture4);
    }


    public void testCleanUpTexture()
    {
        Image texture = engine.createImage("diamond");
        texture.cleanup();

        assertFalse("texture has not been cleaned", texture.isLoaded());
    }


    public void testLoadNotTwoMultipleTexture()
    {
        try
        {
            engine.createImage("textureTest");
        }
        catch (RuntimeException t)
        {
            return;
        }
        fail();
    }
}
