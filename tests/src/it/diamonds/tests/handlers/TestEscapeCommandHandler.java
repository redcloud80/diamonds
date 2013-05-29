package it.diamonds.tests.handlers;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.handlers.EscapeCommandHandler;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.TestCase;


public class TestEscapeCommandHandler extends TestCase
{
    private Environment environment;

    private Input input;

    private InputReactor inputReactor;

    private GameLoop gameLoop;


    protected void setUp() throws Exception
    {
        super.setUp();

        environment = MockEnvironment.create();
        gameLoop = new GameLoop(environment, new InputFactory(environment));
        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());

        inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        inputReactor.addHandler(Code.ESCAPE, new EscapeCommandHandler(gameLoop));
    }


    public void testGameExit()
    {
        input.notify(Event.create(Code.ESCAPE, State.PRESSED));
        input.notify(Event.create(Code.ESCAPE, State.RELEASED));

        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("The GameLoop must be stopped", gameLoop.isFinished());
    }


    public void testGameNotExit()
    {
        input.notify(Event.create(Code.ESCAPE, State.PRESSED));
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertFalse("The GameLoop must be running", gameLoop.isFinished());
    }

}
