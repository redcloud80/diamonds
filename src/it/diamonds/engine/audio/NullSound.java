package it.diamonds.engine.audio;


public class NullSound implements Sound
{
    private boolean wasPlayed = false;


    public void freeMemory()
    {
        wasPlayed = false;
    }


    public Object getName()
    {
        return "null";
    }


    public void play()
    {
        wasPlayed = true;
    }


    public void reset()
    {
        wasPlayed = false;
    }


    public boolean wasPlayed()
    {
        return wasPlayed;
    }

}
