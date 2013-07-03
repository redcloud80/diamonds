package it.diamonds.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Grid;


public class RemoveByColor implements DroppableIteration
{
    private DroppableColor colorToGarbageCollect;

    private Grid grid;


    public RemoveByColor(DroppableColor colorToGarbageCollect, Grid grid)
    {
        this.colorToGarbageCollect = colorToGarbageCollect;
        this.grid = grid;
    }


    public void executeOn(Droppable droppable)
    {
        if (droppable.getColor() == colorToGarbageCollect)
        {
            grid.removeDroppable(droppable);
        }
    }

}
