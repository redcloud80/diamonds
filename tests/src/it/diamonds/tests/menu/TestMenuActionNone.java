package it.diamonds.tests.menu;


import it.diamonds.menu.MenuActionNone;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockLoop;


public class TestMenuActionNone extends EnvironmentTestCase
{

    public void testLoopIsFinished()
    {
        MockLoop loop = new MockLoop(environment);
        new MenuActionNone().execute(loop, environment);
        assertFalse(loop.isFinished());
        assertNull(loop.getNextLoop());
    }
}
