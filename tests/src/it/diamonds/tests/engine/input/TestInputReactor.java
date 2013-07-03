package it.diamonds.tests.engine.input;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEventHandler;


public class TestInputReactor extends EnvironmentTestCase
{
    private Input input;

    private InputReactor reactor;

    private MockEventHandler handler;


    public void setUp()
    {
        super.setUp();
        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());
        reactor = new InputReactor(input, 50, 60);
        handler = MockEventHandler.create();
        reactor.addHandler(Code.ENTER, handler);
    }


    public void testRepeatitionDelaysSet()
    {
        assertEquals(50, reactor.getNormalRepeatDelay());
        assertEquals(60, reactor.getFastRepeatDelay());
    }


    public void testHandlerSet()
    {
        assertEquals(handler, reactor.getEventHandler(Code.ENTER));
    }


    public void testNoHandlerSet()
    {
        reactor = new InputReactor(input, 50, 60);
        assertNull(null, reactor.getEventHandler(Code.ENTER));
    }


    public void testHandlerRepeationDelaysSameAsInputReactor()
    {
        assertEquals(reactor.getNormalRepeatDelay(), handler.getNormalRepeatDelay());
        assertEquals(reactor.getFastRepeatDelay(), handler.getFastRepeatDelay());
    }


    public void testHandlerPressed()
    {
        input.notify(Event.create(Code.ENTER, State.PRESSED));
        reactor.reactToInput(environment.getTimer().getTime());
        assertTrue(handler.executed());
        assertTrue(handler.pressed());
    }


    public void testHandlerReleased()
    {
        input.notify(Event.create(Code.ENTER, State.RELEASED));
        reactor.reactToInput(environment.getTimer().getTime());
        assertTrue(handler.executed());
        assertFalse(handler.pressed());
    }


    public void testInputTimeCorrect()
    {
        input.notify(Event.create(Code.ENTER, State.PRESSED));
        long time = environment.getTimer().getTime();
        reactor.reactToInput(time);
        environment.getTimer().advance(100);
        input.notify(Event.create(Code.ENTER, State.RELEASED));
        reactor.reactToInput(environment.getTimer().getTime());
        assertTrue(time < reactor.getLastInputTimeStamp());
    }


    public void testEmptyQueue()
    {
        input.notify(Event.create(Code.ENTER, State.PRESSED));
        reactor.emptyQueue();
        reactor.reactToInput(environment.getTimer().getTime());
        assertFalse(handler.executed());
    }
}
