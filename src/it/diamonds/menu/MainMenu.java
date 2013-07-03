package it.diamonds.menu;


import it.diamonds.MenuLoop;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.Sprite;


public class MainMenu
{
    private Sprite sprite;

    private MenuItem selectedMenuItem;

    private Environment environment;


    public MainMenu(Environment environment)
    {
        Rectangle rectangle = new Rectangle(0, 0, 512, 60);
        Image texture = environment.getEngine().createImage("gfx/common/main_menu");

        sprite = new Sprite(0f, 227f, rectangle, texture);

        MenuItem.initialiseMenuItems();

        selectedMenuItem = MenuItem.STORY_MODE;

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
        selectedMenuItem = selectedMenuItem.nextItem();
        moveMenuSprite(selectedMenuItem.index());
    }


    public void selectPreviousItem()
    {
        selectedMenuItem = selectedMenuItem.previousItem();

        moveMenuSprite(selectedMenuItem.index());
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
