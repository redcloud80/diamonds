package it.diamonds.grid;


import it.diamonds.droppable.pair.Direction;
import it.diamonds.engine.Rectangle;


public class Region
{
    private Rectangle rectangle = null;


    public Region(int left, int top, int width, int height)
    {
        if (left < 0 || top < 0 || width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException();
        }

        rectangle = new Rectangle(left, top, width, height);
    }


    public void setRow(int row)
    {
        if (row < 0)
        {
            throw new IllegalArgumentException();
        }

        rectangle.translateTo(rectangle.getLeft(), row);
    }


    public void setColumn(int column)
    {
        if (column < 0)
        {
            throw new IllegalArgumentException();
        }

        rectangle.translateTo(column, rectangle.getTop());
    }


    public int getTopRow()
    {
        return rectangle.getTop();
    }


    public int getBottomRow()
    {
        return rectangle.getBottom();
    }


    public int getLeftColumn()
    {
        return rectangle.getLeft();
    }


    public int getRightColumn()
    {
        return rectangle.getRight();
    }


    public int getWidth()
    {
        return rectangle.getWidth();
    }


    public int getHeight()
    {
        return rectangle.getHeight();
    }


    public Region getAdjacentRegionByDirection(Direction direction)
    {
        int top = rectangle.getTop() + direction.getRowDelta();
        int left = rectangle.getLeft() + direction.getColumnDelta();
        int width = rectangle.getWidth();
        int height = rectangle.getHeight();

        switch (direction)
        {
            case GO_DOWN:
                top = rectangle.getBottom() + 1;
                height = 1;
            break;
            case GO_UP:
                height = 1;
            break;
            case GO_RIGHT:
                left = rectangle.getRight() + 1;
                width = 1;
            break;
            case GO_LEFT:
                width = 1;
            break;
        }

        if (top < 0 || left < 0)
        {
            return null;
        }

        return new Region(left, top, width, height);
    }


    public void resizeToContain(Region region)
    {
        Rectangle rectToAdd = region.rectangle;

        final int newLeft = Math.min(rectangle.getLeft(), rectToAdd.getLeft());
        final int newTop = Math.min(rectangle.getTop(), rectToAdd.getTop());
        final int newRight = Math.max(rectangle.getRight(), rectToAdd.getRight());
        final int newBottom = Math.max(rectangle.getBottom(), rectToAdd.getBottom());

        final int newWidth = newRight - newLeft + 1;
        final int newHeight = newBottom - newTop + 1;

        rectangle.translateTo(newLeft, newTop);
        rectangle.resize(newWidth, newHeight);
    }


    public boolean containsCell(Cell cell)
    {
        if (cell.getRow() < getTopRow())
        {
            return false;
        }

        if (cell.getRow() > getBottomRow())
        {
            return false;
        }

        if (cell.getColumn() < getLeftColumn())
        {
            return false;
        }

        if (cell.getColumn() > getRightColumn())
        {
            return false;
        }

        return true;
    }


    public boolean equals(Region other)
    {
        return this.rectangle.equals(other.rectangle);
    }


    @Override
    public boolean equals(Object other)
    {
        return equals((Region)other);
    }
}
