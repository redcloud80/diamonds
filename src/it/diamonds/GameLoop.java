package it.diamonds;


import it.diamonds.engine.ConcreteRandomGenerator;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Timer;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.handlers.EscapeCommandHandler;


public final class GameLoop extends AbstractLoop
{
    public static final String BACKGROUND = "back000.jpg";

    private static final String COMMON_CRUSH = COMMON + "crush/";

    private static final String DROPPABLES = GFX + "droppables/";

    private static final String BOXES = DROPPABLES + "boxes/";

    private static final String FLASHING = DROPPABLES + "flashing/";

    private static final String GEMS = DROPPABLES + "gems/";

    private static final String STONES = DROPPABLES + "stones/";

    private static final String TILES = DROPPABLES + "tiles/";

    private static final String LAYOUT = GFX + "layout/";

    private static final String[] TEXTURE_LIST = { BACKGROUND,
        "grid-background",

        COMMON + "font_14x29", COMMON + "font_8x8", COMMON + "gameover",
        COMMON + "score_16x24",

        COMMON_CRUSH + "02", COMMON_CRUSH + "03", COMMON_CRUSH + "04",
        COMMON_CRUSH + "05", COMMON_CRUSH + "06", COMMON_CRUSH + "07",
        COMMON_CRUSH + "08", COMMON_CRUSH + "09", COMMON_CRUSH + "over",

        BOXES + "diamond", BOXES + "emerald", BOXES + "ruby",
        BOXES + "sapphire", BOXES + "topaz",

        FLASHING + "nocolor",

        GEMS + "diamond", GEMS + "emerald", GEMS + "ruby", GEMS + "sapphire",
        GEMS + "topaz",

        STONES + "diamond", STONES + "emerald", STONES + "ruby",
        STONES + "sapphire", STONES + "topaz",

        TILES + "diamond", TILES + "emerald", TILES + "ruby",
        TILES + "sapphire", TILES + "topaz",

        LAYOUT + "counter", LAYOUT + "warning" };
    
    private InputFactory inputFactory;

    private Input playerOneInput;

    private Input playerTwoInput;

    private PlayField playFieldOne;

    private PlayField playFieldTwo;

    private long gameOverTimestamp;

    private long restartGameDelay;

    private boolean gameOver;


    public GameLoop(Environment environment, InputFactory inputFactory)
    {        
        super(environment, TEXTURE_LIST, BACKGROUND);

        this.inputFactory = inputFactory;
        createInput();
        
        restartGameDelay = environment.getConfig().getInteger("RestartGameDelay");
        
        startNewGame();
        startMusic(environment);
    }


    private void startNewGame()
    {
        gameOver = false;
        initPlayField(environment.getTimer());
        playerOneInput.flush();
        playerTwoInput.flush();
    }


    private void initPlayField(Timer timer)
    {
        long timeStamp = timer.getTime();
        long randomSeed = System.nanoTime();

        createPlayFieldOne(timeStamp, randomSeed);
        createPlayFieldTwo(timeStamp, randomSeed);

        attachFields();
    }


    private void createPlayFieldOne(long timeStamp, long randomSeed)
    {
        playFieldOne = createPlayField(PlayFieldDescriptor.createForPlayerOne(), playerOneInput, timeStamp, randomSeed);
    }


    private void createPlayFieldTwo(long timeStamp, long randomSeed)
    {
        playFieldTwo = createPlayField(PlayFieldDescriptor.createForPlayerTwo(), playerTwoInput, timeStamp, randomSeed);
    }


    private PlayField createPlayField(PlayFieldDescriptor descriptor, Input input, long timeStamp, long randomSeed)
    {
        InputReactor inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        inputReactor.addHandler(Code.ESCAPE, new EscapeCommandHandler(this));

        PlayField field = PlayField.createPlayField(environment, inputReactor, new ConcreteRandomGenerator(randomSeed), descriptor);

        field.resetTimeStamps(timeStamp);
        field.fillLayerManager(layerManager);

        return field;
    }


    private void attachFields()
    {
        playFieldOne.setOpponentPlayField(playFieldTwo);
        playFieldTwo.setOpponentPlayField(playFieldOne);
    }


    private void startMusic(Environment environment)
    {
        environment.getAudio().playMusic();
    }


    private void createInput()
    {
        playerOneInput = inputFactory.createInputForPlayerOne();
        playerTwoInput = inputFactory.createInputForPlayerTwo();
    }


    @Override
    protected void updateState()
    {
        if (!gameOver)
        {
            playFieldOne.update(loopTimestamp);
            playFieldTwo.update(loopTimestamp);

            checkAndShowGameOverMessage(playFieldOne);
            checkAndShowGameOverMessage(playFieldTwo);
        }
        else
        {
            if (gameOverTimestamp + restartGameDelay <= environment.getTimer().getTime())
            {
                initializeGraphics();
                startNewGame();
            }
        }
    }


    private void checkAndShowGameOverMessage(PlayField playField)
    {
        if (playField.getGridController().isGameOver())
        {
            playField.showGameOverMessage();
            initRestartGame();
        }
    }


    private void initRestartGame()
    {
        gameOverTimestamp = environment.getTimer().getTime();
        gameOver = true;
    }


    @Override
    protected void processInput()
    {
       updateInputDevices(); 
        
        if (!gameOver)
        {
            playFieldOne.reactToInput(loopTimestamp);
            playFieldTwo.reactToInput(loopTimestamp);
        }
    }
    
    
    public void updateInputDevices()
    {
        playerOneInput.update();
        
        if (playerOneInput.getInputDevice() != playerTwoInput.getInputDevice())
        {
            playerTwoInput.update();
        }
    }


    public PlayField getPlayFieldOne()
    {
        return this.playFieldOne;
    }


    public PlayField getPlayFieldTwo()
    {
        return this.playFieldTwo;
    }


    public Input getPlayerOneInput()
    {
        return this.playerOneInput;
    }


    public Input getPlayerTwoInput()
    {
        return this.playerTwoInput;
    }
}
