package it.diamonds.engine.modifiers;


public class SinglePulsation extends Pulsation
{
    public SinglePulsation(int pulsationLength, float sizeMultiplier)
    {
        super(pulsationLength, sizeMultiplier);
    }


    public boolean isCompleted()
    {
        return (getCurrentAngle() / Math.PI) > 1;
    }
}
