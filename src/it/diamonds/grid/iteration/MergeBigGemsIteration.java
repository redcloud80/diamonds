package it.diamonds.grid.iteration;

import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.gems.MergeResult;
import it.diamonds.grid.Grid;

public class MergeBigGemsIteration implements DroppableIteration
{
    Grid grid;
    MergeResult mergeResult = MergeResult.MERGE_DID_NOT_HAPPEN;

    public MergeBigGemsIteration(Grid grid)
    {
        this.grid = grid;
    }

    public void executeOn(Droppable droppable)
    {
        //TODO: REFACTOR THIS! This is definitely not nice, required not to change the original behaviour of merges.
        //we would in fact stop iterating after a merge and start a new iteration over.
        
        if (mergeResult == MergeResult.MERGE_HAPPENED)
        {
            return;
        }
        
        MergeResult currentResult = droppable.merge(grid); 
        if (currentResult == MergeResult.MERGE_HAPPENED)
        {
            mergeResult = MergeResult.MERGE_HAPPENED;
        }
    }

    public boolean hasNotYetFinishedMerging()
    {
        return mergeResult == MergeResult.MERGE_HAPPENED;
    }

}
