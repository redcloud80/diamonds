package it.diamonds.tests.engine.video;


import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestAnimatedSprite extends EnvironmentTestCase
{
    private static final int ANIMATION_DELAY = 3500;

    private static final int ANIMATION_RATE = 100;

    private static final int FRAME_SIZE = 32;

    private static final Rectangle DEFAULT_TEXTURE_AREA = new Rectangle(0, 0, FRAME_SIZE, FRAME_SIZE);

    private MockEngine engine;


    public void setUp()
    {
        super.setUp();
        engine = (MockEngine)environment.getEngine();
    }


    private AnimatedSprite createAnimatedSprite(int frameCount, int updateRate)
    {
        return createAnimatedSprite(frameCount, updateRate, 0);
    }


    private AnimatedSprite createAnimatedSprite(int frameCount, int updateRate, int frameSize)
    {
        Sprite sprite = new Sprite(0, 0, DEFAULT_TEXTURE_AREA, engine.createImage("diamond.png"));
        AnimatedSprite animatedSprite = new AnimatedSprite(sprite, new AnimationDescription(frameCount, ANIMATION_DELAY, updateRate));

        return animatedSprite;
    }


    public void testEmptyAnimationsAreNotAllowed()
    {
        try
        {
            createAnimatedSprite(0, 0);
        }
        catch (IllegalArgumentException exception)
        {
            return;
        }

        fail();
    }


    public void testOneFrameAnimationsAreNotAllowed()
    {
        try
        {
            createAnimatedSprite(1, 0);
        }
        catch (IllegalArgumentException exception)
        {
            return;
        }

        fail();
    }


    public void testNewAnimationWithTwoFrames()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(2, 0);

        assertEquals(2, animatedSprite.getNumberOfFrames());
    }


    public void testNewAnimationWithThreeFrames()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(3, 0);

        assertEquals(3, animatedSprite.getNumberOfFrames());
    }


    public void testDefaultCurrentFrameIsZero()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(5, 0);

        animatedSprite.updateAnimation(0);

        assertEquals(0, animatedSprite.getCurrentFrame());
    }


    public void testAnimationIsNotTriggeredBeforeDelay()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(3, ANIMATION_RATE);

        animatedSprite.updateAnimation(ANIMATION_DELAY - 1);

        assertEquals(0, animatedSprite.getCurrentFrame());
    }


    public void testAnimationIsTriggeredAfterDelay()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(3, ANIMATION_RATE);

        animatedSprite.updateAnimation(ANIMATION_DELAY);

        assertEquals(1, animatedSprite.getCurrentFrame());
    }


    public void testFirstFrameIsNotTriggeredBeforeAnimationRate()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(3, ANIMATION_RATE);

        animatedSprite.updateAnimation(ANIMATION_DELAY + ANIMATION_RATE - 1);

        assertEquals(1, animatedSprite.getCurrentFrame());
    }


    public void testFirstFrameIsTriggeredAfterAnimationRate()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(3, ANIMATION_RATE);

        animatedSprite.updateAnimation(ANIMATION_DELAY + ANIMATION_RATE);

        assertEquals(2, animatedSprite.getCurrentFrame());
    }


    public void testFrameLength()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(2, ANIMATION_RATE);

        assertEquals(ANIMATION_RATE, animatedSprite.getFrameDuration(1));
    }


    public void testFrameIsZeroAfterCompleteCycle()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(2, ANIMATION_RATE);

        animatedSprite.updateAnimation(ANIMATION_DELAY + ANIMATION_RATE);

        assertEquals(0, animatedSprite.getCurrentFrame());
    }
}
