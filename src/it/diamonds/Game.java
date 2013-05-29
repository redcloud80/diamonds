package it.diamonds;


import it.diamonds.engine.Environment;


public final class Game
{
    private AbstractLoop currentLoop;

    private Environment environment;


    public Game(AbstractLoop absLoop, Environment environment)
    {
        this.currentLoop = absLoop;
        this.environment = environment;
    }


    public void loop()
    {
        while (currentLoop != null && !isWindowsClosed())
        {
            loopStep();
            sleepOneMillisecond();
        }
        environment.shutDown();
    }


    public void loopStep()
    {
        currentLoop.loopStep();
        if (currentLoop.isFinished())
        {
            currentLoop = currentLoop.getNextLoop();
        }
    }


    private boolean isWindowsClosed()
    {
        return environment.getEngine().isWindowClosed();
    }


    private void sleepOneMillisecond()
    {
        environment.getTimer().advance(1);
    }


    public static Game createGame(Environment environment)
    {
        if (environment == null)
        {
            throw new IllegalArgumentException("Cannot create Game with a null environment");
        }
        return new Game(new MenuLoop(environment), environment);
    }


    public static void main(String[] args)
    {
        Environment environment = null;
        try
        {
            environment = new Environment();
            Game game = createGame(environment);
            game.loop();
        }
        catch (Exception e)
        {
            BugReport.showDramaticMessageBox();
            BugReport.writeBugReport(e);
            if (environment != null)
            {
                environment.shutDown();
            }
        }
    }


    /** only for testing */
    public AbstractLoop getCurrentLoop()
    {
        return currentLoop;
    }
}
