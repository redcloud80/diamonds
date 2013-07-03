package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.NO_COLOR;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.SAPPHIRE;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import static it.diamonds.droppable.types.DroppableType.CHEST;
import static it.diamonds.droppable.types.DroppableType.FLASHING_GEM;
import static it.diamonds.droppable.types.DroppableType.GEM;
import static it.diamonds.droppable.types.DroppableType.MORPHING_GEM;
import static it.diamonds.droppable.types.DroppableType.STONE;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.tests.EnvironmentTestCase;


public class TestGemFactory extends EnvironmentTestCase
{
    private DroppableFactory factory;


    public void setUp()
    {
        super.setUp();
        factory = new DroppableFactory(environment);
    }


    public void testGemCreation()
    {
        assertDroppableCreation(GEM, DIAMOND);
        assertDroppableCreation(GEM, RUBY);
        assertDroppableCreation(GEM, SAPPHIRE);
        assertDroppableCreation(GEM, EMERALD);
        assertDroppableCreation(GEM, TOPAZ);
    }


    private void assertDroppableCreation(DroppableType type, DroppableColor color)
    {
        assertDroppable(factory.create(type, color, 0), type, color, getGemAnimationDelay());
    }


    private void assertDroppable(Droppable droppable, DroppableType type, DroppableColor color)
    {
        assertDroppable(droppable, type, color, getGemAnimationDelay());
    }


    private void assertDroppable(Droppable droppable, DroppableType type, DroppableColor color, int gemAnimationDelay)
    {
        assertEquals("does not return a Gem", type, droppable.getType());
        assertEquals("does not return Gem of type diamond", color, droppable.getColor());

        int numOfFrame = droppable.getAnimatedSprite().getNumberOfFrames();

        assertEquals(gemAnimationDelay, droppable.getAnimatedSprite().getFrameDuration(0));
        for (int frame = 1; frame < numOfFrame; frame++)
        {
            assertEquals(getGemAnimationUpdateRate(), droppable.getAnimatedSprite().getFrameDuration(frame));
        }
    }


    public void testChestCreation()
    {
        assertDroppableCreation(CHEST, DIAMOND);
        assertDroppableCreation(CHEST, RUBY);
        assertDroppableCreation(CHEST, SAPPHIRE);
        assertDroppableCreation(CHEST, EMERALD);
        assertDroppableCreation(CHEST, TOPAZ);
    }


    public void testStoneCreation()
    {
        assertDroppable(factory.create(STONE, DIAMOND, 0), STONE, NO_COLOR, 0);
        assertDroppable(factory.create(STONE, RUBY, 0), STONE, NO_COLOR, 0);
        assertDroppable(factory.create(STONE, SAPPHIRE, 0), STONE, NO_COLOR, 0);
        assertDroppable(factory.create(STONE, EMERALD, 0), STONE, NO_COLOR, 0);
        assertDroppable(factory.create(STONE, TOPAZ, 0), STONE, NO_COLOR, 0);
    }


    public void testMorphingGemCreation()
    {
        assertDroppableCreation(MORPHING_GEM, DIAMOND);
        assertDroppableCreation(MORPHING_GEM, RUBY);
        assertDroppableCreation(MORPHING_GEM, SAPPHIRE);
        assertDroppableCreation(MORPHING_GEM, EMERALD);
        assertDroppableCreation(MORPHING_GEM, TOPAZ);
    }


    public void testFlashGemCreation()
    {
        assertDroppable(factory.create(FLASHING_GEM, NO_COLOR, 0), FLASHING_GEM, NO_COLOR, 0);
    }


    public void testCreateFlashingGem()
    {
        assertDroppable(factory.createFlashingGem(), FLASHING_GEM, NO_COLOR, 0);
    }


    public void testCreateMorphingGem()
    {
        assertDroppable(factory.createMorphingGem(DIAMOND), MORPHING_GEM, DIAMOND);
        assertDroppable(factory.createMorphingGem(RUBY), MORPHING_GEM, RUBY);
        assertDroppable(factory.createMorphingGem(SAPPHIRE), MORPHING_GEM, SAPPHIRE);
        assertDroppable(factory.createMorphingGem(EMERALD), MORPHING_GEM, EMERALD);
        assertDroppable(factory.createMorphingGem(TOPAZ), MORPHING_GEM, TOPAZ);
    }


    public void testCreateStone()
    {
        assertDroppable(factory.createStone(DIAMOND), STONE, NO_COLOR, 0);
        assertDroppable(factory.createStone(RUBY), STONE, NO_COLOR, 0);
        assertDroppable(factory.createStone(SAPPHIRE), STONE, NO_COLOR, 0);
        assertDroppable(factory.createStone(EMERALD), STONE, NO_COLOR, 0);
        assertDroppable(factory.createStone(TOPAZ), STONE, NO_COLOR, 0);
    }

}
