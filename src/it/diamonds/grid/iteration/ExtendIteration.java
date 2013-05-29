package it.diamonds.grid.iteration;

import it.diamonds.droppable.Droppable;
import it.diamonds.grid.Grid;

public class ExtendIteration implements DroppableIteration
{
    private Grid grid;

    public ExtendIteration(Grid grid)
    {
        this.grid = grid;
    }

    public void executeOn(Droppable droppable)
    {
        droppable.extend(grid);
    }

}
