package it.diamonds.tests.gui;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.tests.helper.ComponentHelperForTest.insertAndUpdate;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.Point;
import it.diamonds.engine.modifiers.SinglePulsation;
import it.diamonds.gui.CrushBox;
import it.diamonds.tests.PlayFieldTestCase;


public class TestCrushBoxOnPlayField extends PlayFieldTestCase
{
    private CrushBox crushBox;

    private Point origin;


    public void setUp()
    {
        super.setUp();

        crushBox = field.getCrushBox();

        origin = fieldDescriptorOne.getCrushBoxOrigin();
    }


    public void testSingleCrush()
    {
        makeAllGemsFall();

        createCrush(new DroppableColor[] { DIAMOND }, 0);

        updateLoop(getUpdateRate());

        assertIsLikeOnCreation();
    }


    public void testDoubleCrush()
    {
        makeAllGemsFall();

        createCrush(new DroppableColor[] { DIAMOND, EMERALD }, 0);

        updateLoop(getUpdateRate());

        makeAllGemsFall();

        updateLoop(getUpdateRate());
        updateLoop(getDelayBetweenCrushes());

        assertAfterUpdate(2);
    }


    public void testTripleCrush()
    {
        makeAllGemsFall();

        createCrush(new DroppableColor[] { DIAMOND, EMERALD, RUBY }, 0);

        updateLoop(getUpdateRate());

        makeAllGemsFall();

        updateLoop(getUpdateRate());
        updateLoop(getDelayBetweenCrushes());

        makeAllGemsFall();

        updateLoop(getUpdateRate());
        updateLoop(getDelayBetweenCrushes());

        assertAfterUpdate(3);
    }


    public void testCrushBoxIsNotPulsingAfterCrush()
    {
        testDoubleCrush();

        makeCrushBoxNotPulsing();
    }


    public void testCrushBoxIsOutOfScreenAfterCrush()

    {
        testDoubleCrush();

        makeCrushBoxNotPulsing();

        makeCrushGoOutOfLeftScreen();
    }


    public void testNewCrushAfterCrush()
    {
        testCrushBoxIsOutOfScreenAfterCrush();

        testCrushBoxIsOutOfScreenAfterCrush();
    }


    public void testCreation()
    {
        assertIsLikeOnCreation();
    }


    public void testFlashCrush()
    {
        makeAllGemsFall();

        insertAndUpdate(grid, createGem(DIAMOND), 13, 0);
        insertAndUpdate(grid, createFlashingGem(), 13, 1);

        updateLoop(getUpdateRate());

        assertIsLikeOnCreation();
    }


    private void assertIsLikeOnCreation()
    {
        TestCrushBox.assertIsLikeOnCreation(crushBox, origin);
    }


    private void assertAfterUpdate(int crush)
    {
        TestCrushBox.assertAfterUpdate(crushBox, origin, crush);
    }


    private void makeCrushBoxNotPulsing()
    {
        SinglePulsation sp = TestCrushBox.getCrushBoxSinglePulsation(environment);
        while (!sp.isCompleted())
        {
            assertAfterUpdate(2);

            updateLoop(getFrameRate());

            sp.updateModifierState();
        }
        assertFalse(crushBox.isPulsing());
    }


    private void makeCrushGoOutOfLeftScreen()
    {
        int step = TestCrushBox.getStepOutLeftScreen(crushBox, getCrushBoxSpeed());

        for (int i = 0; i < step; i++)
        {
            TestCrushBox.assertNotOutLeftScreen(crushBox);
            updateLoop(getFrameRate());
        }
        TestCrushBox.assertOutLeftScreen(crushBox);
    }


    private void createCrush(DroppableColor[] colors, int column)
    {
        for (int i = 0; i < colors.length; i++)
        {
            insertAndUpdate(grid, createGem(colors[i]), 13 - i, column);
        }

        for (int i = colors.length - 1; i >= 0; i--)
        {
            insertAndUpdate(grid, createChest(colors[i]), 13
                - (colors.length * 2) + i, column);
        }
    }
}
