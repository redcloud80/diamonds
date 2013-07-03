package it.diamonds.engine;


public interface RandomGenerator
{
    int extract(int module);


    long getSeed();


    RandomGenerator clone();
}
