package it.diamonds.tests.engine.input;


import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Keyboard;
import it.diamonds.engine.input.Listener;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.mocks.MockKeyboard;
import junit.framework.TestCase;


public class TestListener extends TestCase implements Listener
{

    private Event notifiedEvent;


    public void testNotifyCorrectEventToListners()
    {
        Keyboard keyboard = MockKeyboard.create();

        keyboard.setListener(this);
        keyboard.notify(Event.create(Code.KEY_A, State.PRESSED));
        assertTrue(notifiedEvent.is(Code.KEY_A));
        assertTrue(notifiedEvent.isPressed());
    }


    public void notify(Event event)
    {
        notifiedEvent = event;
    }

}
