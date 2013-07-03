package it.diamonds.menu;


import it.diamonds.AbstractLoop;
import it.diamonds.engine.Environment;


public interface MenuAction
{
    void execute(AbstractLoop loop, Environment environment);
}
