package it.diamonds.droppable.gems;


import static it.diamonds.droppable.DroppableColor.NO_COLOR;
import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.CrushPriority;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.pair.Direction;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.iteration.RemoveByColor;


public final class FlashingGem extends AbstractSingleDroppable
{
    private static final Direction[] SEARCH_DIRECTIONS = { Direction.GO_DOWN,
        Direction.GO_LEFT, Direction.GO_RIGHT, Direction.GO_UP };


    public FlashingGem(Engine engine, int animationDelay, int animationUpdateRate)
    {
        super(engine, new DroppableDescription(DroppableType.FLASHING_GEM, NO_COLOR), new AnimationDescription(8, animationDelay, animationUpdateRate));
    }


    public void startCrush(Grid grid, CrushPriority priority, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        grid.removeDroppable(this);

        DroppableColor colorToDelete = searchColorToDelete(grid);

        if (colorToDelete == null)
        {
            return;
        }

        RemoveByColor iteration = new RemoveByColor(colorToDelete, grid);
        grid.runIteration(iteration);
    }


    private DroppableColor searchColorToDelete(Grid grid)
    {
        for (Direction direction : SEARCH_DIRECTIONS)
        {
            DroppableColor colorToDelete = getColorToDelete(grid, direction);

            if (colorToDelete != null && colorToDelete != NO_COLOR)
            {
                return colorToDelete;
            }
        }

        return null;
    }


    private DroppableColor getColorToDelete(Grid grid, Direction direction)
    {
        final int rowToSearch = getRegion().getTopRow()
            + direction.getRowDelta();
        final int columnToSearch = getRegion().getLeftColumn()
            + direction.getColumnDelta();

        Cell cell = Cell.create(rowToSearch, columnToSearch);

        if (!grid.isValidCell(cell))
        {
            return null;
        }

        Droppable droppable = grid.getDroppableAt(cell);

        if (droppable == null)
        {
            return null;
        }

        return droppable.getColor();
    }

}
