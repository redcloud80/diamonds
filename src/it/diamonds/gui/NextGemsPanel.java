package it.diamonds.gui;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.video.Drawable;
import it.diamonds.engine.video.Sprite;
import it.diamonds.grid.Cell;


public class NextGemsPanel implements Drawable
{
    private DroppableGenerator gemQueue;
    
    private Point firstDroppablePosition;
    private Point secondDroppablePosition;


    public NextGemsPanel(DroppableGenerator gemQueue, Point origin)
    {
        this.gemQueue = gemQueue;
        this.firstDroppablePosition = origin;
        this.secondDroppablePosition = new Point(origin.getX(), origin.getY() + Cell.SIZE_IN_PIXELS);
    }


    public void draw(Engine engine)
    {
        Droppable firstDroppableInQueue = gemQueue.getGemAt(1);
        Droppable secondDroppableInQueue = gemQueue.getGemAt(0);
        
        drawDroppableInQueue(engine, firstDroppableInQueue, firstDroppablePosition);
        drawDroppableInQueue(engine, secondDroppableInQueue, secondDroppablePosition);
    }


    private void drawDroppableInQueue(Engine engine, Droppable droppable, Point location)
    {
        DroppableDescription droppableDescription = new DroppableDescription(droppable.getType(), droppable.getColor());
        Sprite droppableSprite = Sprite.createSquareSprite(Cell.SIZE_IN_PIXELS, droppableDescription.createImage(engine));        
        droppableSprite.setPosition(location.getX(), location.getY());
        droppableSprite.draw(engine);
    }
}
