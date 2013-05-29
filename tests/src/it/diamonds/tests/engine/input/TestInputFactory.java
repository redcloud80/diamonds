package it.diamonds.tests.engine.input;


import it.diamonds.engine.Config;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.TestCase;


public class TestInputFactory extends TestCase
{
    private Config config;

    private InputFactory inputFactory;


    @Override
    protected void setUp() throws Exception
    {
        Environment environment = MockEnvironment.create();

        config = environment.getConfig();
        inputFactory = new InputFactory(environment);
    }


    public void testCreateInputForPlayerOne()
    {
        assertTrue(inputFactory.createInputForPlayerOne() instanceof Input);
    }


    public void testCreateInputForPlayerTwo()
    {
        assertTrue(inputFactory.createInputForPlayerTwo() instanceof Input);
    }


    public void testPlayerOneUsesP1EventMappings()
    {
        Input input = inputFactory.createInputForPlayerOne();
        EventMappings mappings = input.getEventMappings();

        assertEquals(EventMappings.createForPlayerOne(config), mappings);
    }


    public void testPlayerTwoUsesP2EventMappings()
    {
        Input input = inputFactory.createInputForPlayerTwo();
        EventMappings mappings = input.getEventMappings();

        assertEquals(EventMappings.createForPlayerTwo(config), mappings);
    }
}
