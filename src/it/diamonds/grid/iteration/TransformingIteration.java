package it.diamonds.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public class TransformingIteration implements DroppableIteration
{
    private Grid grid;

    private GridController controller;


    public TransformingIteration(GridController controller)
    {
        this.controller = controller;
        this.grid = controller.getGridRenderer().getGrid();
    }


    public void executeOn(Droppable droppable)
    {
        Droppable transformed = droppable.transform(controller);
        replaceDroppable(droppable, transformed);
    }


    private void replaceDroppable(Droppable droppable, Droppable transformed)
    {
        if (droppable != transformed)
        {
            int row = droppable.getRegion().getTopRow();
            int column = droppable.getRegion().getLeftColumn();
            grid.removeDroppable(droppable);
            Cell cell = Cell.create(row, column);
            transformed.getRegion().setRow(cell.getRow());
            transformed.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(transformed);
        }
    }


    public boolean areThereMorphingGems()
    {
        return controller.getMorphingGemsCount() > 0;
    }

}
