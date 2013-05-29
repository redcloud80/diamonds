package it.diamonds.tests.grid;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import it.diamonds.StoneCalculator;
import it.diamonds.tests.GridTestCase;


public class TestGridScore extends GridTestCase
{

    public void setUp()
    {
        super.setUp();

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());
    }


    public void testNoScoreOnInsertion()
    {
        insertAndUpdate(createGem(DIAMOND), 0, 0);

        assertEquals("Total Score must be 0", 0, scoreCalculator.getScore());
    }


    public void testScoreOnDeletion()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 2);

        grid.updateCrushes(scoreCalculator, new StoneCalculator());
        scoreCalculator.closeChain();

        assertEquals("Total score was not correct", createGem(DIAMOND).getScore(), scoreCalculator.getScore());
    }


    public void testBigGemsAddedWithBonus()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, new StoneCalculator());
        scoreCalculator.closeChain();

        int realScore = createGem(DIAMOND).getScore() * 4
            * environment.getConfig().getInteger("BonusPercentage") / 100;

        assertEquals("Total score was not correct", realScore, scoreCalculator.getScore());
    }


    public void testBigGemsAddedWithBonus2()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 1);
        insertAndUpdate(createGem(DIAMOND), 12, 2);

        insertAndUpdate(createChest(DIAMOND), 9, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        int realScore = createGem(DIAMOND).getScore() * 4
            * environment.getConfig().getInteger("BonusPercentage") / 100;
        int gridScore = scoreCalculator.getScore();

        assertEquals("Total score was not correct: is " + gridScore
            + " instead of " + realScore, realScore, gridScore);
    }


    public void testScoreOnMultipleCrushes()
    {
        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createChest(DIAMOND), 11, 2);
        insertAndUpdate(createChest(EMERALD), 10, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        int realScore = (createGem(DIAMOND).getScore() + createGem(EMERALD).getScore()) * 2;
        assertEquals(realScore, scoreCalculator.getScore());
    }


    public void testScoreOnMultipleCrushesOfBigGem()
    {
        insert2x2BigGem(DIAMOND, 12, 1);
        insert2x2BigGem(EMERALD, 10, 1);

        insertAndUpdate(createChest(EMERALD), 9, 1);
        insertAndUpdate(createChest(DIAMOND), 9, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        int realScore = (createGem(DIAMOND).getScore() + createGem(EMERALD).getScore()) * 4 * 4;
        assertEquals(realScore, scoreCalculator.getScore());
    }


    public void testScoreOnFlashAndChestCrush()
    {
        insertAndUpdate(createGem(RUBY), 13, 1);
        insertAndUpdate(createGem(EMERALD), 12, 1);
        insertAndUpdate(createFlashingGem(), 13, 0);
        insertAndUpdate(createChest(EMERALD), 13, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        makeAllGemsFall();

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        int realScore = createGem(EMERALD).getScore();
        assertEquals(realScore, scoreCalculator.getScore());
    }
}
