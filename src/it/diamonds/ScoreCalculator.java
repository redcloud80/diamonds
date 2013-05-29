package it.diamonds;


import it.diamonds.droppable.Droppable;


public class ScoreCalculator
{
    private int crushScore = 0;

    private int tempScore = 0;

    private int score = 0;

    private int bonusPercentage;

    private int chainCounter = 0;


    public ScoreCalculator(int bonusPercentage)
    {
        this.bonusPercentage = bonusPercentage;
    }


    public int getScore()
    {
        return score;
    }


    public int getBonusPercentage()
    {
        return bonusPercentage;
    }


    public int getCrushScore()
    {
        return crushScore;
    }


    public int getTempScore()
    {
        return tempScore;
    }


    public void addDroppable(Droppable droppable)
    {
        int area = droppable.getRegion().getHeight()
            * droppable.getRegion().getWidth();

        if (area == 1)
        {
            crushScore += droppable.getScore();
        }
        else
        {
            crushScore += (area * droppable.getScore() * bonusPercentage) / 100;
        }
    }


    public void closeCrush()
    {
        if (crushScore > 0)
        {
            chainCounter++;
            tempScore += crushScore;
            crushScore = 0;
        }
    }


    public void closeChain()
    {
        score += tempScore * chainCounter;
        tempScore = 0;
    }


    public int getChainCounter()
    {
        return chainCounter;
    }


    public void resetChainCounter()
    {
        chainCounter = 0;
    }
}
