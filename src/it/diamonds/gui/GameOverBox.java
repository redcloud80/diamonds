package it.diamonds.gui;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.video.Sprite;


public class GameOverBox extends Sprite
{
    public static final String TEXTURE_PATH = "gfx/common/gameover";


    public GameOverBox(Engine engine, Point origin)
    {
        super(new Point(origin.getX(), origin.getY()), engine.createImage(TEXTURE_PATH));
        hide();
    }
}
