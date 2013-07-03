package it.diamonds.gui;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Sprite;


public final class CounterBox extends Sprite
{
    private static String texturePath = "gfx/layout/counter";


    public CounterBox(Engine engine, Point origin)
    {
        super(origin.getX(), origin.getY(), new Rectangle(0, 0, 172, 59), engine.createImage(texturePath));
        hide();
    }
}
