package it.diamonds.engine;


public class Rectangle
{
    private int left;

    private int top;

    private int width;

    private int height;


    public Rectangle(int left, int top, int width, int height)
    {
        translateTo(left, top);
        
        resize(width, height);
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
        this.left = left;
        this.top = top;
    }


    public boolean equals(Rectangle otherRectangle)
    {
        return left == otherRectangle.left && top == otherRectangle.top
            && width == otherRectangle.width && height == otherRectangle.height;
    }


    public boolean equals(Object otherRectangle)
    {
        return equals((Rectangle)otherRectangle);
    }

}
