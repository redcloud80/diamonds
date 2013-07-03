package it.diamonds.tests.mocks;


import it.diamonds.engine.Timer;


public final class MockTimer implements Timer
{
    private volatile long time;

    private boolean sleepOnAdvance;


    private MockTimer()
    {
        this.time = 0;
        this.sleepOnAdvance = false;
    }


    public static MockTimer create()
    {
        return new MockTimer();
    }


    public long getTime()
    {
        return time;
    }


    public void setTime(long time)
    {
        this.time = time;
    }


    public void advance(long timeOffset)
    {
        this.time += timeOffset;
        if (sleepOnAdvance)
        {
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                ;
            }
        }
    }


    public void sleepOnAdvance(boolean sleepOnAdvance)
    {
        this.sleepOnAdvance = sleepOnAdvance;
    }

}
