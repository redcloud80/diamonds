package it.diamonds;


import it.diamonds.engine.Environment;
import it.diamonds.engine.video.Background;
import it.diamonds.engine.video.LayerManager;


public abstract class AbstractLoop
{
    protected static final String GFX = "gfx/";

    protected static final String COMMON = GFX + "common/";

    protected Environment environment;

    protected LayerManager layerManager;

    protected long loopTimestamp;

    protected long lastRender;

    private boolean quit;

    private String backgroundTexture;

    private AbstractLoop nextLoop;


    protected AbstractLoop(Environment environment, String[] textureList, String backgroundTexture)
    {
        this.environment = environment;
        this.backgroundTexture = backgroundTexture;
        loadTextures(textureList);
        initializeGraphics();

        quit = false;
    }


    protected final void initializeGraphics()
    {
        layerManager = new LayerManager();
        setBackground();
    }


    private void loadTextures(String[] textureList)
    {
        for (String texture : textureList)
        {
            environment.getEngine().createImage(texture);
        }
    }


    private void setBackground()
    {
        layerManager.addSimpleLayer(new Background(environment, backgroundTexture));
    }


    public final void loopStep()
    {
        loopTimestamp = environment.getTimer().getTime();

        updateState();
        render();
        processInput();
    }


    protected abstract void updateState();
    
    
    protected abstract void processInput();


    private void render()
    {
        long timeStamp = environment.getTimer().getTime();

        if (timeStamp - lastRender >= environment.getConfig().getInteger("FrameRate"))
        {
            lastRender = timeStamp;

            environment.getEngine().clearDisplay();
            layerManager.drawLayers(environment.getEngine());
            environment.getEngine().updateDisplay();
        }
    }


    public void exitLoop()
    {
        quit = true;
    }


    public void setNextLoop(AbstractLoop loop)
    {
        nextLoop = loop;
    }


    public AbstractLoop getNextLoop()
    {
        return nextLoop;
    }


    // for tests
    public final LayerManager getLayerManager()
    {
        return layerManager;
    }


    public final boolean isFinished()
    {
        return quit;
    }
}
