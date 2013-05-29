package it.diamonds.gui;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Number;
import it.diamonds.engine.video.Sprite;


public final class WarningBox extends Sprite
{
    private static String texturePath = "gfx/layout/warning";

    private Number incomingStone;


    public WarningBox(Engine engine, Point origin)
    {
        super(new Point(origin.getX(), origin.getY()), new Rectangle(0, 0, 172, 59), engine.createImage(texturePath));
        incomingStone = Number.create16x24(engine, getCrushNumberPosition());

        hide();
    }


    public void draw(Engine engine)
    {
        drawWarningBoxBackground(engine);
        drawIcomingStone(engine);
    }


    private void drawWarningBoxBackground(Engine engine)
    {
        super.draw(engine);
    }


    private void drawIcomingStone(Engine engine)
    {
        if (!isHidden())
        {
            incomingStone.draw(engine);
        }
    }


    public int getCounter()
    {
        return incomingStone.getValue();
    }


    public void setCounter(int gemsCounter)
    {
        incomingStone.setValue(gemsCounter);
    }


    private Point getCrushNumberPosition()
    {
        return new Point(getPosition().getX() - 54, getPosition().getY() + 24);
    }

}
