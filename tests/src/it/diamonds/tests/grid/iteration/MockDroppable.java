package it.diamonds.tests.grid.iteration;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.CrushPriority;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.gems.MergeResult;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.Region;


public class MockDroppable implements Droppable
{
    private boolean canMoveDown = false;

    private Grid grid;


    public void setCanMoveDown(final boolean canMoveDown)
    {
        this.canMoveDown = canMoveDown;
    }


    public int crush(Grid grid)
    {
        return 0;
    }


    public void startCrush(Grid grid, CrushPriority priority, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
    }


    public AnimatedSprite getAnimatedSprite()
    {
        return null;
    }


    public int getArea()
    {
        return 0;
    }


    public Region getRegion()
    {
        return null;
    }


    public int getScore()
    {
        return 0;
    }


    public void moveToCell(Cell cell)
    {
    }


    public void update(long timer)
    {
    }


    public void drop()
    {
    }


    public boolean isFalling()
    {
        return false;
    }


    public boolean canMoveDown(Grid grid)
    {
        this.grid = grid;
        return canMoveDown;
    }


    public void moveDown(Grid grid)
    {
    }


    public Grid getGridPassedToCanMoveDownMethod()
    {
        return grid;
    }


    public void extend(Grid grid)
    {
    }


    public MergeResult merge(Grid grid)
    {
        return null;
    }


    public DroppableColor getColor()
    {
        return null;
    }


    public DroppableColor getHiddenColor()
    {
        return null;
    }


    public DroppableType getType()
    {
        return null;
    }


    public boolean canBeAddedToBigGem(DroppableColor color)
    {
        return false;
    }


    public void getAdjacentCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid)
    {
    }


    public void tryToAddToCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid)
    {
    }


    public void updateTransformation()
    {
    }


    public boolean isStoneTransformed()
    {
        return false;
    }


    public Droppable transform(GridController gridController)
    {
        return this;
    }
}
