package it.diamonds.tests.gui;


import it.diamonds.engine.Point;
import it.diamonds.engine.video.Number;
import it.diamonds.gui.WarningBox;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestWarningBox extends TestCase
{
    private MockEngine engine;

    private Point origin;

    private WarningBox warningBox;


    public void setUp()
    {
        engine = MockEngine.create(800, 600);

        origin = new Point(0, 0);
        warningBox = new WarningBox(engine, origin);
    }


    public void testHiddenAfterCreation()
    {
        assertTrue("new WarningBox must be hidden", warningBox.isHidden());
    }


    private void drawWarningBox(int i)
    {
        warningBox.setCounter(i);
        warningBox.show();

        warningBox.draw(engine);
    }


    public void testNumberDrawn()
    {
        drawWarningBox(2);

        assertNotNull(engine.getDrawInfoByImageName(Number.NUMBER_16X24_TEXTURE_NAME));
    }


    public void testHiddenNotDrawNumber()
    {
        warningBox.setCounter(2);

        warningBox.draw(engine);

        assertEquals("Number of crushes not correctly drawn", 0, engine.getNumberOfQuadsDrawn());
    }


    public void testNumberTwoIsDrawn()
    {
        drawWarningBox(2);

        MockEngine.DrawInfo drawInfo = engine.getDrawInfoByImageName(Number.NUMBER_16X24_TEXTURE_NAME);

        assertEquals(Number.getTextureAreaForDigit(2), drawInfo.getImageRect());
    }


    public void testNumberFourIsDrawn()
    {
        drawWarningBox(4);

        MockEngine.DrawInfo drawInfo = engine.getDrawInfoByImageName(Number.NUMBER_16X24_TEXTURE_NAME);

        assertEquals(Number.getTextureAreaForDigit(4), drawInfo.getImageRect());
    }


    public void testWarningBoxPosition()
    {
        drawWarningBox(2);
        assertEquals(origin, engine.getQuadPosition(1));
    }


    public void testWarningBoxNumberPosition()
    {
        drawWarningBox(2);

        Point numberPosition = Number.getPositionOfLastDigit(new Point(origin.getX() - 54, origin.getY() + 24));

        MockEngine.DrawInfo drawInfo = engine.getDrawInfoByImageName(Number.NUMBER_16X24_TEXTURE_NAME);
        assertEquals("WarningBox displays number of crushes at the wrong position", numberPosition, drawInfo.getQuadPosition());
    }


    public void testCounterIsZeroOnCreation()
    {
        assertEquals(0, warningBox.getCounter());
    }


    public void testSetCounterToOne()
    {
        warningBox.setCounter(1);
        assertEquals(1, warningBox.getCounter());
    }


    public void testSetCounterToTwo()
    {
        warningBox.setCounter(2);
        assertEquals(2, warningBox.getCounter());
    }

}
