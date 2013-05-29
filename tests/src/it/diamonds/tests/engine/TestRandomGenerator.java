package it.diamonds.tests.engine;


import it.diamonds.engine.ConcreteRandomGenerator;
import it.diamonds.engine.RandomGenerator;
import junit.framework.TestCase;


public class TestRandomGenerator extends TestCase
{
    private ConcreteRandomGenerator generator1;

    private ConcreteRandomGenerator generator2;

    private long commonSeed;


    public void setUp()
    {
        commonSeed = System.nanoTime();

        generator1 = new ConcreteRandomGenerator(commonSeed);
        generator2 = new ConcreteRandomGenerator(commonSeed);
    }


    public void testFirstRandomNumberWhitConstructor()
    {
        assertTrue("First random number should be the same", generator1.extract(100000) == generator2.extract(100000));
    }


    private void extractTenHundredNumbers(RandomGenerator one, RandomGenerator two)
    {
        for (int i = 0; i < 1000; ++i)
        {
            one.extract(100000);
            two.extract(100000);
        }
    }


    public void testNextRandomAfterTenHundred()
    {
        extractTenHundredNumbers(generator1, generator2);

        for (int i = 0; i < 1000; ++i)
        {
            assertTrue("Next random number should be the same", generator1.extract(100000) == generator2.extract(100000));
        }
    }


    public void testGettingSeed()
    {
        assertEquals(commonSeed, generator1.getSeed());
    }


    public void testSettingSeedValue()
    {
        generator1.setSeed(commonSeed + 1000);

        assertEquals(commonSeed + 1000, generator1.getSeed());
    }


    public void testSequenceAfterSeedWasSet()
    {
        generator1.setSeed(commonSeed + 1000);
        generator2 = new ConcreteRandomGenerator(generator1.getSeed());

        assertTrue("First random number should be the same", generator1.extract(100000) == generator2.extract(100000));

        extractTenHundredNumbers(generator1, generator2);

        for (int i = 0; i < 1000; ++i)
        {
            assertTrue("Next random number should be the same", generator1.extract(100000) == generator2.extract(100000));
        }
    }


    public void testClone()
    {
        generator2 = (ConcreteRandomGenerator)generator1.clone();

        assertTrue("First random number should be the same", generator1.extract(100000) == generator2.extract(100000));

        extractTenHundredNumbers(generator1, generator2);

        for (int i = 0; i < 1000; ++i)
        {
            assertTrue("Next random number should be the same", generator1.extract(100000) == generator2.extract(100000));
        }

    }
}
