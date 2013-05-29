package it.diamonds.tests.droppable.gems;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.Timer;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;


public class TestGemAnimation extends GridTestCase
{
    private Timer timer;

    private Droppable gem;

    private int numberOfFrames;

    private int animationUpdateRate;


    public void setUp()
    {
        super.setUp();
        timer = environment.getTimer();
        numberOfFrames = 5;
        animationUpdateRate = 100;
        gem = createGem(DroppableColor.DIAMOND, 3500, animationUpdateRate);
    }


    public void testGridUpdatesAnimationsFirstStep()
    {
        normalAnimationFirstStep();

        assertEquals("Gem animation hasn't been updated", 1, gem.getAnimatedSprite().getCurrentFrame());
    }


    public void testGridUpdatesAnimationsFirstAndSecondStep()
    {
        normalAnimationFirstStep();

        timer.advance(100);
        grid.updateDroppableAnimations(timer.getTime());

        assertEquals("Gem animation hasn't been updated", 2, gem.getAnimatedSprite().getCurrentFrame());
    }


    public void testCreateAnimationSequence()
    {
        timer.advance(3450 + animationUpdateRate * numberOfFrames);

        gem.getAnimatedSprite().updateAnimation(timer.getTime());
        gem.getAnimatedSprite().getSprite().draw(environment.getEngine());

        assertEquals("Last frame of the sequence is wrong", Cell.SIZE_IN_PIXELS
            * numberOfFrames, ((MockEngine)environment.getEngine()).getImageRect().getTop());
    }


    private void normalAnimationFirstStep()
    {
        timer.advance(3500);
        Cell cell = Cell.create(1, 1);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateDroppableAnimations(timer.getTime());
    }

}
