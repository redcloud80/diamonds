package it.diamonds.engine.input;


import it.diamonds.engine.Config;
import it.diamonds.engine.input.Event.Code;

import java.util.HashMap;


public class EventMappings
{

    private HashMap<Code, Code> eventMappings = new HashMap<Code, Code>();


    public static EventMappings create()
    {
        return new EventMappings();
    }


    public static EventMappings createForPlayerOne(Config config)
    {
        EventMappings mappings = new EventMappings();

        mappings.addMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        mappings.addMapping(Code.valueOf(config.getKey("P1.UP")), Code.UP);
        mappings.addMapping(Code.valueOf(config.getKey("P1.DOWN")), Code.DOWN);
        mappings.addMapping(Code.valueOf(config.getKey("P1.LEFT")), Code.LEFT);
        mappings.addMapping(Code.valueOf(config.getKey("P1.RIGHT")), Code.RIGHT);
        mappings.addMapping(Code.valueOf(config.getKey("P1.BUTTON1")), Code.BUTTON1);
        mappings.addMapping(Code.valueOf(config.getKey("P1.BUTTON2")), Code.BUTTON2);
        mappings.addMapping(Code.valueOf(config.getKey("P1.BUTTON3")), Code.BUTTON3);

        return mappings;
    }


    public static EventMappings createForPlayerTwo(Config config)
    {
        EventMappings mappings = new EventMappings();

        mappings.addMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        mappings.addMapping(Code.valueOf(config.getKey("P2.UP")), Code.UP);
        mappings.addMapping(Code.valueOf(config.getKey("P2.DOWN")), Code.DOWN);
        mappings.addMapping(Code.valueOf(config.getKey("P2.LEFT")), Code.LEFT);
        mappings.addMapping(Code.valueOf(config.getKey("P2.RIGHT")), Code.RIGHT);
        mappings.addMapping(Code.valueOf(config.getKey("P2.BUTTON1")), Code.BUTTON1);
        mappings.addMapping(Code.valueOf(config.getKey("P2.BUTTON2")), Code.BUTTON2);
        mappings.addMapping(Code.valueOf(config.getKey("P2.BUTTON3")), Code.BUTTON3);

        return mappings;
    }


    public static EventMappings createForMenuLoop(Config config)
    {
        EventMappings mappings = new EventMappings();

        mappings.addMapping(Code.KEY_ENTER, Code.ENTER);
        mappings.addMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        mappings.addMapping(Code.KEY_UP, Code.UP);
        mappings.addMapping(Code.KEY_DOWN, Code.DOWN);

        return mappings;
    }


    public boolean addMapping(Code sourceCode, Code targetCode)
    {
        if (eventMappings.containsKey(sourceCode))
        {
            return true;
        }
        eventMappings.put(sourceCode, targetCode);
        return false;
    }


    public Code translateEvent(Code sourceCode)
    {
        if (!eventMappings.containsKey(sourceCode))
        {
            return Code.UNKNOWN;
        }

        return eventMappings.get(sourceCode);
    }
}
