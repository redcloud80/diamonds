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

    private final Point gridOrigin;

    private Sprite background;


    public GridRenderer(Environment environment, Point gridOrigin, Grid grid)
    {
        this.grid = grid;
        this.gridOrigin = gridOrigin;

        background = new Sprite(gridOrigin, environment.getEngine().createImage("grid-background"));
    }


    public Grid getGrid()
    {
        return grid;
    }


    public void draw(Engine engine)
    {
        background.draw(engine);
        DroppableList droppableList = grid.getDroppablesInArea(getGridRegion());

        for (Droppable droppable : droppableList)
        {
            final float screenPositionX = gridOrigin.getX()
                + droppable.getPositionInGridLocalSpace().getX();
            final float screenPositionY = gridOrigin.getY()
                + droppable.getPositionInGridLocalSpace().getY();

            droppable.getAnimatedSprite().getSprite().setPosition(new Point(screenPositionX, screenPositionY));
            droppable.getAnimatedSprite().getSprite().draw(engine);
        }
    }


    private Region getGridRegion()
    {
        return new Region(0, 0, grid.getNumberOfColumns(), grid.getNumberOfRows());
    }
}
