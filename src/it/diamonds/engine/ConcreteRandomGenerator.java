package it.diamonds.engine;


import java.util.Random;


public class ConcreteRandomGenerator implements RandomGenerator
{
    private Random rand;

    private long seed;


    public ConcreteRandomGenerator(long seed)
    {
        rand = new Random();
        setSeed(seed);
    }


    public ConcreteRandomGenerator()
    {
        this(System.nanoTime());
    }


    public void setSeed(long newSeed)
    {
        seed = newSeed;
        rand.setSeed(seed);
    }


    public long getSeed()
    {
        return seed;
    }


    public int extract(int module)
    {
        return rand.nextInt(module);
    }


    public RandomGenerator clone()
    {
        return new ConcreteRandomGenerator(seed);
    }

}
