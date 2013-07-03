package it.diamonds.tests.menu;


import it.diamonds.engine.Point;
import it.diamonds.menu.MainMenu;
import it.diamonds.menu.MenuItem;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.TestCase;


public class TestMainMenu extends TestCase
{
    private MainMenu mainMenu;


    public void setUp()
    {
        mainMenu = new MainMenu(MockEnvironment.create(800, 600));
    }


    public void testMainMenuSprite()
    {
        assertEquals("gfx/common/main_menu", mainMenu.getSprite().getTexture().getName());
    }


    public void testMainMenuPosition()
    {
        Point position = new Point(0, 227);

        assertEquals(position.getX(), mainMenu.getSprite().getPosition().getX());
        assertEquals(position.getY(), mainMenu.getSprite().getPosition().getY());
    }


    public void testMainMenuWidth()
    {
        assertEquals(512, mainMenu.getSprite().getTextureArea().getWidth());
    }


    public void testMainMenuHeight()
    {
        assertEquals(60, mainMenu.getSprite().getTextureArea().getHeight());
    }


    public void testSelectStoryModeRectangle()
    {
        mainMenu.selectMenuItem(MenuItem.STORY_MODE);

        assertEquals(0, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectStoryModePosition()
    {
        mainMenu.selectMenuItem(MenuItem.STORY_MODE);

        assertEquals(227.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectVersusModeRectangle()
    {
        mainMenu.selectMenuItem(MenuItem.VERSUS_MODE);

        assertEquals(60, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectVersusModePosition()
    {
        mainMenu.selectMenuItem(MenuItem.VERSUS_MODE);

        assertEquals(287.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectOptionsRectangle()
    {
        mainMenu.selectMenuItem(MenuItem.OPTIONS);

        assertEquals(240, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectOptionsPosition()
    {
        mainMenu.selectMenuItem(MenuItem.OPTIONS);

        assertEquals(467.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectQuitRectangle()
    {
        mainMenu.selectMenuItem(MenuItem.QUIT);

        assertEquals(300, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectQuitPosition()
    {
        mainMenu.selectMenuItem(MenuItem.QUIT);

        assertEquals(527.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testRectangleSelectingQuitAndStoryMode()
    {
        mainMenu.selectMenuItem(MenuItem.QUIT);
        mainMenu.selectMenuItem(MenuItem.STORY_MODE);

        assertEquals(0, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testPositionSelectingQuitAndStoryMode()
    {
        mainMenu.selectMenuItem(MenuItem.QUIT);
        mainMenu.selectMenuItem(MenuItem.STORY_MODE);

        assertEquals(227.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testGetSelectedMenuItem()
    {
        mainMenu.selectMenuItem(MenuItem.STORY_MODE);
        assertEquals(MenuItem.STORY_MODE, mainMenu.getSelectedItem());

        mainMenu.selectMenuItem(MenuItem.VERSUS_MODE);
        assertEquals(MenuItem.VERSUS_MODE, mainMenu.getSelectedItem());

    }


    public void testMenuItemString()
    {
        assertEquals("STORY MODE", MenuItem.STORY_MODE.toString());
        assertEquals("VERSUS MODE", MenuItem.VERSUS_MODE.toString());
        assertEquals("OPTIONS", MenuItem.OPTIONS.toString());
        assertEquals("QUIT", MenuItem.QUIT.toString());
        assertEquals("BACK", MenuItem.BACK.toString());
    }
}
