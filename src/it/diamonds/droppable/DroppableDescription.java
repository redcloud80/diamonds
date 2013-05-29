package it.diamonds.droppable;


import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.Image;

import java.util.HashMap;


public final class DroppableDescription
{
    private static final String DROPPABLE_PATH = "gfx/droppables/";

    private static final HashMap<DroppableType, String> DROPPABLE_TYPE_NAMES = new HashMap<DroppableType, String>();

    static
    {
        DROPPABLE_TYPE_NAMES.put(DroppableType.GEM, "gems");
        DROPPABLE_TYPE_NAMES.put(DroppableType.CHEST, "boxes");
        DROPPABLE_TYPE_NAMES.put(DroppableType.FLASHING_GEM, "flashing");
        DROPPABLE_TYPE_NAMES.put(DroppableType.STONE, "stones");
        DROPPABLE_TYPE_NAMES.put(DroppableType.MORPHING_GEM, "stones");
        DROPPABLE_TYPE_NAMES.put(DroppableType.BIG_GEM, "tiles");
    }

    private static final HashMap<DroppableColor, String> DROPPABLE_COLOR_NAMES = new HashMap<DroppableColor, String>();

    static
    {
        DROPPABLE_COLOR_NAMES.put(DroppableColor.DIAMOND, "diamond");
        DROPPABLE_COLOR_NAMES.put(DroppableColor.EMERALD, "emerald");
        DROPPABLE_COLOR_NAMES.put(DroppableColor.RUBY, "ruby");
        DROPPABLE_COLOR_NAMES.put(DroppableColor.SAPPHIRE, "sapphire");
        DROPPABLE_COLOR_NAMES.put(DroppableColor.TOPAZ, "topaz");
        DROPPABLE_COLOR_NAMES.put(DroppableColor.NO_COLOR, "nocolor");
    }

    private final DroppableType type;

    private final DroppableColor color;


    public DroppableDescription(DroppableType type, DroppableColor color)
    {
        this.type = type;
        this.color = color;
    }


    public DroppableType getType()
    {
        return type;
    }


    public DroppableColor getColor()
    {
        return color;
    }


    public Image createImage(Engine engine)
    {
        final String typeName = DROPPABLE_TYPE_NAMES.get(type);
        final String colorName = DROPPABLE_COLOR_NAMES.get(color);

        return engine.createImage((DroppableDescription.DROPPABLE_PATH
            + typeName + "/" + colorName));
    }
}
