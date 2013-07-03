package it.diamonds.engine.input;


import it.diamonds.engine.Timer;
import it.diamonds.engine.input.Event.Code;

import java.util.LinkedList;


public final class Input implements Listener
{

    private InputDevice device;

    private Timer timer;

    private LinkedList<Event> queue;

    private EventMappings eventMappings;


    private Input(InputDevice device, Timer timer)
    {
        this.timer = timer;
        this.device = device;
        this.device.setListener(this);

        queue = new LinkedList<Event>();
    }


    public static Input create(InputDevice input, Timer timer)
    {
        return new Input(input, timer);
    }


    public boolean isEmpty()
    {
        return queue.isEmpty();
    }


    public void update()
    {
        device.update();
    }


    public Event extractEvent()
    {
        if (queue.isEmpty())
        {
            return null;
        }

        return queue.remove();
    }


    public void notify(Event event)
    {
        Code newCode = eventMappings.translateEvent(event.getCode());
        if (newCode != Code.UNKNOWN)
        {
            queue.add(event.copyAndChange(newCode, timer.getTime()));
        }
    }


    public void setEventMappings(EventMappings mappings)
    {
        this.eventMappings = mappings;
    }


    public void flush()
    {
        while (!queue.isEmpty())
        {
            extractEvent();
        }
    }
}
