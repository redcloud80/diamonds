package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestStoneInGrid extends GridTestCase
{
    public void testStoneIsNotCrushingOnFlash()
    {
        insertAndUpdate(createStone(DIAMOND), 13, 0);
        insertAndUpdate(createFlashingGem(), 13, 1);

        grid.updateCrushes(scoreCalculator, null);

        assertEquals("Grid must contain 2 elements", 1,// was :2
        grid.getNumberOfDroppables());
    }


    public void testStoneIsNotAnimated()
    {
        DroppableFactory gemFactory = new DroppableFactory(environment);

        Droppable stone = gemFactory.createStone(EMERALD);
        Cell cell = Cell.create(13, 2);

        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);

        grid.updateDroppableAnimations(environment.getTimer().getTime());
        int startingFrame = stone.getAnimatedSprite().getCurrentFrame();

        environment.getTimer().advance(environment.getConfig().getInteger("GemAnimationUpdateRate") + 1);
        grid.updateDroppableAnimations(environment.getTimer().getTime());

        assertEquals("Stone must not be animated", startingFrame, stone.getAnimatedSprite().getCurrentFrame());
    }


    public void testNotFormingBigGem()
    {
        insertAndUpdate(createStone(EMERALD), 13, 4);
        insertAndUpdate(createStone(EMERALD), 12, 4);
        insertAndUpdate(createStone(EMERALD), 13, 3);
        insertAndUpdate(createStone(EMERALD), 12, 3);

        grid.updateBigGems();

        assertEquals(0, getNumberOfExtensibleObject());
    }
}
