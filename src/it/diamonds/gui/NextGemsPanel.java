package it.diamonds.gui;


import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.video.Drawable;
import it.diamonds.engine.video.Sprite;


public class NextGemsPanel implements Drawable
{
    private DroppableGenerator gemQueue;

    private Point origin;


    public NextGemsPanel(DroppableGenerator gemQueue, Point origin)
    {
        this.gemQueue = gemQueue;
        this.origin = origin;
    }


    public void draw(Engine engine)
    {
        Sprite sprite = null;

        sprite = gemQueue.getGemAt(1).getAnimatedSprite().getSprite();
        sprite.setPosition(origin.getX(), origin.getY());
        sprite.draw(engine);

        sprite = gemQueue.getGemAt(0).getAnimatedSprite().getSprite();
        sprite.setPosition(origin.getX(), origin.getY() + 32);
        sprite.draw(engine);
    }
}
