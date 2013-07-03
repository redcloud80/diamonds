package it.diamonds;


import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.Listener;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.menu.MainMenu;
import it.diamonds.menu.MenuItem;


public class MenuLoop extends AbstractLoop implements Listener
{
    private static final String[] TEXTURE_LIST = { COMMON + "main.jpg",
        COMMON + "main_menu" };

    private Input menuInput;

    private EventMappings eventMappings;

    private MainMenu mainMenu;


    public MenuLoop(Environment environment)
    {
        super(environment, TEXTURE_LIST, "gfx/common/main.jpg");
        setKeyboardListener();
        setupMainMenu(environment);
    }


    private void setKeyboardListener()
    {
        environment.getKeyboard().setListener(this);
    }


    private void setupMainMenu(Environment environment)
    {
        mainMenu = new MainMenu(environment);
        mainMenu.selectMenuItem(MenuItem.VERSUS_MODE);

        layerManager.addSimpleLayer(mainMenu.getSprite());
    }


    @Override
    protected void createEventMappings()
    {
        eventMappings = EventMappings.createForMenuLoop(environment.getConfig());
    }


    @Override
    protected void createInput()
    {
        menuInput = Input.create(environment.getKeyboard(), environment.getTimer());
        menuInput.setEventMappings(eventMappings);
    }


    @Override
    protected void updateState()
    {
        ;
    }


    public void notify(Event event)
    {
        Event myEvent = event.copyAndChange(eventMappings.translateEvent(event.getCode()));

        if (myEvent.isPressed())
        {
            if (myEvent.is(Code.DOWN))
            {
                mainMenu.selectNextItem();
            }
            if (myEvent.is(Code.UP))
            {
                mainMenu.selectPreviousItem();
            }
        }

        if (myEvent.is(Code.ENTER) && myEvent.isReleased())
        {
            mainMenu.executeSelectedItem(this);
        }
    }


    // for tests
    public MainMenu getMainMenu()
    {
        return mainMenu;
    }
}
