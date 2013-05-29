package it.diamonds.tests.gui;


import it.diamonds.engine.Environment;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.modifiers.SinglePulsation;
import it.diamonds.gui.CrushBox;
import it.diamonds.tests.EnvironmentTestCase;


public class TestCrushBox extends EnvironmentTestCase
{
    private Point origin;

    private CrushBox crushBox;


    public void setUp()
    {
        super.setUp();

        origin = new Point(20, 20);

        crushBox = new CrushBox(environment, origin);
    }


    public void testImageLoadedOnEngine()
    {
        for (int i = 2; i < 10; i++)
        {
            assertTrue(mockEngine.isImageCreated("gfx/common/crush/0" + i));
        }
        assertTrue(mockEngine.isImageCreated("gfx/common/crush/over"));
    }


    public void testCreation()
    {
        assertIsLikeOnCreation();
    }


    private void assertIsLikeOnCreation()
    {
        assertIsLikeOnCreation(crushBox, origin);
    }


    public static void assertIsLikeOnCreation(CrushBox crushBox, Point origin)
    {
        assertEquals(origin, crushBox.getPosition());
        assertEquals(new Rectangle(0, 0, 256, 64), crushBox.getTextureArea());
        assertEquals("gfx/common/crush/02", crushBox.getTexture().getName());
        assertTrue(crushBox.isHidden());
        assertFalse(crushBox.isPulsing());
    }


    public void testShowMoveSpriteOnCrushBoxOrigin()
    {
        crushBox.setPosition(0, 0);

        assertEquals(new Point(0, 0), crushBox.getPosition());

        crushBox.show();

        assertEquals(origin, crushBox.getPosition());
    }


    public void testTextureSetOnUpdate()
    {
        for (int i = 2; i < 10; i++)
        {
            crushBox.update(i);
            assertEquals("gfx/common/crush/0" + i, crushBox.getTexture().getName());
        }
        crushBox.update(10);
        assertEquals("gfx/common/crush/over", crushBox.getTexture().getName());

        crushBox.update(100);
        assertEquals("gfx/common/crush/over", crushBox.getTexture().getName());
    }


    public void testUselessUpdate()
    {
        crushBox.update(0);
        assertIsLikeOnCreation();

        crushBox.update(1);
        assertIsLikeOnCreation();
    }


    public void testRealUpdate()
    {
        int numberOfCrushToTest = 15;
        for (int i = 2; i < numberOfCrushToTest; i++)
        {
            crushBox = new CrushBox(environment, origin);
            crushBox.update(i);

            assertAfterUpdate(i);
        }
    }


    private void assertAfterUpdate(int crush)
    {
        assertAfterUpdate(crushBox, origin, crush);
    }


    public static void assertAfterUpdate(CrushBox crushBox, Point origin, int crush)
    {
        String textureCrush = "gfx/common/crush/";

        textureCrush += crush < 10 ? "0" + crush : "over";

        assertEquals(origin, crushBox.getPosition());
        assertEquals(new Rectangle(0, 0, 256, 64), crushBox.getTextureArea());
        assertEquals(textureCrush, crushBox.getTexture().getName());
        assertFalse("CrushBox must not be hidden", crushBox.isHidden());
        assertTrue("CrushBox must be pulsing", crushBox.isPulsing());
    }


    public void testMinorUpdateNotChangeTexture()
    {
        crushBox.update(3);

        crushBox.update(2);

        assertEquals("gfx/common/crush/03", crushBox.getTexture().getName());
    }


    public void testStopPulsing()
    {
        SinglePulsation sp = getCrushBoxSinglePulsation(environment);

        crushBox.update(2);

        while (!sp.isCompleted())
        {
            crushBox.update(2);
            assertAfterUpdate(2);
            crushBox.draw(environment.getEngine());
            sp.updateModifierState();
        }

        crushBox.update(2);
        assertFalse(crushBox.isPulsing());
    }


    public void testStopPulsingWithMajorChangeOfTexture()
    {
        SinglePulsation sp = getCrushBoxSinglePulsation(environment);

        crushBox.update(2);

        while (!sp.isCompleted())
        {
            crushBox.update(3);
            assertAfterUpdate(3);
            assertEquals("gfx/common/crush/03", crushBox.getTexture().getName());

            crushBox.draw(environment.getEngine());
            sp.updateModifierState();
        }

        crushBox.update(3);
        assertFalse(crushBox.isPulsing());
    }


    public void testStopPulsingWithMinorChangeOfTexture()
    {
        crushBox.update(2);

        SinglePulsation sp = getCrushBoxSinglePulsation(environment);
        while (!sp.isCompleted())
        {
            crushBox.update(1);
            assertAfterUpdate(2);
            assertEquals("gfx/common/crush/02", crushBox.getTexture().getName());
            crushBox.draw(environment.getEngine());
            sp.updateModifierState();
        }

        crushBox.update(1);
        assertFalse(crushBox.isPulsing());
    }


    public static SinglePulsation getCrushBoxSinglePulsation(Environment environment)
    {
        float crushBoxSizeMultiplier = (float)environment.getConfig().getInteger("CrushBoxSizeMultiplier");
        int crushBoxPulsationLength = environment.getConfig().getInteger("PulsationLength");
        SinglePulsation sp = new SinglePulsation(crushBoxPulsationLength, crushBoxSizeMultiplier);
        return sp;
    }


    private void makeCrushBoxNotPulsing(int crush, int crushTexture)
    {
        SinglePulsation sp = getCrushBoxSinglePulsation(environment);
        while (!sp.isCompleted())
        {
            crushBox.update(crush);
            assertAfterUpdate(crushTexture);
            crushBox.draw(environment.getEngine());
            sp.updateModifierState();
        }
        crushBox.update(crush);
        assertFalse(crushBox.isPulsing());
    }


    public void testCrushBoxNotPulsingStartMovingToLeftEdgeScreen()
    {
        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        crushBox.update(1);

        assertEquals(origin.getX() - getCrushBoxSpeed(), crushBox.getPosition().getX());
    }


    public void testCrushBoxNotPulsingStartMovingToRightEdgeScreen()
    {
        initCrushBoxForPlayerTwo();

        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        crushBox.update(1);

        assertEquals(origin.getX() + getCrushBoxSpeed(), crushBox.getPosition().getX());
    }


    private void initCrushBoxForPlayerTwo()
    {
        origin = new Point(350, 20);

        crushBox = new CrushBox(environment, origin);
    }


    public void testCrushBoxMoveUntilLeftOutOfScreen()
    {
        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfLeftScreen(1);
    }


    public void testCrushBoxMoveUntilRightOutOfScreen()
    {
        initCrushBoxForPlayerTwo();

        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfRightScreen(1);
    }


    public void testCrushBoxMoveUntilLeftOutOfScreenIsNotHide()
    {
        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfLeftScreen(1);

        assertFalse(crushBox.isHidden());
    }


    public void testHideWhenOutOfLeftScreen()
    {
        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfLeftScreen(1);

        crushBox.update(1);

        assertTrue(crushBox.isHidden());
    }


    public void testCrushBoxMoveUntilRightOutOfScreenIsNotHide()
    {
        initCrushBoxForPlayerTwo();

        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfRightScreen(1);

        assertFalse(crushBox.isHidden());
    }


    public void testHideWhenOutOfRightScreen()
    {
        initCrushBoxForPlayerTwo();

        crushBox.update(2);

        makeCrushBoxNotPulsing(1, 2);

        makeCrushGoOutOfRightScreen(1);

        crushBox.update(1);

        assertTrue(crushBox.isHidden());
    }


    private void makeCrushGoOutOfLeftScreen(int crush)
    {
        int step = getStepOutLeftScreen(crushBox, getCrushBoxSpeed());

        for (int i = 0; i < step; i++)
        {
            assertNotOutLeftScreen(crushBox);
            crushBox.update(crush);
        }
        assertOutLeftScreen(crushBox);
    }


    public static int getStepOutLeftScreen(CrushBox crushBox, int crushBoxSpeed)
    {
        float distanceToGoOutOfScreen = crushBox.getPosition().getX()
            + crushBox.getTextureArea().getWidth();
        return ((int)Math.ceil(distanceToGoOutOfScreen / crushBoxSpeed));
    }


    public static void assertNotOutLeftScreen(CrushBox crushBox)
    {
        int xCrushBoxOutOfScreen = -crushBox.getTextureArea().getWidth();
        assertTrue(xCrushBoxOutOfScreen <= Math.round(crushBox.getPosition().getX()));
    }


    public static void assertOutLeftScreen(CrushBox crushBox)
    {
        int xCrushBoxOutOfScreen = -crushBox.getTextureArea().getWidth();
        assertTrue(xCrushBoxOutOfScreen > Math.round(crushBox.getPosition().getX()));
    }


    private void makeCrushGoOutOfRightScreen(int crush)
    {
        float distanceToGoOutOfScreen = getScreenWidth()
            - crushBox.getPosition().getX();

        int step = (int)Math.ceil(distanceToGoOutOfScreen / getCrushBoxSpeed());

        int xCrushBoxOutOfScreen = getScreenWidth();

        for (int i = 0; i < step; i++)
        {
            assertTrue(xCrushBoxOutOfScreen > (int)crushBox.getPosition().getX());
            crushBox.update(crush);
        }
        assertTrue(xCrushBoxOutOfScreen <= (int)crushBox.getPosition().getX());
    }


    public void testTwoDistinctDoubleCrushes()
    {
        testHideWhenOutOfLeftScreen();

        testHideWhenOutOfLeftScreen();
    }
}
