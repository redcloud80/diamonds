package it.diamonds.tests.engine;


import it.diamonds.engine.ConcreteTimer;
import junit.framework.TestCase;


public class TestTimer extends TestCase
{

    public void testTimer() throws InterruptedException
    {
        ConcreteTimer timer = ConcreteTimer.create();

        long tickOne = timer.getTime();
        timer.advance(100);
        long tickTwo = timer.getTime();
        timer.advance(100);
        long tickThree = timer.getTime();

        assertTrue(tickOne < tickTwo);
        assertTrue(tickTwo < tickThree);
    }
}
