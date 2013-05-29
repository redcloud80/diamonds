package it.diamonds.tests.engine.input;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.EnvironmentTestCase;


public class TestInput extends EnvironmentTestCase
{
    private Input input;
    
    private EventMappings eventMappings;


    protected void setUp()
    {
        super.setUp();
        
        input = Input.create(environment.getKeyboard(), environment.getTimer());
        
        eventMappings = createEventMappings();
        input.setEventMappings(eventMappings);
    }


    public void testQueueEmpty()
    {
        assertTrue(input.isEmpty());
    }


    public void testQueueNotEmpty()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        assertFalse(input.isEmpty());
    }
    

    public void testGetInputDevice()
    {
        assertEquals(environment.getKeyboard(), input.getInputDevice());
    }
    
    
    public void testGetEventMappings()
    {
        assertEquals(eventMappings, input.getEventMappings());
    }
    

    public void testFlush()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.flush();
        assertTrue(input.isEmpty());
    }


    public void testNullExtraction()
    {
        assertNull(input.extractEvent());
    }


    public void testQueueItemRemoval()
    {
        input.notify(Event.create(Code.RIGHT, State.PRESSED));

        assertTrue(input.extractEvent().is(Code.RIGHT));
    }


    public void testQueueSequenceOrder()
    {
        input.notify(Event.create(Code.RIGHT, State.PRESSED));
        input.notify(Event.create(Code.UP, State.PRESSED));

        assertTrue(input.extractEvent().is(Code.RIGHT));
        assertTrue(input.extractEvent().is(Code.UP));
    }


    public void testEventState()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        assertTrue(input.extractEvent().isPressed());
        assertTrue(input.extractEvent().isReleased());
    }


    public void testEventTimestamp() throws InterruptedException
    {
        input.notify(Event.create(Code.RIGHT, State.PRESSED));
        environment.getTimer().advance(100);
        input.notify(Event.create(Code.LEFT, State.RELEASED));

        long timestamp = input.extractEvent().getTimestamp();
        assertTrue(timestamp < input.extractEvent().getTimestamp());
    }


    public void testComplexInputSequence()
    {
        input.notify(Event.create(Code.UP, State.PRESSED));
        input.notify(Event.create(Code.DOWN, State.PRESSED));
        input.notify(Event.create(Code.UP, State.RELEASED));
        input.notify(Event.create(Code.DOWN, State.RELEASED));

        Event event;

        event = input.extractEvent();
        assertTrue(event.is(Code.UP));
        assertTrue(event.isPressed());

        event = input.extractEvent();
        assertTrue(event.is(Code.DOWN));
        assertTrue(event.isPressed());

        event = input.extractEvent();
        assertTrue(event.is(Code.UP));
        assertTrue(event.isReleased());

        event = input.extractEvent();
        assertTrue(event.is(Code.DOWN));
        assertTrue(event.isReleased());
    }


    public void testnotifyEscape()
    {
        input.notify(Event.create(Code.RIGHT, State.PRESSED));
        input.notify(Event.create(Code.ESCAPE, State.PRESSED));

        assertTrue(input.extractEvent().is(Code.RIGHT));
    }


    public void testNotifyEscape()
    {
        input.notify(Event.create(Code.ESCAPE, State.PRESSED));

        assertTrue(input.extractEvent().is(Code.ESCAPE));
    }


    public void testEventMappingsSetsOK()
    {
        input.setEventMappings(null);
        assertTrue(input.isEmpty());
        input.setEventMappings(createEventMappings());
        input.notify(Event.create(Code.LEFT, State.PRESSED));
        assertFalse(input.isEmpty());
    }


    public void testUpdateCorrectlyHandled()
    {
        assertFalse(mockKeyboard.updated());
        input.update();
        assertTrue(mockKeyboard.updated());
    }

}
