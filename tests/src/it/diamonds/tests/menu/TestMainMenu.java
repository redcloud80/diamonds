package it.diamonds.tests.menu;


import it.diamonds.engine.Point;
import it.diamonds.menu.MainMenu;
import it.diamonds.menu.MenuActionNetPlay;
import it.diamonds.menu.MenuActionNone;
import it.diamonds.menu.MenuActionQuit;
import it.diamonds.menu.MenuActionVersusMode;
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
        mainMenu.selectMenuItem(MainMenu.STORY_MODE);

        assertEquals(0, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectStoryModePosition()
    {
        mainMenu.selectMenuItem(MainMenu.STORY_MODE);

        assertEquals(227.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectVersusModeRectangle()
    {
        mainMenu.selectMenuItem(MainMenu.VERSUS_MODE);

        assertEquals(60, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectVersusModePosition()
    {
        mainMenu.selectMenuItem(MainMenu.VERSUS_MODE);

        assertEquals(287.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectOptionsRectangle()
    {
        mainMenu.selectMenuItem(MainMenu.OPTIONS);

        assertEquals(240, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectOptionsPosition()
    {
        mainMenu.selectMenuItem(MainMenu.OPTIONS);

        assertEquals(467.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testSelectQuitRectangle()
    {
        mainMenu.selectMenuItem(MainMenu.QUIT);

        assertEquals(300, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testSelectQuitPosition()
    {
        mainMenu.selectMenuItem(MainMenu.QUIT);

        assertEquals(527.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testRectangleSelectingQuitAndStoryMode()
    {
        mainMenu.selectMenuItem(MainMenu.QUIT);
        mainMenu.selectMenuItem(MainMenu.STORY_MODE);

        assertEquals(0, mainMenu.getSprite().getTextureArea().getTop());
    }


    public void testPositionSelectingQuitAndStoryMode()
    {
        mainMenu.selectMenuItem(MainMenu.QUIT);
        mainMenu.selectMenuItem(MainMenu.STORY_MODE);

        assertEquals(227.0f, mainMenu.getSprite().getPosition().getY(), 0.001f);
    }


    public void testGetSelectedMenuItem()
    {
        mainMenu.selectMenuItem(MainMenu.STORY_MODE);
        assertEquals(MainMenu.STORY_MODE, mainMenu.getSelectedItem());

        mainMenu.selectMenuItem(MainMenu.VERSUS_MODE);
        assertEquals(MainMenu.VERSUS_MODE, mainMenu.getSelectedItem());

    }


    public void testMenuItemString()
    {
        assertEquals("STORY MODE", MainMenu.STORY_MODE.toString());
        assertEquals("VERSUS MODE", MainMenu.VERSUS_MODE.toString());
        assertEquals("NETPLAY", MainMenu.NETPLAY.toString());
        assertEquals("OPTIONS", MainMenu.OPTIONS.toString());
        assertEquals("QUIT", MainMenu.QUIT.toString());
    }
    
    
    public void testMenuItemIndex()
    {
        assertEquals(0, MainMenu.STORY_MODE.index());
        assertEquals(1, MainMenu.VERSUS_MODE.index());
        assertEquals(2, MainMenu.NETPLAY.index());
        assertEquals(4, MainMenu.OPTIONS.index());
        assertEquals(5, MainMenu.QUIT.index());
    }    
    
    
    public void testMenuItemAction()
    {
        assertTrue ( MainMenu.STORY_MODE.getAction() instanceof MenuActionNone);
        assertTrue ( MainMenu.VERSUS_MODE.getAction() instanceof MenuActionVersusMode);
        assertTrue ( MainMenu.NETPLAY.getAction() instanceof MenuActionNetPlay);
        assertTrue ( MainMenu.OPTIONS.getAction() instanceof MenuActionNone);
        assertTrue ( MainMenu.QUIT.getAction() instanceof MenuActionQuit);   
    }    


    public void testNetplayCreation()
    {
        assertEquals(2, MainMenu.NETPLAY.index());
    }


    public void testNetplayIsVersusModeNextItem()
    {
        mainMenu.selectMenuItem(MainMenu.VERSUS_MODE);
        mainMenu.selectNextItem();

        assertEquals(MainMenu.NETPLAY, mainMenu.getSelectedItem());
    }


    public void testNetplayIsQuitPreviousItem()
    {
        mainMenu.selectMenuItem(MainMenu.QUIT);
        mainMenu.selectPreviousItem();

        assertEquals(MainMenu.NETPLAY, mainMenu.getSelectedItem());
    }


    public void testVersusModeIsNetplayPreviousItem()
    {
        mainMenu.selectMenuItem(MainMenu.NETPLAY);
        mainMenu.selectPreviousItem();

        assertEquals(MainMenu.VERSUS_MODE, mainMenu.getSelectedItem());
    }


    public void testQuitIsNetplayNextItem()
    {
        mainMenu.selectMenuItem(MainMenu.NETPLAY);
        mainMenu.selectNextItem();

        assertEquals(MainMenu.QUIT, mainMenu.getSelectedItem());
    }
}
