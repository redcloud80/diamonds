package it.diamonds.gui;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Image;
import it.diamonds.engine.video.Sprite;


public final class CrushBox extends Sprite
{
    private static String crushBoxPath = "gfx/common/crush/";

    private Image crushBoxTexture[];

    private Point origin;

    private int speed;

    private int screenWidth;

    private int crushCounter;

    private int pulsationLength;

    private float sizeMultiplier;


    public CrushBox(Environment environment, Point origin)
    {
        super(origin.getX(), origin.getY(), new Rectangle(0, 0, 256, 64), environment.getEngine().createImage(crushBoxPath
            + "02"));
        createTextures(environment.getEngine());

        this.origin = origin;
        this.screenWidth = environment.getConfig().getInteger("width");
        this.speed = (int)Math.signum(origin.getX() - 320)
            * environment.getConfig().getInteger("CrushBoxSpeed");
        sizeMultiplier = environment.getConfig().getInteger("CrushBoxSizeMultiplier");
        pulsationLength = environment.getConfig().getInteger("PulsationLength");

        resetCounter();
        hide();
    }


    private void createTextures(Engine engine)
    {
        crushBoxTexture = new Image[9];

        for (int crushCombo = 2; crushCombo < 10; crushCombo++)
        {
            String textureName = crushBoxPath + "0" + crushCombo;
            crushBoxTexture[crushCombo - 2] = engine.createImage(textureName);
        }

        crushBoxTexture[8] = engine.createImage(crushBoxPath + "over");
    }


    private boolean isOffScreen()
    {
        return (getPosition().getX() + getTextureArea().getWidth() < 0)
            || (getPosition().getX() >= screenWidth);
    }


    public void update(int newCrushCounter)
    {
        if (canUpdateCounter(newCrushCounter))
        {
            updateCounter(newCrushCounter);
        }

        if (isHidden())
        {
            return;
        }

        updatePosition();
        updatePulsation();
    }


    private void updatePosition()
    {
        if (isPulsing())
        {
            return;
        }

        if (isOffScreen())
        {
            hide();
            resetCounter();
        }
        else
        {
            moveRight(speed);
        }
    }


    private void resetCounter()
    {
        this.crushCounter = 1;
    }


    private void updateCounter(int newCrushCounter)
    {
        this.crushCounter = newCrushCounter;
        updateTexture(crushCounter);

        if (!isPulsing())
        {
            startSinglePulsation(pulsationLength, sizeMultiplier);
        }
    }


    private boolean canUpdateCounter(int newCrushCounter)
    {
        return newCrushCounter >= 2 && newCrushCounter >= this.crushCounter;
    }


    private void updateTexture(int crushCounter)
    {
        if (crushCounter > 9)
        {
            setTexture(crushBoxTexture[8]);
            return;
        }

        setTexture(crushBoxTexture[crushCounter - 2]);
    }


    public void show()
    {
        setPosition(origin.getX(), origin.getY());
        super.show();
    }
}
