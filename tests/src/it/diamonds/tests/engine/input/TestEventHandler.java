package it.diamonds.tests.engine.input;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Timer;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEventHandler;


public class TestEventHandler extends EnvironmentTestCase
{

    private Input input;

    private InputReactor reactor;

    private MockEventHandler handler;

    private Timer timer;


    public void setUp()
    {
        super.setUp();
        initFields(environment);
        initReactor(environment);

        input.setEventMappings(createEventMappings());
        input.notify(Event.create(Code.ENTER, State.PRESSED));
    }


    private void initReactor(Environment environment)
    {
        int normalRepeatDelay = environment.getConfig().getInteger("NormalRepeatDelay");
        int fastRepeatDelay = environment.getConfig().getInteger("FastRepeatDelay");
        reactor = new InputReactor(input, normalRepeatDelay, fastRepeatDelay);
        reactor.addHandler(Code.ENTER, handler);
    }


    private void initFields(Environment environment)
    {
        timer = environment.getTimer();
        handler = MockEventHandler.create();
        input = Input.create(environment.getKeyboard(), environment.getTimer());
    }


    public void testNormalRepeatDelaySet()
    {
        handler.setNormalRepeatDelay(100);
        assertEquals(100, handler.getNormalRepeatDelay());
    }


    public void testFastRepeatDelaySet()
    {
        handler.setFastRepeatDelay(100);
        assertEquals(100, handler.getFastRepeatDelay());
    }


    public void testPressed()
    {
        handler.handleEvent(reactor, Event.create(Code.ENTER, State.PRESSED));
        assertTrue(handler.isPressed());
    }


    public void testReleased()
    {
        handler.handleEvent(reactor, Event.create(Code.ENTER, State.PRESSED));
        handler.handleEvent(reactor, Event.create(Code.ENTER, State.RELEASED));
        assertFalse(handler.isPressed());
    }


    public void testHandlerNotRepeated()
    {
        reactToInput();
        assertFalse(handler.isRepeated(0));
    }


    private void reactToInput()
    {
        reactor.reactToInput(timer.getTime());
    }


    public void testHandlerIsRepeatedAfterDelay()
    {
        reactToInput();
        assertTrue(handler.isRepeated(handler.getNormalRepeatDelay() + 1));
    }


    public void testRepeatDelayIsNotChanging()
    {
        reactToInput();
        assertEquals(handler.getNormalRepeatDelay(), handler.getCurrentRepeatDelay());
    }


    public void testHandlerIsRepeatedAfterTwoInputReaction()
    {
        reactTwiceToInput();
        assertTrue(handler.isRepeated(0));
    }


    public void testFastRepetitionSetAfterTwoInputReaction()
    {
        reactTwiceToInput();
        assertEquals(handler.getFastRepeatDelay(), handler.getCurrentRepeatDelay());
    }


    public void testHandlerExecutedAfterTwoInputReaction()
    {
        reactTwiceToInput();
        assertTrue(handler.executed());
    }


    public void testHandlerPressedAfterTwoInputReaction()
    {
        reactTwiceToInput();
        assertTrue(handler.pressed());
    }


    public void testRepetitionDeactivatedWhenReleased()
    {
        reactTwiceToInput();
        timer.advance(handler.getNormalRepeatDelay() + 1);
        input.notify(Event.create(Code.ENTER, State.RELEASED));
        reactToInput();
        assertFalse(handler.isRepeated(0));
    }


    private void reactTwiceToInput()
    {
        reactToInput();
        timer.advance(handler.getNormalRepeatDelay() + 1);
        reactToInput();
    }
}
