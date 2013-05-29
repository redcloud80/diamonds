package it.diamonds.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.grid.Grid;


public class CanMoveDownQuery implements DroppableIteration
{
    private boolean result = false;

    private Grid grid;


    public CanMoveDownQuery(Grid grid)
    {
        this.grid = grid;
    }


    public void executeOn(Droppable droppable)
    {
        result |= droppable.canMoveDown(grid);
    }


    public boolean getResult()
    {
        return result;
    }

}
