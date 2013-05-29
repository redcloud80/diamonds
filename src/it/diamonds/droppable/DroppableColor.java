package it.diamonds.droppable;


public enum DroppableColor
{
    EMERALD(40), RUBY(50), SAPPHIRE(60), TOPAZ(80), DIAMOND(100), NO_COLOR(0);

    private int score;


    private DroppableColor(int score)
    {
        this.score = score;
    }


    public int getScore()
    {
        return score;
    }
}
