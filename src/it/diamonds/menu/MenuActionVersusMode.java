package it.diamonds.menu;


import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.InputFactory;


public class MenuActionVersusMode implements MenuAction
{
    public void execute(AbstractLoop loop, Environment environment)
    {
        InputFactory inputFactory = new InputFactory(environment);
        
        loop.setNextLoop(new GameLoop(environment, inputFactory));
        loop.exitLoop();
    }
}
