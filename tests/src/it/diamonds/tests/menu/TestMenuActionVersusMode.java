package it.diamonds.tests.menu;


import it.diamonds.GameLoop;
import it.diamonds.menu.MenuActionVersusMode;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockLoop;


public class TestMenuActionVersusMode extends EnvironmentTestCase
{
    private MenuActionVersusMode actionVersusMode;

    private MockLoop loop;


    @Override
    protected void setUp()
    {
        super.setUp();
        actionVersusMode = new MenuActionVersusMode();
        loop = new MockLoop(environment);
    }


    public void testLoopIsFinished()
    {
        actionVersusMode.execute(loop, environment);
        assertTrue(loop.isFinished());
    }


    public void testLoopHaveGameLoopAsNextLoop()
    {
        actionVersusMode.execute(loop, environment);
        assertTrue(loop.getNextLoop() instanceof GameLoop);
    }
}
