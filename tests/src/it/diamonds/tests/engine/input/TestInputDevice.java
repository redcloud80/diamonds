package it.diamonds.tests.engine.input;


import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.tests.EnvironmentTestCase;


public class TestInputDevice extends EnvironmentTestCase
{

    public void testAddOneInput()
    {
        EventMappings keyMappings = EventMappings.create();

        Input input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(keyMappings);

        keyMappings.addMapping(Code.KEY_A, Code.KEY_Z);

        environment.getKeyboard().notify(Event.create(Code.KEY_A, State.PRESSED));
        assertTrue(input.extractEvent().is(Code.KEY_Z));
    }


    public void testAddMultipletInput()
    {
        EventMappings keyMappings1 = EventMappings.create();
        EventMappings keyMappings2 = EventMappings.create();
        EventMappings keyMappings3 = EventMappings.create();

        Input input1 = Input.create(environment.getKeyboard(), environment.getTimer());
        Input input2 = Input.create(environment.getKeyboard(), environment.getTimer());
        Input input3 = Input.create(environment.getKeyboard(), environment.getTimer());

        input1.setEventMappings(keyMappings1);
        input2.setEventMappings(keyMappings2);
        input3.setEventMappings(keyMappings3);

        keyMappings1.addMapping(Code.KEY_A, Code.KEY_X);
        keyMappings2.addMapping(Code.KEY_B, Code.KEY_Y);
        keyMappings3.addMapping(Code.KEY_C, Code.KEY_Z);

        environment.getKeyboard().notify(Event.create(Code.KEY_A, State.PRESSED));
        environment.getKeyboard().notify(Event.create(Code.KEY_B, State.PRESSED));
        environment.getKeyboard().notify(Event.create(Code.KEY_C, State.PRESSED));

        assertTrue(input1.extractEvent().is(Code.KEY_X));
        assertTrue(input3.extractEvent().is(Code.KEY_Z));
        assertTrue(input2.extractEvent().is(Code.KEY_Y));
    }


    public void testUpdateCorrectlyHandled()
    {
        assertFalse(mockKeyboard.updated());
        environment.getKeyboard().update();
        assertTrue(mockKeyboard.updated());
    }
}
