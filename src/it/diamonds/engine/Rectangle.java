package it.diamonds.engine;


public class Rectangle
{
    private int left;

    private int top;

    private int width;

    private int height;


    public Rectangle(int left, int top, int width, int height)
    {

        if (width < 0 || height < 0)
        {
            throw new IllegalArgumentException();
        }

        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }


    public int getHeight()
    {
        return height;
    }


    public int getWidth()
    {
        return width;
    }


    public int getTop()
    {
        return top;
    }


    public int getLeft()
    {
        return left;
    }


    public int getRight()
    {
        return width + left - 1;
    }


    public int getBottom()
    {
        return height + top - 1;
    }


    public void resize(int width, int height)
    {
        if (width < 0 || height < 0)
        {
            throw new IllegalArgumentException();
        }

        this.width = width;
        this.height = height;
    }


    public void translateTo(int left, int top)
    {
        this.top = top;
        this.left = left;
    }


    public boolean equals(Rectangle rectangle)
    {
        return left == rectangle.left && top == rectangle.top
            && width == rectangle.width && height == rectangle.height;
    }


    public boolean equals(Object arg0)
    {
        return equals((Rectangle)arg0);
    }

}
