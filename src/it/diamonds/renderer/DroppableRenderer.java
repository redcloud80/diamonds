package it.diamonds.renderer;


import it.diamonds.droppable.DroppableDescription;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.engine.video.Sprite;
import it.diamonds.engine.video.TiledSprite;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Region;


public class DroppableRenderer extends AnimatedSprite
{
    public DroppableRenderer(Engine engine, DroppableDescription droppableDescription, AnimationDescription animationDescription)
    {
        super(Sprite.createSquareSprite(Cell.SIZE_IN_PIXELS, droppableDescription.createImage(engine)), animationDescription);
    }


    public DroppableRenderer(Engine engine, Region region, DroppableDescription droppableDescription)
    {
        super(new TiledSprite(0, 0, droppableDescription.createImage(engine), region), AnimationDescription.createNullAnimation());
    }
}
