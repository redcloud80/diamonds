package it.diamonds.droppable.gems;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.CrushPriority;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.Grid;


public final class Chest extends AbstractSingleDroppable
{
    public Chest(Engine engine, DroppableColor color, int animationDelay, int animationUpdateRate)
    {
        super(engine, new DroppableDescription(DroppableType.CHEST, color), new AnimationDescription(6, animationDelay, animationUpdateRate));
    }


    public void startCrush(Grid grid, CrushPriority priority, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        if (priority != CrushPriority.NORMAL_PRIORITY)
        {
            return;
        }

        DroppableList crushableGems = new DroppableList();
        DroppableList optionalCrushableGems = new DroppableList();
        getAdjacentCrushableGems(crushableGems, optionalCrushableGems, this, grid);

        if (crushableGems.size() > 0)
        {
            crushableGems.add(this);
            crushableGems.addAll(optionalCrushableGems);

            for (Droppable gemToCrush : crushableGems)
            {
                scoreCalculator.addDroppable(gemToCrush);
                stoneCalculator.addDroppable(gemToCrush);
                grid.removeDroppable(gemToCrush);
            }
        }
    }

}
