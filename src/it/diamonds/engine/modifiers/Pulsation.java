package it.diamonds.engine.modifiers;


import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Sprite;


public class Pulsation implements DrawModifier
{
    private float currentAngle;

    private float angleStep;

    private float sizeMultiplier;


    public Pulsation(int pulsationLength, float sizeMultiplier)
    {
        this.sizeMultiplier = sizeMultiplier;

        angleStep = (float)Math.PI / pulsationLength;
    }


    public void updateModifierState()
    {
        currentAngle += angleStep;
    }


    public void draw(Sprite sprite, Engine engine)
    {
        float delta = calculateDelta();
        Point position = getDrawingPosition(sprite, delta);
        Rectangle textureArea = sprite.getTextureArea();

        engine.drawImage(position, textureArea.getWidth() + delta, textureArea.getHeight()
            + delta, sprite.getTexture(), textureArea);
    }


    public boolean isCompleted()
    {
        return false;
    }


    private float calculateDelta()
    {
        return (float)Math.abs(Math.sin(currentAngle) * sizeMultiplier);
    }


    private Point getDrawingPosition(Sprite sprite, float delta)
    {
        return new Point(sprite.getPosition().getX() - (delta / 2), sprite.getPosition().getY()
            - (delta / 2));
    }


    public float getCurrentAngle()
    {
        return currentAngle;
    }


    public float getAngleStep()
    {
        return angleStep;
    }


    public float getSizeMultiplier()
    {
        return sizeMultiplier;
    }
}
