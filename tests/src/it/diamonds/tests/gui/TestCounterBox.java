package it.diamonds.tests.gui;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.gui.CounterBox;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestCounterBox extends TestCase
{
    private Engine engine;

    private CounterBox counterBox;


    protected void setUp() throws Exception
    {
        engine = MockEngine.create(800, 600);
        counterBox = new CounterBox(engine, new Point(0, 0));
    }


    public void testHiddenAfterCreation()
    {
        assertTrue("new CounterBox must be hidden", counterBox.isHidden());
    }


    public void testCorrectTexture()
    {
        assertEquals("CounterBox must be created with correct texture", "gfx/layout/counter", counterBox.getTexture().getName());
    }


    public void testIsDrawnWithCorrectRectangle()
    {
        counterBox.show();
        counterBox.draw(engine);

        assertEquals("CounterBox drawn with wrong Rectangle", new Rectangle(0, 0, 172, 59), ((MockEngine)engine).getImageRect());
    }
}
