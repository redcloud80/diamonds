package it.diamonds.tests.engine.input;


import it.diamonds.engine.Config;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Event.Code;
import junit.framework.TestCase;


public class TestEventMappings extends TestCase
{
    private Config config;

    private EventMappings eventMappings;


    protected void setUp()
    {
        config = Config.create();

        eventMappings = EventMappings.create();
    }


    public void testEmptyMappings()
    {
        assertEquals(Code.UNKNOWN, eventMappings.translateEvent(Code.KEY_A));
    }


    public void testExistingMappings()
    {
        assertFalse(eventMappings.addMapping(Code.KEY_A, Code.KEY_B));
        assertTrue(eventMappings.addMapping(Code.KEY_A, Code.KEY_C));
    }


    public void testUnexistingMappings()
    {
        eventMappings.addMapping(Code.KEY_A, Code.KEY_B);

        assertEquals(Code.UNKNOWN, eventMappings.translateEvent(Code.KEY_B));
    }


    private void checkKey(String keyName)
    {
        Code code = Code.valueOf(config.getKey(keyName));
        assertFalse(eventMappings.addMapping(code, code));
    }


    private void commonTestPlayerConfig(String player)
    {
        checkKey(player + ".UP");
        checkKey(player + ".DOWN");
        checkKey(player + ".LEFT");
        checkKey(player + ".RIGHT");
        checkKey(player + ".BUTTON1");
        checkKey(player + ".BUTTON2");
        checkKey(player + ".BUTTON3");
        checkKey(player + ".BLOW");
    }


    public void testPlayer1Config()
    {
        commonTestPlayerConfig("P1");
    }


    public void testPlayer2Config()
    {
        commonTestPlayerConfig("P2");
    }

}
