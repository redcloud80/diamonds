package it.diamonds.engine;


public interface Timer
{

    long getTime();


    void advance(long timeOffset);

}
