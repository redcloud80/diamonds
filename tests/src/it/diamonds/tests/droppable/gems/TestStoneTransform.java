package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.types.DroppableType.STONE;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.gems.MorphingGem;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestStoneTransform extends GridTestCase
{

    private DroppableFactory gemFactory;

    private Droppable stone;


    public void setUp()
    {
        super.setUp();

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());

        gemFactory = new DroppableFactory(environment);
        stone = gemFactory.create(STONE, DIAMOND, 0);
        Cell cell = Cell.create(13, 5);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
    }


    public void testStoneNotTransformed()
    {
        Droppable newStone = stone.transform(controller);
        assertSame("newStone must be same of stone", stone, newStone);
    }


    public void testStoneTransformed()
    {
        stone.getAnimatedSprite().setCurrentFrame(7);
        Droppable droppable = stone.transform(controller);
        assertNotSame("droppable must be not same of stone", droppable, stone);
    }


    public void testStoneTransforming()
    {
        stone.getAnimatedSprite().setCurrentFrame(6);
        Droppable droppable = stone.transform(controller);
        assertTrue(droppable instanceof MorphingGem);
    }

}
