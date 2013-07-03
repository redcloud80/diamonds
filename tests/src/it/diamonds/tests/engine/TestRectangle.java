package it.diamonds.tests.engine;


import it.diamonds.engine.Rectangle;
import junit.framework.TestCase;


public class TestRectangle extends TestCase
{

    private Rectangle rectangle;

    private Rectangle anotherRectangle;


    protected void setUp() throws Exception
    {
        rectangle = new Rectangle(10, 10, 10, 15);
        anotherRectangle = new Rectangle(4, 8, 15, 40);
    }


    public void testRectangleWidth()
    {
        assertEquals(10, rectangle.getWidth());
        assertEquals(15, anotherRectangle.getWidth());
    }


    public void testRectangleHeight()
    {
        assertEquals(15, rectangle.getHeight());
        assertEquals(40, anotherRectangle.getHeight());
    }


    public void testRectangleLeft()
    {
        assertEquals(10, rectangle.getLeft());
        assertEquals(4, anotherRectangle.getLeft());
    }


    public void testRectangleRight()
    {
        assertEquals(19, rectangle.getRight());
        assertEquals(18, anotherRectangle.getRight());
    }


    public void testRectangleBottom()
    {
        assertEquals(24, rectangle.getBottom());
        assertEquals(47, anotherRectangle.getBottom());
    }


    public void testRectangleTop()
    {
        assertEquals(10, rectangle.getTop());
        assertEquals(8, anotherRectangle.getTop());
    }


    public void testRectangleEquals()
    {
        int left = rectangle.getLeft();
        int top = rectangle.getTop();
        int width = rectangle.getWidth();
        int height = rectangle.getHeight();

        Rectangle copyRectangle = new Rectangle(left, top, width, height);
        assertTrue("copyRectangle must equals rectangle", rectangle.equals(copyRectangle));

        copyRectangle = new Rectangle(left + 1, top, width, height);
        assertFalse("copyRectangle must equals rectangle", rectangle.equals(copyRectangle));

        copyRectangle = new Rectangle(left, top + 1, width, height);
        assertFalse("copyRectangle must equals rectangle", rectangle.equals(copyRectangle));

        copyRectangle = new Rectangle(left, top, width + 1, height);
        assertFalse("copyRectangle must equals rectangle", rectangle.equals(copyRectangle));

        copyRectangle = new Rectangle(left, top, width, height + 1);
        assertFalse("copyRectangle must equals rectangle", rectangle.equals(copyRectangle));
    }


    public void testInvalidWidth()
    {
        try
        {
            new Rectangle(20, 10, -1, 25);
            fail("Invalid argument exception not thrown for width argument");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testInvalidHeight()
    {
        try
        {
            new Rectangle(10, 20, 20, -1);
            fail("Invalid argument exception not thrown for height argument");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testResize()
    {
        int newWidth = rectangle.getWidth() + 3;
        int newHeight = rectangle.getHeight() + 5;

        rectangle.resize(newWidth, newHeight);

        Rectangle resizedRectangle = new Rectangle(rectangle.getLeft(), rectangle.getTop(), newWidth, newHeight);
        assertTrue(rectangle.equals(resizedRectangle));
    }


    public void testInvalidResizeWidth()
    {
        try
        {
            rectangle.resize(-1, 1);
            fail("Invalid argument exception not thrown for width argument");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testInvalidResizeHeight()
    {
        try
        {
            rectangle.resize(1, -1);
            fail("Invalid argument exception not thrown for height argument");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testTranslate()
    {

        int newLeft = rectangle.getLeft() + 3;
        int newTop = rectangle.getTop() + 5;

        rectangle.translateTo(newLeft, newTop);

        Rectangle translatedRectangle = new Rectangle(newLeft, newTop, rectangle.getWidth(), rectangle.getHeight());
        assertTrue(rectangle.equals(translatedRectangle));
    }

}
