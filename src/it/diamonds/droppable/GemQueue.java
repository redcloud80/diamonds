package it.diamonds.droppable;


import it.diamonds.engine.Environment;
import it.diamonds.engine.RandomGenerator;

import java.util.LinkedList;


public final class GemQueue implements DroppableGenerator
{
    public static final int MAX_QUEUE_SIZE = 2;

    private LinkedList<Droppable> queue;

    private RandomDroppableFactory randomDroppableFactory;


    private GemQueue()
    {
        queue = new LinkedList<Droppable>();
    }


    public static GemQueue create(Environment environment, RandomGenerator randomGenerator)
    {
        GemQueue gemQueue = new GemQueue();

        gemQueue.randomDroppableFactory = new RandomDroppableFactory(environment, randomGenerator);

        gemQueue.fillQueueRandomly();

        return gemQueue;
    }


    private void fillQueueRandomly()
    {
        while (queue.size() < MAX_QUEUE_SIZE)
        {
            Droppable droppable = randomDroppableFactory.createRandomDroppable();
            queue.addLast(droppable);
        }
    }


    public Droppable extract()
    {
        Droppable droppable = queue.removeFirst();
        fillQueueRandomly();
        return droppable;
    }


    public Droppable getGemAt(int index)
    {
        return queue.get(index);
    }
}
