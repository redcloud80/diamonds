package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import it.diamonds.grid.Grid;
import it.diamonds.tests.GridTestCase;


public class TestGemCrushing extends GridTestCase
{

    public void setUp()
    {
        super.setUp();

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());
    }


    public void testGemsAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 2);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testMoreGemsAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 2);
        insertAndUpdate(createGem(EMERALD), 13, 5);
        insertAndUpdate(createChest(EMERALD), 13, 6);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testNotCrushingOnDifferentType()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(EMERALD), 13, 2);

        insertAndUpdate(createGem(DIAMOND), 13, 5);
        insertAndUpdate(createChest(DIAMOND), 13, 6);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must contain two gems", 2, grid.getNumberOfDroppables());
    }


    public void testVerticalGemsAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createChest(DIAMOND), 12, 2);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testTwoDiamondsNotCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);

        grid.updateCrushes(scoreCalculator, null);

        assertEquals("grid must contain two gems", 2, grid.getNumberOfDroppables());
    }


    public void testTwoChestsCrushing()
    {
        insertAndUpdate(createChest(DIAMOND), 13, 2);
        insertAndUpdate(createChest(DIAMOND), 13, 3);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testTwoDiamondsAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testThreeDiamondsAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testDiamondsChestAndFlashCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 1);
        insertAndUpdate(createFlashingGem(), 13, 5);

        grid.updateCrushes(scoreCalculator, null);
        scoreCalculator.closeChain();

        assertEquals("score must be empty", 0, scoreCalculator.getScore());
    }


    public void testDiamondsFlashCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createFlashingGem(), 13, 5);

        grid.updateCrushes(scoreCalculator, null);
        scoreCalculator.closeChain();

        assertEquals("score must be empty", 0, scoreCalculator.getScore());
    }


    public void testStoneNotCrushingWithFlashingGem()
    {
        insertAndUpdate(createStone(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createFlashingGem(), 13, 5);

        grid.updateCrushes(scoreCalculator, null);

        assertEquals("Stone must not crush", 1, grid.getNumberOfDroppables());
    }


    public void testCrushOnLeftBound()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 0);
        insertAndUpdate(createChest(DIAMOND), 13, 1);

        try
        {
            grid.updateCrushes(scoreCalculator, stoneCalculator);
        }
        catch (ArrayIndexOutOfBoundsException exc)
        {
            fail("ArrayIndexOutOfBoundsException thrown");
        }
    }


    public void testCrushOnRightBound()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 6);
        insertAndUpdate(createChest(DIAMOND), 13, 7);

        try
        {
            grid.updateCrushes(scoreCalculator, stoneCalculator);
        }
        catch (ArrayIndexOutOfBoundsException exc)
        {
            fail("ArrayIndexOutOfBoundsException thrown");
        }
    }


    public void testCrushOnTopBound()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createGem(DIAMOND), 12, 4);
        insertAndUpdate(createGem(DIAMOND), 11, 4);
        insertAndUpdate(createGem(DIAMOND), 10, 4);
        insertAndUpdate(createGem(DIAMOND), 9, 4);
        insertAndUpdate(createGem(DIAMOND), 8, 4);
        insertAndUpdate(createGem(DIAMOND), 7, 4);
        insertAndUpdate(createGem(DIAMOND), 6, 4);
        insertAndUpdate(createGem(DIAMOND), 5, 4);
        insertAndUpdate(createGem(DIAMOND), 4, 4);
        insertAndUpdate(createGem(DIAMOND), 3, 4);
        insertAndUpdate(createGem(DIAMOND), 2, 4);
        insertAndUpdate(createGem(DIAMOND), 1, 4);
        insertAndUpdate(createGem(DIAMOND), 0, 4);

        try
        {
            grid.updateCrushes(scoreCalculator, null);
        }
        catch (ArrayIndexOutOfBoundsException exc)
        {
            fail("ArrayIndexOutOfBoundsException thrown");
        }
    }


    public void testBigGemAndChestCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, getNumberOfExtensibleObject());
        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testBigGemOnLeftBorderCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 0);
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 0);
        insertAndUpdate(createGem(DIAMOND), 12, 1);
        insertAndUpdate(createChest(DIAMOND), 11, 1);

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must be empty", 0, getNumberOfExtensibleObject());
        assertEquals("grid must be empty", 0, grid.getNumberOfDroppables());
    }


    public void testSeparateGemsNotCrushing()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 0);
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 13, 5);
        insertAndUpdate(createGem(DIAMOND), 13, 6);
        insertAndUpdate(createChest(DIAMOND), 12, 0);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals("grid must contain 2 gems", 2, grid.getNumberOfDroppables());
    }


    public void testCrushSetTimer()
    {
        insertBigGem(DIAMOND, 12, 2, 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);
        assertTrue("Last crush environment.getTimer() not properly set", 1 > 0);
    }


    public void testCrushesCounters()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);
        insertAndUpdate(createChest(EMERALD), 10, 2);

        insertAndUpdate(createGem(RUBY), 9, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);

        checkCountersValues(grid, 5, 1, 1);

        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());

        controller.update(environment.getTimer().getTime(), scoreCalculator);

        checkCountersValues(grid, 3, 2, 2);

        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createChest(DIAMOND), 12, 1);

        insertAndUpdate(createGem(RUBY), 11, 1);

        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), null);

        makeAllGemsFall();
        controller.update(environment.getTimer().getTime(), null);

        checkCountersValues(grid, 6, 1, 1);
    }


    public void testCrushBeforeTimeIntervalElapsed()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);
        insertAndUpdate(createChest(EMERALD), 10, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);
        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes() - 1);
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals("Only first crash must be done", 4, grid.getNumberOfDroppables());
    }


    public void testFallingTimeNotCountedInDelayBetweenCrushes()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);
        insertAndUpdate(createChest(EMERALD), 10, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);
        environment.getTimer().advance(10);
        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes() - 10);
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals("Only first crash must be done", 4, grid.getNumberOfDroppables());
    }


    public void testCrushAfterTimeIntervalElapsed()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);
        insertAndUpdate(createChest(EMERALD), 10, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);
        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals("Two crash must be done", 2, grid.getNumberOfDroppables());
    }


    public void testCrushesDelay()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createGem(EMERALD), 11, 2);
        insertAndUpdate(createGem(DIAMOND), 10, 2);
        insertAndUpdate(createChest(DIAMOND), 9, 2);
        insertAndUpdate(createChest(EMERALD), 8, 2);
        insertAndUpdate(createChest(DIAMOND), 7, 2);
        insertAndUpdate(createChest(EMERALD), 6, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);

        makeAllGemsFall();
        oneStepForward();

        for (int i = 1; i < getDelayBetweenCrushes(); i++)
        {
            checkCrush("at iteration:" + i, 8, 1);
            oneStepForward();
        }

        checkCrush("second crush:", 6, 2);

        makeAllGemsFall();

        for (int i = 0; i < getDelayBetweenCrushes(); i++)
        {
            checkCrush("at iteration:" + i, 6, 2);
            oneStepForward();
        }

        checkCrush("third crush:", 4, 3);

        makeAllGemsFall();

        for (int i = 0; i < getDelayBetweenCrushes(); i++)
        {
            checkCrush("at iteration:" + i, 4, 3);
            oneStepForward();
        }

        checkCrush("final update:", 2, 4);
    }


    public void testDoubleCrushInOneTurn()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createChest(DIAMOND), 12, 1);
        insertAndUpdate(createChest(EMERALD), 12, 2);

        insertAndUpdate(createGem(RUBY), 11, 1);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator);

        checkCountersValues(grid, 3, 2, 1);

    }


    private void checkCrush(String string, int numberOfGems, int chainCounter)
    {
        assertEquals(string, numberOfGems, grid.getNumberOfDroppables());
        assertEquals(string, chainCounter, scoreCalculator.getChainCounter());
    }


    private void oneStepForward()
    {
        environment.getTimer().advance(1);
        controller.update(environment.getTimer().getTime(), scoreCalculator);
    }


    private void checkCountersValues(Grid grid, int numberOfGems, int crushedGemsCounter, int chainCounter)
    {
        assertEquals("The number of gems must be equal", numberOfGems, grid.getNumberOfDroppables());
        assertEquals("The number of crush chain must be equal", chainCounter, scoreCalculator.getChainCounter());
    }

}
