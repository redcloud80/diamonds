package it.diamonds.tests.engine.modifiers;


import it.diamonds.engine.Point;
import it.diamonds.engine.modifiers.Pulsation;
import it.diamonds.engine.video.Sprite;
import it.diamonds.tests.mocks.MockEngine;
import junit.framework.TestCase;


public class TestPulsation extends TestCase
{
    private static final int PULSATION_LENGHT = 3;

    private static final float SIZE_MULTIPLIER = 2.5f;

    private static final float ANGLE_STEP = (float)Math.PI / PULSATION_LENGHT;

    private Pulsation pulsation;

    private MockEngine engine;

    private Sprite sprite;


    public void setUp() throws Exception
    {
        pulsation = new Pulsation(PULSATION_LENGHT, SIZE_MULTIPLIER);
        engine = MockEngine.create(800, 600);
        sprite = new Sprite(new Point(100, 200), engine.createImage("diamond"));
    }


    public void testGetSizeMultiplier()
    {
        assertEquals(SIZE_MULTIPLIER, pulsation.getSizeMultiplier());
    }


    public void testGetAngleStep()
    {
        assertEquals(ANGLE_STEP, pulsation.getAngleStep());
    }


    public void testUpdateState()
    {
        assertEquals(0f, pulsation.getCurrentAngle());

        pulsation.updateModifierState();
        assertEquals(ANGLE_STEP, pulsation.getCurrentAngle());

        pulsation.updateModifierState();
        assertEquals(2 * ANGLE_STEP, pulsation.getCurrentAngle());
    }


    public void testEnding()
    {
        assertFalse(pulsation.isCompleted());
    }


    public void testIsDrawn()
    {
        pulsation.draw(sprite, engine);
        checkDrawingRect();
    }


    public void testIsDrawnAfterUpdate()
    {
        pulsation.updateModifierState();
        pulsation.draw(sprite, engine);

        checkDrawingRect();
    }


    public void testIsDrawnAfterMoreUpdate()
    {
        pulsation.updateModifierState();
        pulsation.updateModifierState();
        pulsation.updateModifierState();

        pulsation.draw(sprite, engine);

        checkDrawingRect();
    }


    private void checkDrawingRect()
    {
        float delta = (float)Math.abs(Math.sin(pulsation.getCurrentAngle())
            * pulsation.getSizeMultiplier());

        float top = sprite.getPosition().getY() - (delta / 2);
        float left = sprite.getPosition().getX() - (delta / 2);
        float width = sprite.getTextureArea().getWidth() + delta;
        float height = sprite.getTextureArea().getHeight() + delta;

        assertEquals("Sprite not correctly drawn", 1, engine.getNumberOfQuadsDrawn());
        assertEquals("Sprite drawn in wrong position", new Point(left, top), engine.getQuadPosition());
        assertEquals("Sprite drawn with wrong width", width, engine.getQuadWidth());
        assertEquals("Sprite drawn with wrong height", height, engine.getQuadHeight());
    }
}
