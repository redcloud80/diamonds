package it.diamonds.grid.state;


import it.diamonds.engine.Environment;


public interface GemFallStrategy
{
    AbstractControllerState getState(Environment environment, long timer);
}
