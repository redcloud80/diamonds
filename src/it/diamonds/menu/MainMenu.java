package it.diamonds.menu;


import it.diamonds.MenuLoop;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.Sprite;

import java.util.HashMap;


public class MainMenu
{
    public static final MenuItem STORY_MODE = new MenuItem(0, new MenuActionNone(), "STORY MODE");

    public static final MenuItem VERSUS_MODE = new MenuItem(1, new MenuActionVersusMode(), "VERSUS MODE");

    public static final MenuItem NETPLAY = new MenuItem(2, new MenuActionNetPlay(), "NETPLAY");

    public static final MenuItem OPTIONS = new MenuItem(4, new MenuActionNone(), "OPTIONS");

    public static final MenuItem QUIT = new MenuItem(5, new MenuActionQuit(), "QUIT");
    
    private Sprite sprite;

    private MenuItem selectedMenuItem;

    private Environment environment;
    
    private HashMap<MenuItem, MenuItem> previousItems = new HashMap<MenuItem, MenuItem> ();
    private HashMap<MenuItem, MenuItem> nextItems = new HashMap<MenuItem, MenuItem> ();


    public MainMenu(Environment environment)
    {
        Rectangle rectangle = new Rectangle(0, 0, 512, 60);
        Image texture = environment.getEngine().createImage("gfx/common/main_menu");

        sprite = new Sprite(new Point(0f, 227f), rectangle, texture);

        previousItems.put(VERSUS_MODE, QUIT);
        previousItems.put(NETPLAY, VERSUS_MODE);
        previousItems.put(QUIT, NETPLAY);
        
        nextItems.put(QUIT, VERSUS_MODE);
        nextItems.put(NETPLAY, QUIT);
        nextItems.put(VERSUS_MODE, NETPLAY);
        
        selectedMenuItem = STORY_MODE;

        this.environment = environment;
    }


    public Sprite getSprite()
    {
        return sprite;
    }


    private void moveMenuSprite(int index)
    {
        int offSet = 60 * index;
        sprite.setOrigin(0, offSet);
        sprite.setPosition(0f, offSet + 227f);
    }


    public void selectMenuItem(MenuItem menuItem)
    {
        this.selectedMenuItem = menuItem;
        moveMenuSprite(menuItem.index());
    }


    public void selectNextItem()
    {
        selectMenuItem(nextItems.get(selectedMenuItem));
    }


    public void selectPreviousItem()
    {
        selectMenuItem(previousItems.get(selectedMenuItem));
    }


    public MenuItem getSelectedItem()
    {
        return selectedMenuItem;
    }


    public void executeSelectedItem(MenuLoop loop)
    {
        selectedMenuItem.execute(loop, environment);
    }
}
