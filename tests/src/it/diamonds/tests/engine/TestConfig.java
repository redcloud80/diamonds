package it.diamonds.tests.engine;


import it.diamonds.engine.Config;
import junit.framework.TestCase;


public class TestConfig extends TestCase
{
    private Config config;


    public void testConfigFileNotFound()
    {
        try
        {
            config = new Config("data/BooYa", "data/KeysConfig");
            fail("exception not raised");
        }
        catch (RuntimeException e)
        {
            ;
        }
    }


    public void testKeysConfigFileNotFound()
    {
        try
        {
            config = new Config("data/GameConfig", "data/BooYa");
            fail("exception not raised");
        }
        catch (RuntimeException e)
        {
            ;
        }
    }


    public void testGetInteger()
    {
        config = new Config("data/TestGameConfig", "data/KeysConfig");
        assertEquals(314, config.getInteger("TestInteger"));
    }


    public void testWrongPropertyName()
    {
        try
        {
            config = Config.create();
            assertEquals(null, config.getInteger("obviously-wrong@property.name"));
            fail("exception not raised");
        }
        catch (RuntimeException e)
        {
            ;
        }
    }


    public void testWrongKeyName()
    {
        config = Config.create();
        assertEquals(null, config.getKey("obviously-wrong@property.name"));
    }


    public void testGetKey()
    {
        config = new Config("data/GameConfig", "data/TestKeysConfig");
        assertEquals("KEY_UP", config.getKey("P1.UP"));
    }

}
