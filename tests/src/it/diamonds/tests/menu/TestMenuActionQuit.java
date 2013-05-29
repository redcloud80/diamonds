package it.diamonds.tests.menu;


import it.diamonds.menu.MenuActionQuit;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockLoop;


public class TestMenuActionQuit extends EnvironmentTestCase
{

    public void testLoopIsFinished()
    {
        MockLoop loop = new MockLoop(environment);
        new MenuActionQuit().execute(loop, environment);
        assertTrue(loop.isFinished());
    }
}
