package it.diamonds.tests.engine.input;


import it.diamonds.engine.Config;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Event.Code;
import junit.framework.TestCase;


public class TestEventMappings extends TestCase
{
    private Config config;

    private EventMappings eventMappings;

    private EventMappings eventMappings2;


    protected void setUp()
    {
        config = Config.create();

        eventMappings = new EventMappings();
        eventMappings2 = new EventMappings();
    }


    public void testUnkownMapping()
    {
        assertEquals(Code.UNKNOWN, eventMappings.translateEvent(Code.KEY_A));
    }


    public void testExistingMapping()
    {
        eventMappings.addMapping(Code.KEY_A, Code.KEY_B);
        assertEquals(Code.KEY_B, eventMappings.translateEvent(Code.KEY_A));
    }


    public void testUnexistingMapping()
    {
        eventMappings.addMapping(Code.KEY_A, Code.KEY_B);
        assertEquals(Code.UNKNOWN, eventMappings.translateEvent(Code.KEY_B));
    }


    public void testAlreadyExistingMapping()
    {
        assertFalse(eventMappings.addMapping(Code.KEY_A, Code.KEY_B));

        assertTrue(eventMappings.addMapping(Code.KEY_A, Code.KEY_C));
        assertEquals(Code.KEY_B, eventMappings.translateEvent(Code.KEY_A));
    }


    public void testEqualsOnCreation()
    {
        assertTrue(eventMappings.equals(eventMappings2));
    }


    public void testNotEquals()
    {
        eventMappings2.addMapping(Code.KEY_A, Code.KEY_B);

        assertFalse(eventMappings.equals(eventMappings2));
    }


    public void testEquals()
    {
        eventMappings.addMapping(Code.KEY_A, Code.KEY_B);
        eventMappings2.addMapping(Code.KEY_A, Code.KEY_B);

        assertTrue(eventMappings.equals(eventMappings2));
    }


    public void testNotEqualsOnValues()
    {
        eventMappings.addMapping(Code.KEY_A, Code.KEY_C);
        eventMappings2.addMapping(Code.KEY_A, Code.KEY_B);

        assertFalse(eventMappings.equals(eventMappings2));
    }


    public void testPlayerOneConfigUniqueMappings()
    {
        testPlayerConfigUniqueMappings("P1");
    }


    public void testPlayerTwoConfigUniqueMappings()
    {
        testPlayerConfigUniqueMappings("P2");
    }


    private void testPlayerConfigUniqueMappings(String player)
    {
        checkNotExistingKey(player + ".UP");
        checkNotExistingKey(player + ".DOWN");
        checkNotExistingKey(player + ".LEFT");
        checkNotExistingKey(player + ".RIGHT");
        checkNotExistingKey(player + ".BUTTON1");
        checkNotExistingKey(player + ".BUTTON2");
        checkNotExistingKey(player + ".BUTTON3");
        checkNotExistingKey(player + ".BLOW");
    }


    private void checkNotExistingKey(String keyName)
    {
        Code code = Code.valueOf(config.getKey(keyName));
        assertFalse(eventMappings.addMapping(code, code));
    }


    public void testMenuLoopMappings()
    {
        eventMappings = EventMappings.createForMenuLoop();
        checkMapping(Code.KEY_ENTER, Code.ENTER);
        checkMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        checkMapping(Code.KEY_UP, Code.UP);
        checkMapping(Code.KEY_DOWN, Code.DOWN);
    }


    private void checkMapping(Code source, Code translated)
    {
        assertEquals(translated, eventMappings.translateEvent(source));
    }


    public void testPlayerOneMappings()
    {
        eventMappings = EventMappings.createForPlayerOne(config);
        testPlayerMappings("P1");
    }


    public void testPlayerTwoMappings()
    {
        eventMappings = EventMappings.createForPlayerTwo(config);
        testPlayerMappings("P2");
    }


    private void testPlayerMappings(String player)
    {
        checkMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        checkMapping(player + ".UP");
        checkMapping(player + ".DOWN");
        checkMapping(player + ".LEFT");
        checkMapping(player + ".RIGHT");
        checkMapping(player + ".BUTTON1");
        checkMapping(player + ".BUTTON2");
        checkMapping(player + ".BUTTON3");
        // checkMapping(player + ".BLOW");
    }


    private void checkMapping(String configSourceCode)
    {
        String sourceCodeString = config.getKey(configSourceCode);

        int pointIndex = configSourceCode.lastIndexOf(".");
        String translatedCodeString = configSourceCode.substring(pointIndex + 1);

        Code source = Code.valueOf(sourceCodeString);
        Code translated = Code.valueOf(translatedCodeString);

        assertEquals(translated, eventMappings.translateEvent(source));
    }
}
