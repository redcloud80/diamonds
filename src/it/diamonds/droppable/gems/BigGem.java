package it.diamonds.droppable.gems;


import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.pair.Direction;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;
import it.diamonds.renderer.DroppableRenderer;


public class BigGem extends AbstractDroppable
{

    private static final Direction[] EXTEND_DIRECTIONS = { Direction.GO_UP,
        Direction.GO_RIGHT, Direction.GO_LEFT, Direction.GO_DOWN };

    private static final Direction[] MERGE_DIRECTIONS = { Direction.GO_UP,
        Direction.GO_RIGHT };


    public BigGem(DroppableColor color, Engine engine)
    {
        super(new DroppableDescription(DroppableType.BIG_GEM, color));

        getRegion().resizeToContain(new Region(0, 0, 2, 2));

        AnimatedSprite animatedSprite = new DroppableRenderer(engine, getRegion(), getDroppableDescription());

        setAnimatedSprite(animatedSprite);
    }


    public void update(long timer)
    {
    }


    public void extend(Grid grid)
    {
        for (Direction direction : EXTEND_DIRECTIONS)
        {
            extend(grid, direction);
        }
    }


    private void extend(Grid grid, Direction direction)
    {
        Region region = getRegion().getAdjacentRegionByDirection(direction);
        if (region == null)
        {
            return;
        }

        DroppableList droppables = grid.getDroppablesInArea(region);
        if (!canAdd(droppables, direction))
        {
            return;
        }

        for (Droppable droppable : droppables)
        {
            grid.removeDroppable(droppable);
        }

        getRegion().resizeToContain(region);

        updateSpritePosition(direction);
    }


    private void updateSpritePosition(Direction direction)
    {
        if (direction.equals(Direction.GO_UP))
        {
            getSprite().getPosition().setY(getSprite().getPosition().getY()
                - Cell.SIZE_IN_PIXELS);
        }

        if (direction.equals(Direction.GO_LEFT))
        {
            getSprite().getPosition().setX(getSprite().getPosition().getX()
                - Cell.SIZE_IN_PIXELS);
        }
    }


    private boolean canAdd(DroppableList list, Direction direction)
    {
        if (!isSizeCompatible(list, direction))
        {
            return false;
        }

        for (Droppable droppable : list)
        {
            if (!droppable.canBeAddedToBigGem(getColor()))
            {
                return false;
            }
        }

        return true;
    }


    private boolean isVertical(Direction direction)
    {
        return direction.equals(Direction.GO_UP)
            || direction.equals(Direction.GO_DOWN);
    }


    private boolean isSizeCompatible(DroppableList list, Direction direction)
    {
        return isVertical(direction) ? isWidthCompatible(list)
            : isHeightCompatible(list);
    }


    private boolean isWidthCompatible(DroppableList list)
    {
        int width = 0;

        for (Droppable droppable : list)
        {
            width += droppable.getRegion().getWidth();
            if (droppable.getRegion().getLeftColumn() < getRegion().getLeftColumn())
            {
                return false;
            }
        }

        return width == getRegion().getWidth();
    }


    private boolean isHeightCompatible(DroppableList list)
    {
        int height = 0;
        for (Droppable droppable : list)
        {
            height += droppable.getRegion().getHeight();

            if (droppable.getRegion().getTopRow() < getRegion().getTopRow())
            {
                return false;
            }
        }

        return height == getRegion().getHeight();
    }


    public MergeResult merge(Grid grid)
    {
        for (Direction direction : MERGE_DIRECTIONS)
        {
            if (merge(grid, direction) == MergeResult.MERGE_HAPPENED)
            {
                return MergeResult.MERGE_HAPPENED;
            }
        }

        return MergeResult.MERGE_DID_NOT_HAPPEN;
    }


    private MergeResult merge(Grid grid, Direction direction)
    {
        Region region = getRegion().getAdjacentRegionByDirection(direction);
        if (region == null)
        {
            return MergeResult.MERGE_DID_NOT_HAPPEN;
        }

        DroppableList list = grid.getDroppablesInArea(region);
        if (list.size() != 1 || !isSizeCompatible(list, direction))
        {
            return MergeResult.MERGE_DID_NOT_HAPPEN;
        }

        Droppable droppable = list.get(0);
        if (!droppable.getColor().equals(getColor()))
        {
            return MergeResult.MERGE_DID_NOT_HAPPEN;
        }

        if (direction == Direction.GO_UP)
        {
            getSprite().setPosition(droppable.getAnimatedSprite().getSprite().getPosition());
        }

        getRegion().resizeToContain(droppable.getRegion());
        grid.removeDroppable(droppable);
        return MergeResult.MERGE_HAPPENED;
    }


    public void moveDown(Grid grid)
    {
        if (!canMoveDown(grid))
        {
            return;
        }

        super.moveDown(grid);
    }


    public int getScore()
    {
        return super.getColor().getScore();
    }


    public boolean isFalling()
    {
        return false;
    }


    public void drop()
    {

    }
}
