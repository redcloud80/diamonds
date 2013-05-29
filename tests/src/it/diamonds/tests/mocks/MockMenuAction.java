package it.diamonds.tests.mocks;


import it.diamonds.AbstractLoop;
import it.diamonds.engine.Environment;
import it.diamonds.menu.MenuAction;


public class MockMenuAction implements MenuAction
{
    private boolean isExecuted = false;


    public void execute(AbstractLoop loop, Environment env)
    {
        isExecuted = true;
    }


    public boolean isExecuted()
    {
        return isExecuted;
    }
}
