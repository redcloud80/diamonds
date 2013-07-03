package it.diamonds.tests.engine.video;


import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestAnimatedSpriteRendering extends EnvironmentTestCase
{
    private static final int ANIMATION_DELAY = 3500;

    private static final int FRAME_SIZE = 32;

    private static final Rectangle DEFAULT_TEXTURE_AREA = new Rectangle(0, 0, FRAME_SIZE, FRAME_SIZE);

    private MockEngine engine;


    protected void setUp()
    {
        super.setUp();
        engine = (MockEngine)environment.getEngine();
    }


    private AnimatedSprite createAnimatedSprite(int frameCount, int updateRate, int frameSize)
    {
        Sprite sprite = new Sprite(0, 0, DEFAULT_TEXTURE_AREA, engine.createImage("diamond.png"));
        AnimatedSprite animatedSprite = new AnimatedSprite(sprite, new AnimationDescription(frameCount, ANIMATION_DELAY, updateRate));

        return animatedSprite;
    }


    public void testRenderingOfFirstFrame()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(2, 100, FRAME_SIZE);

        animatedSprite.updateAnimation(0);
        animatedSprite.getSprite().draw(engine);

        Rectangle expectedImageRectangle = new Rectangle(0, 0, FRAME_SIZE, FRAME_SIZE);
        assertEquals(expectedImageRectangle, engine.getImageRect());
    }


    public void testRenderingOfSecondFrame()
    {
        AnimatedSprite animatedSprite = createAnimatedSprite(2, 100, FRAME_SIZE);

        animatedSprite.updateAnimation(ANIMATION_DELAY);
        animatedSprite.getSprite().draw(engine);

        Rectangle expectedImageRectangle = new Rectangle(0, FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
        assertEquals(expectedImageRectangle, engine.getImageRect());
    }


    public void testRenderingOfThirdFrame()
    {
        final int animationUpdateRate = 100;
        AnimatedSprite animatedSprite = createAnimatedSprite(3, animationUpdateRate, FRAME_SIZE);

        animatedSprite.updateAnimation(ANIMATION_DELAY + animationUpdateRate);
        animatedSprite.getSprite().draw(engine);

        Rectangle expectedImageRectangle = new Rectangle(0, FRAME_SIZE * 2, FRAME_SIZE, FRAME_SIZE);
        assertEquals(expectedImageRectangle, engine.getImageRect());
    }

}
