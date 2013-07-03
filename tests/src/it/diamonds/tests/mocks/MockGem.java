package it.diamonds.tests.mocks;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public class MockGem extends AbstractSingleDroppable
{
    private boolean crushed = false;

    private int score = 0;


    public MockGem(Engine engine)
    {
        super(engine, new DroppableDescription(DroppableType.GEM, DroppableColor.DIAMOND), new AnimationDescription(6, 0, 0));
    }


    public MockGem(Engine engine, int score)
    {
        super(engine, new DroppableDescription(DroppableType.GEM, DroppableColor.DIAMOND), new AnimationDescription(6, 0, 0));
        this.score = score;
    }


    public int crush(Grid grid)
    {
        crushed = true;
        return 1;
    }


    public boolean isCrushed()
    {
        return crushed;
    }


    public int getScore()
    {
        return score;
    }


    public Droppable transform(GridController gridController)
    {
        return this;
    }
}
