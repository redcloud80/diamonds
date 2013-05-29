package it.diamonds.tests.engine.video;


import static it.diamonds.engine.video.Number.DIGIT_HEIGHT;
import static it.diamonds.engine.video.Number.DIGIT_OVERLAY;
import static it.diamonds.engine.video.Number.DIGIT_WIDTH;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Number;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestNumber extends EnvironmentTestCase
{
    private MockEngine engine;

    private Number number;

    private Point origin;


    public void setUp()
    {
        super.setUp();
        engine = (MockEngine)environment.getEngine();
        origin = new Point(0, 0);
        number = Number.create16x24(engine, origin);
    }


    public void testOneValue()
    {
        number.setValue(1234);
        assertEquals(1234, number.getValue());
    }


    public void testAnotherValue()
    {
        number.setValue(4321);
        assertEquals(4321, number.getValue());
    }


    public void testDrawOneDigit()
    {
        number.setValue(8);
        number.draw(engine);
        assertEquals(1, engine.getNumberOfQuadsDrawn());
    }


    public void testDrawTwoDigits()
    {
        number.setValue(56);
        number.draw(engine);
        assertEquals(2, engine.getNumberOfQuadsDrawn());
    }


    public void testTrailingZeroes()
    {
        number.setValue(12300);
        number.draw(engine);
        assertEquals(5, engine.getNumberOfQuadsDrawn());
    }


    public void testMiddleZeroes()
    {
        number.setValue(71104);
        number.draw(engine);
        assertEquals(5, engine.getNumberOfQuadsDrawn());
    }


    public void testZero()
    {
        number.setValue(0);
        number.draw(engine);
        assertEquals(1, engine.getNumberOfQuadsDrawn());
    }


    public void testSpriteSize()
    {
        number.setValue(3);
        number.draw(engine);
        assertEquals((float)DIGIT_WIDTH, engine.getQuadWidth());
        assertEquals((float)DIGIT_HEIGHT, engine.getQuadHeight());
    }


    public void testSpriteTopCoord()
    {
        number.setValue(2);
        number.draw(engine);
        assertEquals(0, (int)engine.getQuadPosition().getY());
    }


    public void testTextureAreaForDigit()
    {
        for (int i = 0; i < 10; i++)
        {
            Rectangle rec = Number.getTextureAreaForDigit(i);
            assertEquals(new Rectangle((i % 5) * DIGIT_WIDTH, (i / 5)
                * DIGIT_HEIGHT, DIGIT_WIDTH, DIGIT_HEIGHT), rec);
        }
    }


    public void testGetPositionOfDigitSeven()
    {
        assertEquals((DIGIT_WIDTH - DIGIT_OVERLAY) * 7, (int)Number.getPositionOfDigit(origin, 7).getX());
    }


    public void testGetPositionOfDigitSix()
    {
        assertEquals((DIGIT_WIDTH - DIGIT_OVERLAY) * 6, (int)Number.getPositionOfDigit(origin, 6).getX());
    }


    public void testGetPositionOfDigitOne()
    {
        assertEquals((DIGIT_WIDTH - DIGIT_OVERLAY) * 1, (int)Number.getPositionOfDigit(origin, 1).getX());
    }


    public void testFirstSpriteLeftCoord()
    {
        number.setValue(2);
        number.draw(engine);
        assertEquals(Number.getPositionOfDigit(origin, 7), engine.getQuadPosition());
    }


    public void testSecondSpriteLeftCoord()
    {
        number.setValue(20);
        number.draw(engine);
        assertEquals(Number.getPositionOfDigit(origin, 6), engine.getQuadPosition(1));
    }


    public void testSpriteTopCoordWithOrigin()
    {
        number = Number.create16x24(engine, new Point(64, 35));
        number.setValue(9);
        number.draw(engine);
        assertEquals(35, (int)engine.getQuadPosition().getY());
    }


    public void testFirstSpriteLeftCoordWithOrigin()
    {
        origin = new Point(64, 35);
        number = Number.create16x24(engine, origin);
        number.setValue(9);
        number.draw(engine);
        assertEquals(Number.getPositionOfDigit(origin, 7), engine.getQuadPosition());
    }


    public void testSecondSpriteLeftCoordWithOrigin()
    {
        origin = new Point(64, 35);
        number = Number.create16x24(engine, origin);
        number.setValue(90);
        number.draw(engine);
        assertEquals(Number.getPositionOfDigit(origin, 6), engine.getQuadPosition(1));
    }


    public void testGetPositionOfLastDigit()
    {
        assertEquals((DIGIT_WIDTH - DIGIT_OVERLAY) * 7, (int)Number.getPositionOfLastDigit(origin).getX());
    }
}
