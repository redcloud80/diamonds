package it.diamonds.droppable;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.SAPPHIRE;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import it.diamonds.engine.ConcreteRandomGenerator;
import it.diamonds.engine.Environment;
import it.diamonds.engine.RandomGenerator;


public class RandomDroppableFactory
{
    private static final DroppableColor COLORS[] = { DIAMOND, RUBY, SAPPHIRE,
        EMERALD, TOPAZ };

    private RandomGenerator randomGenerator;

    private boolean firstChestExtraction = true;

    private int lastChestIndex = COLORS.length;

    private boolean lastExtractionIsFlash = false;

    private DroppableFactory droppableFactory;

    private int chestProbability;

    private int flashProbability;


    public RandomDroppableFactory(Environment environment, RandomGenerator randomGenerator)
    {
        this.randomGenerator = randomGenerator;
        this.droppableFactory = new DroppableFactory(environment);

        flashProbability = environment.getConfig().getInteger("FlashProbability");

        chestProbability = environment.getConfig().getInteger("ChestProbability")
            + flashProbability;
    }


    public RandomDroppableFactory(Environment environment)
    {
        this(environment, new ConcreteRandomGenerator());
    }


    public Droppable createRandomGem()
    {
        int module = COLORS.length;

        int randomIndex = randomGenerator.extract(module);

        resetChestExtraction();

        return droppableFactory.createGem(COLORS[randomIndex]);
    }


    private void resetChestExtraction()
    {
        firstChestExtraction = true;
        lastChestIndex = COLORS.length;
    }


    public Droppable createRandomChest()
    {
        int module = COLORS.length - 1;

        if (firstChestExtraction)
        {
            module = COLORS.length;
            firstChestExtraction = false;
        }

        int randomIndex = randomGenerator.extract(module);

        if (randomIndex >= lastChestIndex)
        {
            randomIndex = (randomIndex + 1) % COLORS.length;
        }

        lastChestIndex = randomIndex;

        return droppableFactory.createChest(COLORS[randomIndex]);
    }


    protected void resetLastExtractionFlag()
    {
        lastExtractionIsFlash = false;
    }


    public Droppable createRandomDroppable()
    {
        int random = randomGenerator.extract(100);

        return createRandomDroppable(random);
    }


    protected Droppable createRandomDroppable(int random)
    {
        if (random < flashProbability && !lastExtractionIsFlash)
        {
            resetLastExtractionFlag();
            lastExtractionIsFlash = true;
            return droppableFactory.createFlashingGem();
        }

        if (random < chestProbability)
        {
            resetLastExtractionFlag();
            return createRandomChest();
        }

        resetLastExtractionFlag();
        return createRandomGem();
    }
}
