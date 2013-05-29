package it.diamonds.droppable;


import it.diamonds.engine.Environment;
import it.diamonds.engine.RandomGenerator;


public final class GemQueue implements DroppableGenerator
{
    public static final int MAX_QUEUE_SIZE = 2;

    private DroppableList queue;

    private RandomDroppableFactory randomDroppableFactory;


    private GemQueue()
    {
        queue = new DroppableList();
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
            queue.add(droppable);
        }
    }


    public Droppable extract()
    {
        Droppable droppable = queue.remove(0);
        fillQueueRandomly();
        return droppable;
    }


    public Droppable getGemAt(int index)
    {
        return queue.get(index);
    }
}
