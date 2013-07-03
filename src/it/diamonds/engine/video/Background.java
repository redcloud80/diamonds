package it.diamonds.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Rectangle;


public class Background implements Drawable
{
    private Sprite background;


    public Background(Environment environment, String textureName)
    {
        Rectangle area = new Rectangle(0, 0, environment.getConfig().getInteger("width"), environment.getConfig().getInteger("height"));

        background = new Sprite(0, 0, area, environment.getEngine().createImage(textureName));
    }


    public float getX()
    {
        return background.getPosition().getX();
    }


    public float getY()
    {
        return background.getPosition().getY();
    }


    public int getWidth()
    {
        return background.getTexture().getWidth();
    }


    public int getHeight()
    {
        return background.getTexture().getHeight();
    }


    public Sprite getSprite()
    {
        return background;
    }


    public void draw(Engine engine)
    {
        background.draw(engine);
    }
}
