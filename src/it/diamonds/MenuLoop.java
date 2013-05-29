package it.diamonds;


import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Listener;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.menu.MainMenu;


public class MenuLoop extends AbstractLoop implements Listener
{
    private static final String[] TEXTURE_LIST = { COMMON + "main.jpg",
        COMMON + "main_menu" };

    private EventMappings eventMappings;

    private MainMenu mainMenu;


    public MenuLoop(Environment environment)
    {
        super(environment, TEXTURE_LIST, "gfx/common/main.jpg");
        
        createEventMappings();
        environment.getKeyboard().setListener(this);
        
        mainMenu = new MainMenu(environment);
        mainMenu.selectMenuItem(MainMenu.VERSUS_MODE);
        
        layerManager.addSimpleLayer(mainMenu.getSprite());
    }


    private void createEventMappings()
    {
        eventMappings = EventMappings.createForMenuLoop();
    }


    @Override
    protected void updateState()
    {
        ;
    }
    
    
    protected void processInput()
    {
        environment.getKeyboard().update();
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
    
    
    public final void exitLoop()
    {
        environment.getKeyboard().removeListener(this);
        super.exitLoop();
    }


    // for tests
    public MainMenu getMainMenu()
    {
        return mainMenu;
    }
}
