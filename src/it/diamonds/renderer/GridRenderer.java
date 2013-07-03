package it.diamonds.renderer;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableList;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.video.Drawable;
import it.diamonds.engine.video.Sprite;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;


public final class GridRenderer implements Drawable
{
    private final Grid grid;

    private Sprite background;


    public GridRenderer(Environment environment, Point origin)
    {
        grid = new Grid(environment, origin);

        initializeBackground(origin, environment.getEngine());
    }


    private void initializeBackground(Point origin, Engine engine)
    {
        background = new Sprite(origin.getX(), origin.getY(), engine.createImage("grid-background"));
    }


    public Grid getGrid()
    {
        return grid;
    }


    public void draw(Engine engine)
    {
        background.draw(engine);
        DroppableList droppableList = getGrid().getDroppablesInArea(getGridRegion());

        for (Droppable droppable : droppableList)
        {
            droppable.getAnimatedSprite().getSprite().draw(engine);
        }
    }


    private Region getGridRegion()
    {
        return new Region(0, 0, getGrid().getNumberOfColumns(), getGrid().getNumberOfRows());
    }
}
