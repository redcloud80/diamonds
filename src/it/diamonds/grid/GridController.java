package it.diamonds.grid;


import it.diamonds.GameTurn;
import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.droppable.GemQueue;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.Config;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.RandomGenerator;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.grid.iteration.CanMoveDownQuery;
import it.diamonds.handlers.DropCommandHandler;
import it.diamonds.handlers.MirrorSlaveGemCommandHandler;
import it.diamonds.handlers.MoveLeftCommandHandler;
import it.diamonds.handlers.MoveRightCommandHandler;
import it.diamonds.handlers.RotateClockwiseCommandHandler;
import it.diamonds.handlers.RotateCounterclockwiseCommandHandler;
import it.diamonds.renderer.GridRenderer;


public class GridController
{
    private GridRenderer gridRenderer;

    private InputReactor inputReactor;

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

        this.gemsPair = new DroppablePair(getGrid(), environment.getConfig());

        this.inputReactor = inputReactor;
        addGridInputHandlers();

        this.gemGenerator = gemGenerator;

        insertNewGemsPair();

        gameTurn = new GameTurn(environment);
    }


    private void addGridInputHandlers()
    {
        inputReactor.addHandler(Code.BUTTON1, new RotateCounterclockwiseCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.BUTTON2, new RotateClockwiseCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.BUTTON3, new MirrorSlaveGemCommandHandler(gemsPair, inputReactor.getNormalRepeatDelay(), inputReactor.getFastRepeatDelay()));

        inputReactor.addHandler(Code.LEFT, new MoveLeftCommandHandler(gemsPair));
        inputReactor.addHandler(Code.RIGHT, new MoveRightCommandHandler(gemsPair));
        inputReactor.addHandler(Code.DOWN, new DropCommandHandler(getGrid(), gemsPair));
    }


    public void insertNewGemsPair()
    {
        Droppable gem;

        if (getGrid().isCellFree(Cell.create(config.getInteger("PivotRow"), config.getInteger("PivotColumn"))))
        {
            gem = gemGenerator.extract();
            Cell cell = Cell.create(config.getInteger("PivotRow"), config.getInteger("PivotColumn"));
            gem.getRegion().setRow(cell.getRow());
            gem.getRegion().setColumn(cell.getColumn());

            getGrid().insertDroppable(gem);
            gemsPair.setPivot(gem);

            gem = gemGenerator.extract();
            Cell cell1 = Cell.create(config.getInteger("SlaveRow"), config.getInteger("SlaveColumn"));
            gem.getRegion().setRow(cell1.getRow());
            gem.getRegion().setColumn(cell1.getColumn());

            getGrid().insertDroppable(gem);
            gemsPair.setSlave(gem);
        }
        else
        {
            gem = gemGenerator.extract();
            Cell cell = Cell.create(config.getInteger("SlaveRow"), config.getInteger("SlaveColumn"));
            gem.getRegion().setRow(cell.getRow());
            gem.getRegion().setColumn(cell.getColumn());

            getGrid().insertDroppable(gem);
            gem.drop();
            gemsPair.setPivot(gem);
            gemsPair.setNoSlave();
        }
    }


    public boolean isCenterColumnFull()
    {
        return getGrid().isColumnFull(4);
    }


    public Grid getGrid()
    {
        return gridRenderer.getGrid();
    }


    public GridRenderer getGridRenderer()
    {
        return gridRenderer;
    }


    public DroppableGenerator getGemGenerator()
    {
        return gemGenerator;
    }


    public void update(long timer, ScoreCalculator scoreCalculator)
    {
        gameTurn.update(timer, this, scoreCalculator);

        getGrid().updateDroppableAnimations(timer);
    }


    public void reactToInput(long timer)
    {
        gameTurn.reactToInput(inputReactor, timer);
    }


    public DroppablePair getGemsPair()
    {
        return this.gemsPair;
    }


    public static GridController create(Environment environment, InputReactor inputReactor, RandomGenerator generator, Point origin)
    {
        GridRenderer gridRenderer = new GridRenderer(environment, origin);

        GridController controller = new GridController(environment, gridRenderer, inputReactor, GemQueue.create(environment, generator));

        return controller;
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


    public boolean droppedGemWithoutGemsPairCanMoveDown()
    {
        CanMoveDownQuery action = new CanMoveDownQuery(getGrid());
        getGrid().runIteration(action);
        return action.getResult();
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
