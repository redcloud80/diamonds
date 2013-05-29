package it.diamonds.tests.mocks;


import it.diamonds.engine.RandomGenerator;


public class MockRandomGenerator implements RandomGenerator
{
    private int index;

    private int numbers[];


    public MockRandomGenerator(int numbers[])
    {
        setNumbers(numbers);
    }


    public MockRandomGenerator()
    {
        setNumbers(new int[] { 0, 1, 2, 3 });
    }


    public void setNumbers(int numbers[])
    {
        index = -1;
        this.numbers = numbers;
    }


    public int extract(int module)
    {
        index = (index + 1) % numbers.length;
        return numbers[index] % module;
    }


    public long getSeed()
    {
        return 0;
    }


    public MockRandomGenerator clone()
    {
        return new MockRandomGenerator(numbers);
    }
}
