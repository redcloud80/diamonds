package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.types.DroppableType.MORPHING_GEM;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.gems.MorphingGem;
import it.diamonds.droppable.gems.Stone;
import it.diamonds.tests.GridTestCase;


public class TestMorphingGem extends GridTestCase
{
    private DroppableFactory gemFactory;

    private Droppable stone;

    private long animationUpdateRate;


    public void setUp()
    {
        super.setUp();

        gemFactory = new DroppableFactory(environment);
        stone = gemFactory.createStone(DIAMOND);
        animationUpdateRate = environment.getConfig().getInteger("GemAnimationUpdateRate");
    }


    public void testStoneBecomesMorhpingGem()
    {
        stone.getAnimatedSprite().setCurrentFrame(4);
        stone.updateTransformation();
        Droppable morphingGem = stone.transform(controller);
        assertTrue(morphingGem instanceof MorphingGem);
    }


    public void testMorhpingGemIsDropped()
    {
        stone.getAnimatedSprite().setCurrentFrame(4);
        stone.updateTransformation();
        Droppable morphingGem = stone.transform(controller);
        assertFalse(morphingGem.isFalling());
    }


    public void testStoneNotBecomesMorhpingGem()
    {
        stone.getAnimatedSprite().setCurrentFrame(2);
        stone.updateTransformation();
        stone = stone.transform(controller);
        assertTrue(stone instanceof Stone);
    }


    public void testMorphingGemKnowsLastTimestamp()
    {
        stone.getAnimatedSprite().setCurrentFrame(4);
        stone.update(34);
        stone.updateTransformation();
        Droppable morphingGem = stone.transform(controller);
        morphingGem.update(34 + animationUpdateRate);
        assertEquals(6, morphingGem.getAnimatedSprite().getCurrentFrame());
    }


    public void testAnimationNotUpdatedBeforeUpdateRateElapsed()
    {
        Droppable morphingGem = gemFactory.createMorphingGem(DIAMOND);
        morphingGem.getAnimatedSprite().setCurrentFrame(5);
        morphingGem.update(animationUpdateRate - 1);
        assertEquals(5, morphingGem.getAnimatedSprite().getCurrentFrame());
    }


    public void testMorphingGemBecomesGem()
    {
        Droppable morphingGem = gemFactory.create(MORPHING_GEM, stone.getHiddenColor(), 0);
        morphingGem.getAnimatedSprite().setCurrentFrame(6);
        morphingGem.update(animationUpdateRate);
        Droppable gem = morphingGem.transform(controller);
        assertTrue(gem instanceof Gem);
    }


    public void testGemIsIsDropped()
    {
        Droppable morphingGem = gemFactory.create(MORPHING_GEM, stone.getHiddenColor(), 0);
        morphingGem.getAnimatedSprite().setCurrentFrame(6);
        morphingGem.update(animationUpdateRate);
        Droppable gem = morphingGem.transform(controller);
        assertFalse(gem.isFalling());
    }


    public void testMorphingGemNotBecomesGemPrematurely()
    {
        Droppable morphingGem = gemFactory.create(MORPHING_GEM, stone.getHiddenColor(), 0);
        morphingGem.getAnimatedSprite().setCurrentFrame(5);
        morphingGem.update(animationUpdateRate);
        Droppable gem = morphingGem.transform(controller);
        assertTrue(gem instanceof MorphingGem);
    }


    public void testMorphingGemUpdateCurrentFrame()
    {
        Droppable morphingGem = gemFactory.create(MORPHING_GEM, stone.getHiddenColor(), 0);
        morphingGem.getAnimatedSprite().setCurrentFrame(6);
        morphingGem.update(animationUpdateRate);
        assertEquals(7, morphingGem.getAnimatedSprite().getCurrentFrame());
    }
}
