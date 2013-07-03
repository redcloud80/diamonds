package it.diamonds.tests.droppable;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.SAPPHIRE;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import static it.diamonds.droppable.types.DroppableType.CHEST;
import static it.diamonds.droppable.types.DroppableType.GEM;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.RandomDroppableFactory;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.gems.FlashingGem;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockRandomGenerator;


public class TestRandomGemFactory extends GridTestCase
{

    private RandomDroppableFactory factory;

    private final int randomIntArray[] = { 4, 0, 4, 0, 0, 1 };

    private int startFlash;

    private int endFlash;

    private int startChest;

    private int endChest;

    private int startGem;

    private int endGem;


    public void setUp()
    {
        super.setUp();

        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(randomIntArray));

        int chestProb = environment.getConfig().getInteger("ChestProbability");
        int flashProb = environment.getConfig().getInteger("FlashProbability");

        startFlash = 0;
        endFlash = flashProb - 1;
        startChest = flashProb;
        endChest = flashProb + chestProb - 1;
        startGem = flashProb + chestProb;
        endGem = 99;

    }


    public void testConcreteRandomDroppableFactory()
    {
        factory = new RandomDroppableFactory(environment);

        assertNotNull(factory.createRandomChest());
        assertNotNull(factory.createRandomGem());
        assertNotNull(factory.createRandomDroppable());
    }


    public void testRandomGemSequence()
    {
        Droppable gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type topaz", TOPAZ, gem.getColor());

        gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type diamond", DIAMOND, gem.getColor());

        gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type diamond", TOPAZ, gem.getColor());

        gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type ruby", DIAMOND, gem.getColor());

        gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type ruby", DIAMOND, gem.getColor());

        gem = factory.createRandomGem();
        assertEquals("does not return a Gem", GEM, gem.getType());
        assertEquals("does not return Gem of type sapphire", RUBY, gem.getColor());
    }


    public void testRandomChestSequence()
    {
        Droppable gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 1 does not return Chest of type topaz", TOPAZ, gem.getColor());

        gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 2 does not return Chest of type diamond", DIAMOND, gem.getColor());

        gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 3 does not return Chest of type ruby", RUBY, gem.getColor());

        gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 4 does not return Chest of type diamond", DIAMOND, gem.getColor());

        gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 5 does not return Chest of type ruby", RUBY, gem.getColor());

        gem = factory.createRandomChest();
        assertEquals("does not return a Chest", CHEST, gem.getType());
        assertEquals("extraction 6 does not return Chest of type sapphire", SAPPHIRE, gem.getColor());
    }


    public void testCorrectGemAndChestAndFlashProportion()
    {
        int[] percentages = { startGem, 1, endGem, 1, startFlash, startChest,
            1, endChest, 1, endFlash };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(percentages));

        Droppable gem = factory.createRandomDroppable();
        assertNotNull("does not return a Gem", (Gem)gem);

        gem = factory.createRandomDroppable();
        assertNotNull("does not return a Gem", (Gem)gem);

        gem = factory.createRandomDroppable();
        assertNotNull("does not return a Flash", (FlashingGem)gem);

        gem = factory.createRandomDroppable();
        assertNotNull("does not return a Chest", (Chest)gem);

        gem = factory.createRandomDroppable();
        assertNotNull("does not return a Chest", (Chest)gem);

        gem = factory.createRandomDroppable();
        assertNotNull("does not return a Flash", (FlashingGem)gem);

    }


    public void testNotTwoFlashSequence()
    {
        int indexes[] = { startFlash, endFlash };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));

        Droppable gem = factory.createRandomDroppable();
        assertSame("does return a Flash", gem.getType(), DroppableType.FLASHING_GEM);

        gem = factory.createRandomDroppable();
        assertNotSame("does not return a Flash", gem.getType(), DroppableType.FLASHING_GEM);
    }


    public void testSameIndexForGemAndChest()
    {
        int indexes[] = { 1, 0, 1 };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));

        Droppable gem = factory.createRandomGem();
        assertSame("The gem is not a ruby", gem.getColor(), RUBY);
        assertSame("The gem is not a gem", gem.getType(), GEM);

        factory.createRandomGem();

        gem = factory.createRandomChest();
        assertSame("The chest is not a chest", gem.getType(), CHEST);
        assertSame("It is not a Chest of type ruby", gem.getColor(), RUBY);
    }


    public void testNotTwoEqualChestSequence()
    {
        int indexes[] = { 2, 1 };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));

        assertNotSame("The two chest are of the same type", factory.createRandomChest().getColor(), factory.createRandomChest().getColor());
    }


    public void testFlashChestFlashSequence()
    {
        int indexes[] = { startFlash, startChest, 1, endFlash };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));

        Droppable gem = factory.createRandomDroppable();
        assertSame("does return a Flash", gem.getType(), DroppableType.FLASHING_GEM);

        gem = factory.createRandomDroppable();
        assertSame("does not return a Flash", gem.getType(), DroppableType.CHEST);

        gem = factory.createRandomDroppable();
        assertSame("does return a Flash", gem.getType(), DroppableType.FLASHING_GEM);
    }


    public void testChestGemChestSequence()
    {
        int indexes[] = { startChest, 1, startGem, 4, endChest, 1 };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));

        DroppableType firstChest = factory.createRandomDroppable().getType();
        factory.createRandomDroppable().getType();
        DroppableType lastChest = factory.createRandomDroppable().getType();
        assertSame("first chest was not of same type of last chest", firstChest, lastChest);
    }


    public void testGemGemChestGemSequence()
    {
        int indexes[] = { startGem, 1, startGem, 2, startChest, 3, startGem, 1 };
        factory = new RandomDroppableFactory(environment, new MockRandomGenerator(indexes));
        DroppableType firstGem = factory.createRandomDroppable().getType();
        factory.createRandomDroppable().getType();
        factory.createRandomDroppable().getType();
        DroppableType lastGem = factory.createRandomDroppable().getType();
        assertSame("first Gem was not of same type of last Gem", firstGem, lastGem);
    }

}
