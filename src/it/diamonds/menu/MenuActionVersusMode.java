package it.diamonds.menu;


import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;


public class MenuActionVersusMode implements MenuAction
{
    public void execute(AbstractLoop loop, Environment environment)
    {
        loop.setNextLoop(new GameLoop(environment));
        loop.exitLoop();
    }
}
