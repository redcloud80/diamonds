package it.diamonds.menu;


import it.diamonds.AbstractLoop;
import it.diamonds.engine.Environment;


public class MenuActionQuit implements MenuAction
{
    public void execute(AbstractLoop loop, Environment environment)
    {
        loop.exitLoop();
    }
}
