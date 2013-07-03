package it.diamonds;


import it.diamonds.droppable.Droppable;
import it.diamonds.engine.Environment;
import it.diamonds.engine.RandomGenerator;
import it.diamonds.engine.Timer;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.video.LayerManager;
import it.diamonds.engine.video.Number;
import it.diamonds.grid.Cell;
import it.diamonds.grid.GridController;
import it.diamonds.gui.CounterBox;
import it.diamonds.gui.CrushBox;
import it.diamonds.gui.GameOverBox;
import it.diamonds.gui.NextGemsPanel;
import it.diamonds.gui.WarningBox;


public final class PlayField
{
    private GridController gridController;

    private Timer timer;

    private long lastInputReactionTimeStamp = 0;

    private long lastUpdateTimeStamp = 0;

    private long lastCounterBoxUpdate;

    private NextGemsPanel nextGemsPanel;

    private Number score;

    private int inputServed;

    private int updateRate;

    private int inputRate;

    private int counterBoxUpdateRate;

    private CrushBox crushBox;

    private WarningBox warningBox;

    private CounterBox counterBox;

    private GameOverBox gameOverBox;

    private PlayField opponentPlayField;

    private boolean wasGameOverDrawn = false;

    private ScoreCalculator scoreCalculator;


    private PlayField(Environment environment, InputReactor inputReactor, RandomGenerator randomGenerator, PlayFieldDescriptor descriptor)
    {
        timer = environment.getTimer();

        gridController = GridController.create(environment, inputReactor, randomGenerator, descriptor.getGridOrigin());
        nextGemsPanel = new NextGemsPanel(gridController.getGemGenerator(), descriptor.getNextGemsPanelOrigin());
        score = Number.create16x24(environment.getEngine(), descriptor.getScoreOrigin());
        crushBox = new CrushBox(environment, descriptor.getCrushBoxOrigin());
        gameOverBox = new GameOverBox(environment.getEngine(), descriptor.getGameOverOrigin());
        warningBox = new WarningBox(environment.getEngine(), descriptor.getWarningBoxOrigin());
        counterBox = new CounterBox(environment.getEngine(), descriptor.getCounterBoxOrigin());

        scoreCalculator = new ScoreCalculator(environment.getConfig().getInteger("BonusPercentage"));

        updateRate = environment.getConfig().getInteger("UpdateRate");
        inputRate = environment.getConfig().getInteger("InputRate");
        counterBoxUpdateRate = environment.getConfig().getInteger("counterBoxUpdateRate");
    }


    public static PlayField createPlayField(Environment environment, InputReactor inputReactor, RandomGenerator randomGenerator, PlayFieldDescriptor descriptor)
    {
        return new PlayField(environment, inputReactor, randomGenerator, descriptor);
    }


    public GridController getGridController()
    {
        return gridController;
    }


    public void update(long timestamp)
    {
        if (lastUpdateTimeStamp + updateRate <= timestamp)
        {
            updatePlayField(timestamp);
            lastUpdateTimeStamp += updateRate;
            score.setValue(scoreCalculator.getScore());
            updateWarningBox();
            updateCrushBox();
            updateCounterBox();
        }
    }


    public void addIncomingStones(int stonesNumber)
    {
        int incomingStones = gridController.getIncomingStones() + stonesNumber;
        if (incomingStones > 99)
        {
            incomingStones = 99;
        }
        gridController.setIncomingStones(incomingStones);
        warningBox.setCounter(incomingStones);
        warningBox.show();
    }


    private void updateWarningBox()
    {
        if (!warningBox.isHidden() && gridController.getIncomingStones() == 0)
        {
            warningBox.hide();
        }

        if (opponentPlayField != null)
        {
            int stonesToSend = gridController.getStonesToSend();
            if (stonesToSend != 0)
            {
                opponentPlayField.addIncomingStones(stonesToSend);
                gridController.setStonesToSend(0);
            }
        }
    }


    private void updateCounterBox()
    {
        if (gridController.isCounterBoxToShow())
        {
            warningBox.hide();
            counterBox.show();
            lastCounterBoxUpdate = timer.getTime();
            gridController.setCounterBoxVisibility(false);
        }

        if (timer.getTime() >= lastCounterBoxUpdate + counterBoxUpdateRate)
        {
            counterBox.hide();
        }
    }


    private void updateCrushBox()
    {
        crushBox.update(scoreCalculator.getChainCounter());
    }


    private void updatePlayField(long timestamp)
    {
        gridController.update(timestamp, scoreCalculator);

    }


    public void reactToInput(long timestamp)
    {
        if (lastInputReactionTimeStamp + inputRate <= timestamp)
        {
            gridController.reactToInput(timestamp);
            lastInputReactionTimeStamp += inputRate;
            ++inputServed;
        }
    }


    public void fillLayerManager(LayerManager layerManager)
    {
        layerManager.addSimpleLayer(score);

        layerManager.addSimpleLayer(gridController.getGridRenderer());
        layerManager.addSimpleLayer(nextGemsPanel);
        layerManager.addSimpleLayer(warningBox);
        layerManager.addSimpleLayer(counterBox);
        layerManager.addSimpleLayer(crushBox);
        layerManager.addSimpleLayer(gameOverBox);
    }


    public void showGameOverMessage()
    {
        if (wasGameOverDrawn)
        {
            return;
        }

        gameOverBox.show();

        wasGameOverDrawn = true;
    }


    public void resetTimeStamps(long timeStamp)
    {
        lastUpdateTimeStamp = timeStamp;
        lastInputReactionTimeStamp = timeStamp;
    }


    public int wasInputServed()
    {
        return inputServed;
    }


    public CrushBox getCrushBox()
    {
        return crushBox;
    }


    public CounterBox getCounterBox()
    {
        return counterBox;
    }


    public WarningBox getWarningBox()
    {
        return warningBox;
    }


    public PlayField getOpponentPlayField()
    {
        return opponentPlayField;
    }


    public void setOpponentPlayField(PlayField opponentPlayField)
    {
        this.opponentPlayField = opponentPlayField;
    }


    public Droppable getDroppableAt(Cell cell)
    {
        return getGridController().getGrid().getDroppableAt(cell);
    }

}
