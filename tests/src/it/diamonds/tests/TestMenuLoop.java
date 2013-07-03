package it.diamonds.tests;


import it.diamonds.MenuLoop;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.engine.video.Background;
import it.diamonds.engine.video.Image;
import it.diamonds.menu.MainMenu;
import it.diamonds.menu.MenuItem;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockMenuAction;

import java.io.IOException;


public class TestMenuLoop extends EnvironmentTestCase
{
    private MenuLoop menuLoop;


    protected void setUp()
    {
        super.setUp();
        menuLoop = new MenuLoop(environment);
    }


    public void testSetKeyBoardListenereOnCreate()
    {
        mockKeyboard.isListenerRegistred(menuLoop);
    }


    public void testMainMenuNotNullOnCreation()
    {
        assertNotNull(menuLoop.getMainMenu());
    }


    public void testVersusModeSelectedOnCreation()
    {
        assertEquals(menuLoop.getMainMenu().getSelectedItem(), MenuItem.VERSUS_MODE);
    }


    public void testLayerContainMainMenuSprite()
    {
        Image texture = menuLoop.getMainMenu().getSprite().getTexture();
        menuLoop.getLayerManager().drawLayers(environment.getEngine());
        assertTrue(mockEngine.wasImageDrawn(texture));
    }


    public void testEscKeyNotStopMenuLoop()
    {
        menuLoop.notify(Event.create(Code.KEY_ESCAPE, State.RELEASED));
        assertFalse("The MenuLoop must be running if key is pressed before start the game loop", menuLoop.isFinished());
    }


    /*
     * One of several tests that now reflects a behavior verified in a specific menu
     * selection
     */
    public void testEnterOnVersusModeItemFinishMenuLoop() throws IOException
    {
        menuLoop.notify(Event.create(Code.KEY_ENTER, State.RELEASED));

        assertTrue(menuLoop.isFinished());
    }


    /*
     * One of several tests that now reflects a behavior verified in a specific menu
     * selection
     */
    public void testEnterOnQuitItemFinishMenuLoop() throws IOException
    {
        menuLoop.getMainMenu().selectMenuItem(MenuItem.QUIT);

        menuLoop.notify(Event.create(Code.KEY_ENTER, State.RELEASED));

        assertTrue("The GameLoop must be stopped if key is pressed before start the game loop", menuLoop.isFinished());
    }


    public void testMenuBackgroundIsShown()
    {
        environment.getTimer().advance(getFrameRate());
        menuLoop.loopStep();

        Background background = new Background(environment, "gfx/common/main.jpg");

        Image texture = background.getSprite().getTexture();

        assertTrue(((MockEngine)environment.getEngine()).wasImageDrawn(texture));
    }


    public void testMainMenuIsShown()
    {
        environment.getTimer().advance(getFrameRate());
        menuLoop.loopStep();

        Image texture = environment.getEngine().createImage("gfx/common/main_menu");

        assertTrue(((MockEngine)environment.getEngine()).wasImageDrawn(texture));
    }


    public void testMainMenuReactionToDownInput()
    {
        menuLoop.notify(Event.create(Code.KEY_DOWN, State.PRESSED));
        assertEquals(MenuItem.QUIT, menuLoop.getMainMenu().getSelectedItem());
    }


    public void testMainMenuReactionToUpInput()
    {
        MainMenu mainMenu = menuLoop.getMainMenu();
        mainMenu.selectMenuItem(MenuItem.QUIT);

        menuLoop.notify(Event.create(Code.KEY_UP, State.PRESSED));
        assertEquals(MenuItem.VERSUS_MODE, mainMenu.getSelectedItem());
    }


    public void testFromLowerMenuItemToHigherMenuItem()
    {
        MainMenu mainMenu = menuLoop.getMainMenu();
        mainMenu.selectMenuItem(MenuItem.QUIT);

        menuLoop.notify(Event.create(Code.KEY_DOWN, State.PRESSED));
        assertEquals(MenuItem.VERSUS_MODE, mainMenu.getSelectedItem());
    }


    public void testFromHigherMenuItemToLowerMenuItem()
    {
        menuLoop.notify(Event.create(Code.KEY_UP, State.PRESSED));
        assertEquals(MenuItem.QUIT, menuLoop.getMainMenu().getSelectedItem());
    }


    public void testChangeMenuItemPressAndRealease()
    {
        menuLoop.notify(Event.create(Code.KEY_DOWN, State.PRESSED));
        menuLoop.notify(Event.create(Code.KEY_DOWN, State.RELEASED));

        assertEquals(MenuItem.QUIT, menuLoop.getMainMenu().getSelectedItem());

        menuLoop.notify(Event.create(Code.KEY_UP, State.PRESSED));
        menuLoop.notify(Event.create(Code.KEY_UP, State.RELEASED));

        assertEquals(MenuItem.VERSUS_MODE, menuLoop.getMainMenu().getSelectedItem());
    }


    public void testNotSelectMenuItemWithEnterPressed() throws IOException
    {
        MainMenu mainMenu = menuLoop.getMainMenu();
        MockMenuAction mockMenuAction = new MockMenuAction();
        mainMenu.selectMenuItem(new MenuItem(0, mockMenuAction, "MOCK"));

        menuLoop.notify(Event.create(Code.KEY_ENTER, State.PRESSED));

        assertFalse(mockMenuAction.isExecuted());
    }


    public void testSelectMenuItemWithEnterReleased() throws IOException
    {
        MainMenu mainMenu = menuLoop.getMainMenu();
        MockMenuAction mockMenuAction = new MockMenuAction();
        mainMenu.selectMenuItem(new MenuItem(0, mockMenuAction, "MOCK"));

        assertFalse(mockMenuAction.isExecuted());

        menuLoop.notify(Event.create(Code.KEY_ENTER, State.RELEASED));

        assertTrue(mockMenuAction.isExecuted());
    }
}
