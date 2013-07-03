package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.GemQueue;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockRandomGenerator;

import java.util.NoSuchElementException;


public class TestGemQueue extends EnvironmentTestCase
{
    private GemQueue queue;

    private MockRandomGenerator mockGenerator;

    private int startGem;

    private int startChest;


    public void setUp()
    {
        super.setUp();

        mockGenerator = new MockRandomGenerator();
        queue = GemQueue.create(environment, mockGenerator);

        int chestProb = environment.getConfig().getInteger("ChestProbability");
        int flashProb = environment.getConfig().getInteger("FlashProbability");

        startGem = flashProb + chestProb;
        startChest = flashProb;
    }


    public void testNotEmptyQueue()
    {
        try
        {
            queue.extract();
        }
        catch (NoSuchElementException e)
        {
            fail("queue must be not empty");
        }
    }


    public void testFullQueue()
    {
        for (int i = 0; i < GemQueue.MAX_QUEUE_SIZE; i++)
        {
            try
            {
                queue.extract();
            }
            catch (NoSuchElementException e)
            {
                fail("queue not full after creation");
            }
        }
    }


    public void testQueueIsAlwaysFull()
    {
        for (int i = 0; i < GemQueue.MAX_QUEUE_SIZE; i++)
        {
            queue.extract();
        }
        try
        {
            queue.extract();
            queue.extract();
        }
        catch (NoSuchElementException e)
        {
            fail("queue isn't always full");
        }
    }


    public void testExtractedDroppablesAreCorrect()
    {
        int randomSequence[] = { startGem, 1, startChest, 1 };
        mockGenerator.setNumbers(randomSequence);
        queue = GemQueue.create(environment, mockGenerator);
        assertNotNull((Gem)queue.extract());
        assertNotNull((Chest)queue.extract());
    }


    public void testGetGemNotEmptyQueue()
    {
        try
        {
            queue.getGemAt(0);
        }
        catch (Exception e)
        {
            fail("La queue creata non è piena");
        }

    }


    public void testGetFirstGem()
    {
        Droppable gem = queue.getGemAt(0);
        assertSame(gem, queue.extract());
    }


    public void testGetSecondGem()
    {
        Droppable gem = queue.getGemAt(1);
        queue.extract();
        assertSame(gem, queue.extract());
    }


    public void testGetInvalidGem()
    {
        try
        {
            queue.getGemAt(2);
            fail("");
        }
        catch (IndexOutOfBoundsException e)
        {
            ;
        }
    }

}
