package it.diamonds.engine.video;


import it.diamonds.engine.Point;


public class AnimatedSprite
{
    private int currentFrame;

    private int frameCount;

    private FrameList animationFrames = new FrameList();

    private long animationCycleStart;

    private long animationCycleLength;

    private Sprite sprite;


    public AnimatedSprite(Sprite sprite, AnimationDescription animationDescription)
    {
        if (animationDescription.getFrameCount() < 2)
        {
            throw new IllegalArgumentException();
        }

        this.sprite = sprite;
        this.frameCount = animationDescription.getFrameCount();

        addFrame(0, 0, animationDescription.getDelay());

        createAnimationSequence(animationDescription.getUpdateRate());
    }


    private void createAnimationSequence(int animationUpdateRate)
    {
        for (int frameIndex = 1; frameIndex < frameCount; ++frameIndex)
        {
            addFrame(0, sprite.getTextureArea().getWidth() * frameIndex, animationUpdateRate);
        }
    }


    public int getNumberOfFrames()
    {
        return frameCount;
    }


    private void addFrame(int x, int y, int delay)
    {
        animationFrames.add(new Frame(x, y, delay));
        animationCycleLength += delay;
    }


    public int getCurrentFrame()
    {
        return currentFrame;
    }


    public void updateAnimation(long timer)
    {
        long animationTime = computeAnimationTime(timer);
        setCurrentFrame(findAnimationFrame(animationTime));
    }


    private long computeAnimationTime(long timer)
    {
        long timeElapsedSinceCycleStart = timer - animationCycleStart;

        animationCycleStart += animationCycleLength
            * (timeElapsedSinceCycleStart / animationCycleLength);

        timeElapsedSinceCycleStart %= animationCycleLength;

        return timeElapsedSinceCycleStart;
    }


    private int findAnimationFrame(long animationTime)
    {
        int frameIndex = 0;

        int frameLength = animationFrames.get(frameIndex).getLength();

        while (animationTime >= frameLength)
        {
            ++frameIndex;

            frameLength += animationFrames.get(frameIndex).getLength();
        }

        return frameIndex;
    }


    public void setCurrentFrame(int frameIndex)
    {
        Frame frame = animationFrames.get(frameIndex);

        getSprite().setOrigin(frame.getX(), frame.getY());

        currentFrame = frameIndex;
    }


    public int getFrameDuration(int index)
    {
        return animationFrames.get(index).getLength();
    }


    public Sprite getSprite()
    {
        return sprite;
    }


    public Point getPosition()
    {
        return getSprite().getPosition();
    }

}
