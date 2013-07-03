package it.diamonds.engine.video;


public final class AnimationDescription
{
    private final int frameCount;

    private final int delay;

    private final int updateRate;


    public AnimationDescription(int frameCount, int delay, int animationUpdateRate)
    {
        this.frameCount = frameCount;
        this.delay = delay;
        this.updateRate = animationUpdateRate;
    }


    public int getFrameCount()
    {
        return frameCount;
    }


    public int getDelay()
    {
        return delay;
    }


    public int getUpdateRate()
    {
        return updateRate;
    }


    public static AnimationDescription createNullAnimation()
    {
        return new AnimationDescription(6, 0, 0);
    }
}
