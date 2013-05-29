package it.diamonds.tests.grid.iteration;

import it.diamonds.droppable.gems.MergeResult;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.iteration.MergeBigGemsIteration;

public class TestMergeBigGemsIteration extends DroppableIterationTestCase
{
    private class MockDroppableForBigGemsIterationTests extends MockDroppable
    {
        private Grid gridPassedToMerge = null;
        private MergeResult mergeResult = MergeResult.MERGE_DID_NOT_HAPPEN;
        
        
        public void setMergeResult(MergeResult mergeResult)
        {
            this.mergeResult = mergeResult;
        }
        
        public MergeResult merge(Grid grid)
        {
            gridPassedToMerge = grid;
            
            return mergeResult;
        }
        
        public Grid getGridPassedToMerge()
        {
            return gridPassedToMerge;
        }
    }
    
    private MergeBigGemsIteration iteration;
    private Grid grid;
    
    public void setUp()
    {
        super.setUp();
        
        grid = new Grid(new Environment(null, null, null, null));
        iteration = new MergeBigGemsIteration(grid);
    }
    
    public void testMergeInvokedCorrectly()
    {
        MockDroppableForBigGemsIterationTests droppable = createAndInsertMockDroppable();
        
        iterateFakeGrid(iteration);
        
        assertSame(grid, droppable.getGridPassedToMerge());
    }
    
    public void testHasFinishedMergingIfNoMergeHappened()
    {
        MockDroppableForBigGemsIterationTests droppable = createAndInsertMockDroppable();
        droppable.setMergeResult(MergeResult.MERGE_DID_NOT_HAPPEN);
        
        iterateFakeGrid(iteration);
        
        assertFalse(iteration.hasNotYetFinishedMerging());
    }
    
    public void testHasNotFinishedMergingIfMergeHappened()
    {
        MockDroppableForBigGemsIterationTests droppable = createAndInsertMockDroppable();
        droppable.setMergeResult(MergeResult.MERGE_HAPPENED);
        
        iterateFakeGrid(iteration);
        
        assertTrue(iteration.hasNotYetFinishedMerging());
    }
    
    public void testHasNotFinishedMergingIfMergeHappenedOnAtLeastOneDroppable()
    {
        MockDroppableForBigGemsIterationTests droppable1 = createAndInsertMockDroppable();
        droppable1.setMergeResult(MergeResult.MERGE_HAPPENED);
        
        MockDroppableForBigGemsIterationTests droppable2 = createAndInsertMockDroppable();
        droppable2.setMergeResult(MergeResult.MERGE_DID_NOT_HAPPEN);
        
        iterateFakeGrid(iteration);
        
        assertTrue(iteration.hasNotYetFinishedMerging());
    }
    
    public void testSkipSecondDroppableIfFirstOneMerged()
    {
        MockDroppableForBigGemsIterationTests droppable1 = createAndInsertMockDroppable();
        droppable1.setMergeResult(MergeResult.MERGE_HAPPENED);
        
        MockDroppableForBigGemsIterationTests droppable2 = createAndInsertMockDroppable();
        
        iterateFakeGrid(iteration);
        
        assertNull(droppable2.getGridPassedToMerge());
    }
    
    public void testMergingIsFinishedIfNoDroppablesWereIterated()
    {
        iterateFakeGrid(iteration);
        
        assertFalse(iteration.hasNotYetFinishedMerging());
    }

    private MockDroppableForBigGemsIterationTests createAndInsertMockDroppable()
    {
        MockDroppableForBigGemsIterationTests droppable = new MockDroppableForBigGemsIterationTests();
        insertIntoFakeGrid(droppable);
        
        return droppable;
    }
    
}
