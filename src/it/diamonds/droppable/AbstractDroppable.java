package it.diamonds.droppable;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.gems.MergeResult;
import it.diamonds.droppable.pair.Direction;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.engine.video.Sprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.Region;


public abstract class AbstractDroppable implements Droppable
{
    private static final Direction[] DIRECTIONS = { Direction.GO_LEFT,
        Direction.GO_RIGHT, Direction.GO_UP, Direction.GO_DOWN };

    private Region region = new Region(0, 0, 1, 1);

    private AnimatedSprite animation;

    private DroppableDescription droppableDescription;


    protected AbstractDroppable(DroppableDescription droppableDescription)
    {
        this.droppableDescription = droppableDescription;
    }


    public abstract int getScore();


    public int getArea()
    {
        return getRegion().getHeight() * getRegion().getWidth();
    }


    public DroppableType getType()
    {
        return droppableDescription.getType();
    }


    public DroppableColor getColor()
    {
        return droppableDescription.getColor();
    }


    public DroppableColor getHiddenColor()
    {
        return getColor();
    }


    // REFACTOR THIS: missing test for BigGem
    public void moveToCell(Cell cell)
    {
        int deltaX = cell.getColumn() - getRegion().getLeftColumn();
        int deltaY = cell.getRow() - getRegion().getTopRow();

        getRegion().setColumn(cell.getColumn());
        getRegion().setRow(cell.getRow());

        getSprite().translate(Cell.SIZE_IN_PIXELS * deltaX, Cell.SIZE_IN_PIXELS
            * deltaY);
    }


    protected boolean canMoveButNotWithFullGravity(Grid grid)
    {
        float nextPositionY = getSprite().getPosition().getY()
            + grid.getActualGravity();
        float limit = grid.getRowUpperBound(grid.getNumberOfRows()
            - getRegion().getHeight());

        if (nextPositionY > limit)
        {
            return true;
        }

        float currentRowLimit = grid.getRowUpperBound(getRegion().getTopRow());

        if (nextPositionY <= currentRowLimit)
        {
            return false;
        }

        Region bottomRegion = getRegion().getAdjacentRegionByDirection(Direction.GO_DOWN);
        DroppableList collidingDroppables = grid.getDroppablesInArea(bottomRegion);
        return !collidingDroppables.isEmpty();
    }


    public void extend(Grid grid)
    {
    }


    public MergeResult merge(Grid grid)
    {
        return null;
    }


    public void moveDown(Grid grid)
    {
        if (canMoveButNotWithFullGravity(grid))
        {
            while (canMoveDown(grid))
            {
                if (getSprite().getPosition().getY() == grid.getRowUpperBound(getRegion().getTopRow()))
                {
                    getRegion().setRow(getRegion().getTopRow() + 1);
                }

                getSprite().getPosition().setY(grid.getRowUpperBound(getRegion().getTopRow()));
            }

            return;
        }

        getSprite().translate(0, grid.getActualGravity());

        if (getSprite().getPosition().getY() > grid.getRowUpperBound(getRegion().getTopRow()))
        {
            getRegion().setRow(getRegion().getTopRow() + 1);
        }
    }


    public boolean canMoveDown(Grid grid)
    {
        if (getSprite().getPosition().getY() != grid.getRowUpperBound(getRegion().getTopRow()))
        {
            return true;
        }

        if (getRegion().getBottomRow() == grid.getNumberOfRows() - 1)
        {
            return false;
        }

        Region bottomRegion = getRegion().getAdjacentRegionByDirection(Direction.GO_DOWN);
        DroppableList collidingDroppables = grid.getDroppablesInArea(bottomRegion);
        return collidingDroppables.isEmpty();
    }


    public Sprite getSprite()
    {
        return getAnimatedSprite().getSprite();
    }


    public Region getRegion()
    {
        return region;
    }


    public void update(long timer)
    {
        updateAnimation(timer);
    }


    protected void updateAnimation(long timer)
    {
        getAnimatedSprite().updateAnimation(timer);
    }


    public void setAnimatedSprite(AnimatedSprite animation)
    {
        this.animation = animation;
    }


    public AnimatedSprite getAnimatedSprite()
    {
        return animation;
    }


    public void startCrush(Grid grid, CrushPriority priority, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
    }


    public boolean canBeAddedToBigGem(DroppableColor color)
    {
        return false;
    }


    protected DroppableDescription getDroppableDescription()
    {
        return droppableDescription;
    }


    public void getAdjacentCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid)
    {
        for (Direction direction : DIRECTIONS)
        {
            Region adjacentRegion = getRegion().getAdjacentRegionByDirection(direction);

            // TODO: wouldn't it be nice to allow 0x0 regions and remove this if?
            if (adjacentRegion == null)
            {
                continue;
            }

            DroppableList list = grid.getDroppablesInArea(adjacentRegion);
            for (Droppable droppable : list)
            {
                droppable.tryToAddToCrushableGems(crushableGems, optionalCrushableGems, crushStarter, grid);
            }
        }
    }


    public void tryToAddToCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid)
    {
        if (getColor() == crushStarter.getColor()
            && !crushableGems.contains(this))
        {
            crushableGems.add(this);
            getAdjacentCrushableGems(crushableGems, optionalCrushableGems, crushStarter, grid);
        }
    }


    public void updateTransformation()
    {
    }


    public Droppable transform(GridController gridController)
    {
        return this;
    }

}
