package it.diamonds.tests;


import it.diamonds.ScoreCalculator;
import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.engine.Engine;
import it.diamonds.grid.Region;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestScoreCalculator extends TestCase
{
    private ScoreCalculator scoreCalculator;

    private Droppable gem;


    public void setUp()
    {
        Engine engine = MockEngine.create(800, 600);

        gem = new Gem(engine, DroppableColor.DIAMOND, 0, 0);
        scoreCalculator = new ScoreCalculator(100);
    }


    private void doSuccessfulCrushRoundWithSingleGemAdded()
    {
        scoreCalculator.addDroppable(gem);
        scoreCalculator.closeCrush();
    }


    private void doSuccessfulCrushRoundWithTwoGemsAdded()
    {
        scoreCalculator.addDroppable(gem);
        scoreCalculator.addDroppable(gem);
        scoreCalculator.closeCrush();
    }


    public void testScoreIsZeroAfterCreation()
    {
        assertEquals(0, scoreCalculator.getScore());
    }


    public void testCrushScoreIsZeroAfterCreation()
    {
        assertEquals(0, scoreCalculator.getCrushScore());
    }


    public void testTempScoreIsZeroAfterCreation()
    {
        assertEquals(0, scoreCalculator.getTempScore());
    }


    public void testChainCounterIsZeroAfterCreation()
    {
        assertEquals(0, scoreCalculator.getChainCounter());
    }


    public void testBonusPercentageValueIsSetInConstructor()
    {
        assertEquals(100, scoreCalculator.getBonusPercentage());
    }


    public void testAddGemDoesntIncreaseChainCounter()
    {
        scoreCalculator.addDroppable(gem);
        assertEquals(0, scoreCalculator.getChainCounter());
    }
    
    
    public void testCloseCrushWithoutAddedGemsDoesntIncreaseChainCounter()
    {
        scoreCalculator.closeCrush();
        assertEquals(0, scoreCalculator.getChainCounter());
    }


    public void testAddGemIncreasesChainCounterWhenCrushRoundIsOver()
    {
        int expectedChainCounter = scoreCalculator.getChainCounter();

        doSuccessfulCrushRoundWithSingleGemAdded();
        assertEquals(expectedChainCounter + 1, scoreCalculator.getChainCounter());
    }


    public void testAddGemIncreasesChainCounterWhenTwoCrushRoundsAreOver()
    {
        int expectedChainCounter = scoreCalculator.getChainCounter();

        doSuccessfulCrushRoundWithSingleGemAdded();
        doSuccessfulCrushRoundWithSingleGemAdded();

        assertEquals(expectedChainCounter + 2, scoreCalculator.getChainCounter());
    }


    public void testResetChainCounter()
    {
        scoreCalculator.resetChainCounter();
        assertEquals(0, scoreCalculator.getChainCounter());
    }


    public void testAddGemDoesntIncrementScore()
    {
        int previousScore = scoreCalculator.getScore();

        scoreCalculator.addDroppable(gem);
        assertEquals(previousScore, scoreCalculator.getScore());
    }


    public void testAddGemDoesntIncrementTempScore()
    {
        int previousScore = scoreCalculator.getTempScore();

        scoreCalculator.addDroppable(gem);
        assertEquals(previousScore, scoreCalculator.getTempScore());
    }


    public void testAddGemIncrementsCrushScore()
    {
        int expectedScore = scoreCalculator.getCrushScore() + gem.getScore();

        scoreCalculator.addDroppable(gem);
        assertEquals(expectedScore, scoreCalculator.getCrushScore());
    }


    public void testAddTwoGemsIncrementsCrushScore()
    {
        int expectedScore = scoreCalculator.getTempScore();

        scoreCalculator.addDroppable(gem);
        scoreCalculator.addDroppable(gem);
        assertEquals(expectedScore + 2 * gem.getScore(), scoreCalculator.getCrushScore());
    }


    public void testAddTwoGemsInSameCrushRoundIncreasesChainCounterOnce()
    {
        int expectedChainCounter = scoreCalculator.getChainCounter();

        doSuccessfulCrushRoundWithTwoGemsAdded();

        assertEquals(expectedChainCounter + 1, scoreCalculator.getChainCounter());
    }
    
    
    public void testAddGemAfterCrushRoundDoesntIncreaseChainCounter()
    {
        int expectedChainCounter = scoreCalculator.getChainCounter();

        doSuccessfulCrushRoundWithTwoGemsAdded();
        scoreCalculator.addDroppable(gem);
        
        assertEquals(expectedChainCounter + 1, scoreCalculator.getChainCounter());
    } 


    public void testBigGemsGetBonus()
    {
        AbstractDroppable bigGem = new BigGem(DroppableColor.DIAMOND, MockEngine.create(800, 600));
        bigGem.getRegion().resizeToContain(new Region(0, 0, 3, 6));
        int expectedScore = (gem.getScore() * 18 * scoreCalculator.getBonusPercentage()) / 100;

        scoreCalculator.addDroppable(bigGem);
        assertEquals(expectedScore, scoreCalculator.getCrushScore());
    }


    public void testCrushScoreIsZeroAfterCrushRound()
    {
        doSuccessfulCrushRoundWithSingleGemAdded();

        assertEquals(0, scoreCalculator.getCrushScore());
    }


    public void testTempScoreIsIncrementedAfterCrushRound()
    {
        int expectedScore = scoreCalculator.getTempScore() + gem.getScore();

        doSuccessfulCrushRoundWithSingleGemAdded();

        assertEquals(expectedScore, scoreCalculator.getTempScore());
    }


    public void testScoreIsNotIncrementedAfterCrushRound()
    {
        int expectedScore = scoreCalculator.getScore();

        doSuccessfulCrushRoundWithSingleGemAdded();

        assertEquals(expectedScore, scoreCalculator.getScore());
    }


    public void testCrushScoreIsZeroAfterCloseChain()
    {
        doSuccessfulCrushRoundWithSingleGemAdded();

        scoreCalculator.closeChain();
        assertEquals(0, scoreCalculator.getCrushScore());
    }


    public void testTempScoreIsZeroAfterCloseChain()
    {
        doSuccessfulCrushRoundWithSingleGemAdded();

        scoreCalculator.closeChain();
        assertEquals(0, scoreCalculator.getTempScore());
    }


    public void testScoreIsIncrementedAfterCloseChain()
    {
        int expectedScore = scoreCalculator.getScore() + gem.getScore();

        doSuccessfulCrushRoundWithSingleGemAdded();

        scoreCalculator.closeChain();
        assertEquals(expectedScore, scoreCalculator.getScore());
    }


    public void testConsecutiveCrushesMultiplyTempScore()
    {
        int initialScore = scoreCalculator.getTempScore();

        doSuccessfulCrushRoundWithSingleGemAdded();
        doSuccessfulCrushRoundWithSingleGemAdded();

        assertEquals(initialScore + gem.getScore() * 2, scoreCalculator.getTempScore());
    }


    public void testConsecutiveDifferentCrushesMultiplyTempScore()
    {
        int initialScore = scoreCalculator.getTempScore();

        doSuccessfulCrushRoundWithSingleGemAdded();
        doSuccessfulCrushRoundWithTwoGemsAdded();

        assertEquals(initialScore + gem.getScore() * 3, scoreCalculator.getTempScore());
    }


    public void testChainCounterMultiplyScore()
    {
        doSuccessfulCrushRoundWithSingleGemAdded();
        doSuccessfulCrushRoundWithSingleGemAdded();
        doSuccessfulCrushRoundWithSingleGemAdded();

        int tempScore = scoreCalculator.getTempScore();

        scoreCalculator.closeChain();

        assertEquals(scoreCalculator.getChainCounter() * tempScore, scoreCalculator.getScore());
    }
}
