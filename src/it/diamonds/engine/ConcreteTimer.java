package it.diamonds.engine;


public final class ConcreteTimer implements Timer
{
    private long timeStamp;


    private ConcreteTimer()
    {
        timeStamp = System.nanoTime();
    }


    public static ConcreteTimer create()
    {
        return new ConcreteTimer();
    }


    public long getTime()
    {
        return (System.nanoTime() - timeStamp) / 1000000;
    }


    public void advance(long timeOffset)
    {
        try
        {
            Thread.sleep(timeOffset);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

}
