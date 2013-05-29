package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.SAPPHIRE;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.tests.EnvironmentTestCase;


public class TestStone extends EnvironmentTestCase
{
    private DroppableFactory gemFactory;

    private Droppable stone;

    private int animationUpdateRate;


    protected void setUp()
    {
        super.setUp();
        gemFactory = new DroppableFactory(environment);
        stone = gemFactory.createStone(DIAMOND);
        animationUpdateRate = environment.getConfig().getInteger("GemAnimationUpdateRate");
    }


    public void testStoneScores()
    {
        assertEquals("Stone's score must be 0", 0, gemFactory.createStone(EMERALD).getScore());
        assertEquals("Stone's score must be 0", 0, gemFactory.createStone(RUBY).getScore());
        assertEquals("Stone's score must be 0", 0, gemFactory.createStone(SAPPHIRE).getScore());
        assertEquals("Stone's score must be 0", 0, gemFactory.createStone(TOPAZ).getScore());
        assertEquals("Stone's score must be 0", 0, gemFactory.createStone(DIAMOND).getScore());
    }


    public void testStoneNumberOfFrames()
    {
        assertEquals("Stone must have 8 frames", 8, stone.getAnimatedSprite().getNumberOfFrames());
    }


    public void updateOnce()
    {
        stone.update(0);
        stone.update(animationUpdateRate);
    }


    public void testNoFrameAdvanceBeforeFifthFrame()
    {
        stone.getAnimatedSprite().setCurrentFrame(0);
        updateOnce();
        assertEquals(0, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testNoUpdateAtFirstTime()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        stone.update(1234);
        assertEquals(5, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testNoFrameChangeBeforeTimerInterval()
    {
        stone.getAnimatedSprite().setCurrentFrame(6);
        stone.update(0);
        stone.update(animationUpdateRate / 2);
        assertEquals(6, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testFrameChangesWithinRange()
    {
        stone.getAnimatedSprite().setCurrentFrame(7);
        stone.update(animationUpdateRate);
        assertEquals(7, stone.getAnimatedSprite().getCurrentFrame());
    }

}
