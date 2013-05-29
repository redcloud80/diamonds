package it.diamonds.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.engine.Engine;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;


public class CreateNewBigGems implements DroppableIteration
{
    private transient Engine engine;

    private Grid grid;


    public CreateNewBigGems(Grid grid, Engine engine)
    {
        this.grid = grid;
        this.engine = engine;
    }


    public void executeOn(Droppable gem)
    {
        DroppableColor color = gem.getColor();
        int row = gem.getRegion().getTopRow();
        int column = gem.getRegion().getLeftColumn();

        DroppableList candidateGems = grid.getDroppablesInArea(new Region(column, row, 2, 2));

        if (candidateGems.size() < 4)
        {
            return;
        }

        for (Droppable droppable : candidateGems)
        {
            if (!droppable.canBeAddedToBigGem(color))
            {
                return;
            }
        }

        for (Droppable droppable : candidateGems)
        {
            grid.removeDroppable(droppable);
        }

        BigGem bigGem = new BigGem(color, engine);
        bigGem.getRegion().setRow(row);
        bigGem.getRegion().setColumn(column);

        grid.insertDroppable(bigGem);
    }

}
