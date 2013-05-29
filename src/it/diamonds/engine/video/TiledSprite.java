package it.diamonds.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Region;


public class TiledSprite extends Sprite
{
    private static final Rectangle TEXTURE_AREA = new Rectangle(0, 0, Cell.SIZE_IN_PIXELS, Cell.SIZE_IN_PIXELS);

    private Region dimensions;


    public TiledSprite(float posX, float posY, Image texture, Region dimensions)
    {
        super(new Point(posX, posY), TEXTURE_AREA, texture);
        this.dimensions = dimensions;
    }


    public void draw(Engine engine)
    {
        float posX = getPosition().getX();
        float posY = getPosition().getY();

        for (int row = 0; row < dimensions.getHeight(); row++)
        {
            for (int column = 0; column < dimensions.getWidth(); column++)
            {
                setOrigin(getLeftOrigin(column), getTopOrigin(row));

                setPosition(posX + column * Cell.SIZE_IN_PIXELS, posY + row
                    * Cell.SIZE_IN_PIXELS);

                super.draw(engine);
            }
        }
        setPosition(posX, posY);
    }


    private int getTopOrigin(int row)
    {
        if (row == 0)
        {
            return 0;
        }

        if (row == dimensions.getHeight() - 1)
        {
            return Cell.SIZE_IN_PIXELS * 2;
        }

        return Cell.SIZE_IN_PIXELS;
    }


    private int getLeftOrigin(int column)
    {
        if (column == 0)
        {
            return 0;
        }

        if (column == dimensions.getWidth() - 1)
        {
            return Cell.SIZE_IN_PIXELS * 2;
        }

        return Cell.SIZE_IN_PIXELS;
    }
}
