package it.diamonds.engine.input;


import it.diamonds.engine.Config;
import it.diamonds.engine.input.Event.Code;

import java.util.HashMap;


public class EventMappings
{

    private HashMap<Code, Code> eventMappings = new HashMap<Code, Code>();
    
    public static EventMappings createForPlayerOne(Config config)
    {
        return createPlayerMapping(config, "P1");
    }


    public static EventMappings createForPlayerTwo(Config config)
    {
        return createPlayerMapping(config, "P2");
    }
    
    
    private static EventMappings createPlayerMapping(Config config, String playerId)
    {
        EventMappings mappings = new EventMappings();
        
        mappings.addMapping(Code.KEY_ESCAPE, Code.ESCAPE);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".UP")), Code.UP);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".DOWN")), Code.DOWN);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".LEFT")), Code.LEFT);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".RIGHT")), Code.RIGHT);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".BUTTON1")), Code.BUTTON1);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".BUTTON2")), Code.BUTTON2);
        mappings.addMapping(Code.valueOf(config.getKey(playerId + ".BUTTON3")), Code.BUTTON3);
        
        return mappings;
    }

    public static EventMappings createForMenuLoop()
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

    @Override
    public boolean equals(Object obj)
    {
        EventMappings other = (EventMappings) obj;
        return eventMappings.equals(other.eventMappings);
    }

}
