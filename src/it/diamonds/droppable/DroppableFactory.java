package it.diamonds.droppable;


import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Environment;
import it.diamonds.engine.audio.Sound;


public class DroppableFactory
{
    private int gemAnimationUpdateRate;

    private int gemAnimationDelay;

    private Environment environment;


    public DroppableFactory(Environment environment)
    {
        setupDropSound(environment);

        environment.getEngine();

        this.environment = environment;

        gemAnimationUpdateRate = environment.getConfig().getInteger("GemAnimationUpdateRate");
        gemAnimationDelay = environment.getConfig().getInteger("GemAnimationDelay");
    }


    private static void setupDropSound(Environment environment)
    {
        Sound dropSound = environment.getAudio().createSound("diamond");
        AbstractSingleDroppable.setDropSound(dropSound);
    }


    public Droppable create(DroppableType type, DroppableColor color, long lastUpdateTimeStamp)
    {
        return type.createInstance(environment, color, gemAnimationDelay, gemAnimationUpdateRate, lastUpdateTimeStamp);
    }


    public Droppable createGem(DroppableColor color)
    {
        return create(DroppableType.GEM, color, 0);
    }


    public Droppable createChest(DroppableColor color)
    {
        return create(DroppableType.CHEST, color, 0);
    }


    public Droppable createFlashingGem()
    {
        return create(DroppableType.FLASHING_GEM, null, 0);
    }


    public Droppable createStone(DroppableColor color)
    {
        return create(DroppableType.STONE, color, 0);
    }


    public Droppable createMorphingGem(DroppableColor color)
    {
        return create(DroppableType.MORPHING_GEM, color, 0);
    }
}
