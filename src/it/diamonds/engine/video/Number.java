package it.diamonds.engine.video;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;

import java.util.ArrayList;


public final class Number implements Drawable
{
    public static final int DIGIT_WIDTH = 16;

    public static final int DIGIT_HEIGHT = 24;

    public static final int DIGIT_OVERLAY = 5;

    public static final String NUMBER_16X24_TEXTURE_NAME = "gfx/common/score_16x24";

    private ArrayList<Sprite> digits;

    private int value;

    private Point origin;


    private Number(Engine engine, String textureName, Point origin)
    {
        createDigits(engine.createImage(textureName));
        this.origin = origin;
    }


    private void createDigits(Image texture)
    {
        digits = new ArrayList<Sprite>();

        for (int i = 0; i < 10; i++)
        {
            Rectangle textureArea = getTextureAreaForDigit(i);
            digits.add(new Sprite(new Point(0, 0), textureArea, texture));
        }
    }


    public static Number create16x24(Engine engine, Point origin)
    {
        return new Number(engine, NUMBER_16X24_TEXTURE_NAME, origin);
    }


    public int getValue()
    {
        return value;
    }


    public void setValue(int value)
    {
        this.value = value;
    }


    private void drawDigit(Engine engine, int digit, Point position)
    {
        Sprite digitSprite = digits.get(digit);

        digitSprite.setPosition(position);
        digitSprite.draw(engine);
    }


    private int extractDigit(int position)
    {
        int pow = (int)Math.pow(10, position);
        return (value % (pow * 10)) / pow;
    }


    public void draw(Engine engine)
    {
        boolean processingLeadingZeroes = true;

        for (int position = 0; position < 8; ++position)
        {
            int curDigit = extractDigit(7 - position);

            if (0 == curDigit && processingLeadingZeroes && position < 7)
            {
                continue;
            }

            processingLeadingZeroes = false;

            drawDigit(engine, curDigit, getPositionOfDigit(origin, position));
        }
    }


    public static Point getPositionOfDigit(Point origin, int position)
    {
        return new Point(origin.getX() + (DIGIT_WIDTH - DIGIT_OVERLAY)
            * position, origin.getY());
    }


    public static Point getPositionOfLastDigit(Point origin)
    {
        return new Point(origin.getX() + (DIGIT_WIDTH - DIGIT_OVERLAY) * 7, origin.getY());
    }


    public static Rectangle getTextureAreaForDigit(int i)
    {
        int x = (i % 5) * DIGIT_WIDTH;
        int y = (i / 5) * DIGIT_HEIGHT;

        Rectangle textureArea = new Rectangle(x, y, DIGIT_WIDTH, DIGIT_HEIGHT);
        return textureArea;
    }

}
