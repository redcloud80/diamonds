package it.diamonds.grid;


import it.diamonds.GameTurn;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.Config;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.handlers.DropCommandHandler;
import it.diamonds.handlers.MirrorSlaveGemCommandHandler;
import it.diamonds.handlers.MoveLeftCommandHandler;
import it.diamonds.handlers.MoveRightCommandHandler;
import it.diamonds.handlers.RotateClockwiseCommandHandler;
import it.diamonds.handlers.RotateCounterclockwiseCommandHandler;
import it.diamonds.renderer.GridRenderer;


public class GridController
{
    protected InputReactor inputReactor;
    
    private GridRenderer gridRenderer;

    private DroppableGenerator gemGenerator;

    private DroppablePair gemsPair;

    private int incomingStones = 0;

    private int stonesToSend = 0;

    private int morphingGems = 0;

    private boolean isCounterBoxToShow = false;

    private final Config config;

    private GameTurn gameTurn;

    public GridController(Environment environment, GridRenderer gridRenderer, InputReactor inputReactor, DroppableGenerator gemGenerator)
    {
        config = environment.getConfig();

        this.gridRenderer = gridRenderer;

        Grid grid = getGridRenderer().getGrid();
        this.gemsPair = new DroppablePair(grid, environment.getConfig());

        this.inputReactor = inputReactor;
        addGridInputHandlers(grid);

        this.gemGenerator = gemGenerator;

        insertNewGemsPair(grid);

        gameTurn = new GameTurn(environment);
    }


    protected void addGridInputHandlers(Grid grid)
    {
        inputReactor.addHandler(Code.BUTTON1, new RotateCounterclockwiseCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.BUTTON2, new RotateClockwiseCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.BUTTON3, new MirrorSlaveGemCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.LEFT, new MoveLeftCommandHandler(gemsPair));
        inputReactor.addHandler(Code.RIGHT, new MoveRightCommandHandler(gemsPair));
        inputReactor.addHandler(Code.DOWN, new DropCommandHandler(grid, gemsPair));
    }


    public void insertNewGemsPair(Grid grid)
    {
        Droppable gem;

        if (grid.isCellFree(Cell.create(config.getInteger("PivotRow"), config.getInteger("PivotColumn"))))
        {
            gem = gemGenerator.extract();
            gem.getRegion().setRow(config.getInteger("PivotRow"));
            gem.getRegion().setColumn(config.getInteger("PivotColumn"));

            grid.insertDroppable(gem);
            gemsPair.setPivot(gem);

            gem = gemGenerator.extract();
            gem.getRegion().setRow(config.getInteger("SlaveRow"));
            gem.getRegion().setColumn(config.getInteger("SlaveColumn"));

            grid.insertDroppable(gem);
            gemsPair.setSlave(gem);
        }
        else
        {
            gem = gemGenerator.extract();
            gem.getRegion().setRow(config.getInteger("SlaveRow"));
            gem.getRegion().setColumn(config.getInteger("SlaveColumn"));

            grid.insertDroppable(gem);
            gem.drop();
            
            gemsPair.setPivot(gem);
            gemsPair.setNoSlave();
        }
    }


    public GridRenderer getGridRenderer()
    {
        return gridRenderer;
    }


    public DroppableGenerator getGemGenerator()
    {
        return gemGenerator;
    }


    public void update(long timer, ScoreCalculator scoreCalculator, Grid grid)
    {
        gameTurn.update(timer, this, scoreCalculator);

        grid.updateDroppableAnimations(timer);
    }


    public void reactToInput(long timer)
    {
        gameTurn.reactToInput(inputReactor, timer);
    }


    public DroppablePair getGemsPair()
    {
        return this.gemsPair;
    }


    public void setIncomingStones(int value)
    {
        incomingStones = value;
    }


    public int getIncomingStones()
    {
        return incomingStones;
    }


    public void setStonesToSend(int value)
    {
        stonesToSend = value;
    }


    public int getStonesToSend()
    {
        return stonesToSend;
    }


    public void setCounterBoxVisibility(boolean visibility)
    {
        isCounterBoxToShow = visibility;
    }


    public boolean isCounterBoxToShow()
    {
        return isCounterBoxToShow;
    }


    public boolean isGameOver()
    {
        return gameTurn.isGameOver();
    }


    public void increaseMorphingGemCount()
    {
        morphingGems++;
    }


    public void decreaseMorphingGemCount()
    {
        morphingGems--;
    }


    public int getMorphingGemsCount()
    {
        return morphingGems;
    }
}
