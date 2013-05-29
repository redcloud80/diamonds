package it.diamonds.engine.modifiers;


import it.diamonds.engine.Engine;
import it.diamonds.engine.video.Sprite;


public interface DrawModifier
{
    void updateModifierState();


    void draw(Sprite sprite, Engine engine);


    boolean isCompleted();
}
