package it.diamonds.menu;


import it.diamonds.MenuLoop;
import it.diamonds.engine.Environment;


public class MenuItem
{
    private int index;

    private String name;

    private MenuAction action;

    public MenuItem(int index, MenuAction action, String name)
    {
        this.index = index;
        this.action = action;
        this.name = name;
    }


    public final int index()
    {
        return this.index;
    }


    public MenuAction getAction()
    {
        return this.action;
    }


    public void execute(MenuLoop gameLoop, Environment environment)
    {
        getAction().execute(gameLoop, environment);
    }


    // Needed by JUnit to print out the name of the object
    public String toString()
    {
        return name;
    }
}
