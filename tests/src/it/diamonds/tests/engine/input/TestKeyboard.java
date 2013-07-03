package it.diamonds.tests.engine.input;


import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Listener;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockListener;

import java.util.ConcurrentModificationException;


public class TestKeyboard extends EnvironmentTestCase
{
    private MockListener listener;

    private Event event;


    @Override
    protected void setUp()
    {
        super.setUp();
        listener = new MockListener();
        event = Event.create(Code.KEY_ENTER, State.RELEASED);
    }


    public void testShuttedDown()
    {
        assertTrue(mockKeyboard.isCreated());
        environment.getKeyboard().shutDown();
        assertFalse(mockKeyboard.isCreated());
    }


    public void testStatePressed()
    {
        assertEquals(State.PRESSED, mockKeyboard.getState(true));
    }


    public void testStateReleased()
    {
        assertEquals(State.RELEASED, mockKeyboard.getState(false));
    }


    public void testAddListener()
    {
        mockKeyboard.setListener(listener);

        assertTrue(mockKeyboard.isListenerRegistred(listener));
    }


    public void testAddTwoListener()
    {
        mockKeyboard.setListener(listener);

        Listener listener2 = new MockListener();
        mockKeyboard.setListener(listener2);

        assertTrue(mockKeyboard.isListenerRegistred(listener));
        assertTrue(mockKeyboard.isListenerRegistred(listener2));
    }


    public void testNotifyListener()
    {
        mockKeyboard.setListener(listener);

        mockKeyboard.notify(event);

        assertEquals(event, listener.getLastEvent());
    }


    public void testNotifyTwoListener()
    {
        mockKeyboard.setListener(listener);

        MockListener listener2 = new MockListener();
        mockKeyboard.setListener(listener2);

        mockKeyboard.notify(event);

        assertEquals(event, listener.getLastEvent());
        assertEquals(event, listener2.getLastEvent());
    }


    public void testSecondNotifyListener()
    {
        mockKeyboard.setListener(listener);

        mockKeyboard.notify(event);

        event = Event.create(Code.KEY_ENTER, State.PRESSED);

        mockKeyboard.notify(event);

        assertEquals(event, listener.getLastEvent());
    }


    public void testNotifyListenerThatAddListener()
    {

        Listener listener2 = new Listener()
        {
            public void notify(Event event)
            {
                mockKeyboard.setListener(listener);

            }
        };

        mockKeyboard.setListener(listener2);

        try
        {
            mockKeyboard.notify(event);
        }
        catch (ConcurrentModificationException e)
        {
            fail("must no throw ConcurrentModificationException");
        }
    }
}
