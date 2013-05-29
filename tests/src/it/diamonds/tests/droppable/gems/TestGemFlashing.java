package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.DroppableColor.TOPAZ;
import static it.diamonds.droppable.types.DroppableType.CHEST;
import static it.diamonds.droppable.types.DroppableType.GEM;
import it.diamonds.droppable.Droppable;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGemFlashing extends GridTestCase
{

    public void setUp()
    {
        super.setUp();

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());
    }


    public void testFlashingGemAnimationLenght()
    {
        Droppable gem = createFlashingGem();
        assertEquals("Flashing gem animation lenght must be 8", 8, gem.getAnimatedSprite().getNumberOfFrames());
    }


    public void testFlashDeleteOrderDown()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 1);
        insertAndUpdate(createFlashingGem(), 12, 1);
        insertAndUpdate(createGem(TOPAZ), 11, 1);

        insertAndUpdate(createGem(EMERALD), 13, 0);
        insertAndUpdate(createGem(EMERALD), 12, 0);

        insertAndUpdate(createGem(RUBY), 13, 2);
        insertAndUpdate(createGem(RUBY), 12, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertEquals(grid.getDroppableAt(Cell.create(12, 1)).getColor(), TOPAZ);
        assertNull(grid.getDroppableAt(Cell.create(13, 1)));

        assertEquals(grid.getDroppableAt(Cell.create(13, 0)).getColor(), EMERALD);
        assertEquals(grid.getDroppableAt(Cell.create(12, 0)).getColor(), EMERALD);
        assertEquals(grid.getDroppableAt(Cell.create(13, 2)).getColor(), RUBY);
        assertEquals(grid.getDroppableAt(Cell.create(12, 2)).getColor(), RUBY);
    }


    public void testFlashDeleteOrderLeft()
    {
        insertAndUpdate(createFlashingGem(), 13, 1);
        insertAndUpdate(createGem(TOPAZ), 12, 1);

        insertAndUpdate(createGem(EMERALD), 13, 0);

        insertAndUpdate(createGem(RUBY), 13, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertEquals(grid.getDroppableAt(Cell.create(13, 1)).getColor(), TOPAZ);
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));

        assertEquals(grid.getDroppableAt(Cell.create(13, 2)).getColor(), RUBY);
    }


    public void testFlashDeleteOrderRight()
    {
        insertAndUpdate(createFlashingGem(), 13, 1);
        insertAndUpdate(createGem(TOPAZ), 12, 1);

        insertAndUpdate(createGem(RUBY), 13, 2);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertEquals(grid.getDroppableAt(Cell.create(13, 1)).getColor(), TOPAZ);
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));

        assertNull(grid.getDroppableAt(Cell.create(13, 2)));
    }


    public void testFlashDeleteOrderUp()
    {
        insertAndUpdate(createFlashingGem(), 13, 1);
        insertAndUpdate(createGem(TOPAZ), 12, 1);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));
    }


    public void testFlashStartByChest()
    {
        insertAndUpdate(createChest(TOPAZ), 13, 1);
        insertAndUpdate(createFlashingGem(), 12, 1);
        insertAndUpdate(createChest(TOPAZ), 13, 7);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));
        assertNull(grid.getDroppableAt(Cell.create(13, 7)));
    }


    public void testFlashDeleteChest()
    {
        insertAndUpdate(createGem(TOPAZ), 13, 1);
        insertAndUpdate(createFlashingGem(), 12, 1);

        insertAndUpdate(createChest(TOPAZ), 13, 7);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));
        assertNull(grid.getDroppableAt(Cell.create(13, 7)));
    }


    public void testFlashNotDeleteNothing()
    {
        insertAndUpdate(createFlashingGem(), 13, 1);

        insertAndUpdate(createChest(TOPAZ), 13, 7);
        insertAndUpdate(createGem(EMERALD), 13, 6);
        insertAndUpdate(createGem(DIAMOND), 13, 5);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), null, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertEquals(grid.getDroppableAt(Cell.create(13, 7)).getType(), CHEST);
        assertEquals(grid.getDroppableAt(Cell.create(13, 6)).getType(), GEM);
        assertEquals(grid.getDroppableAt(Cell.create(13, 5)).getType(), GEM);
    }


    public void testFlashGemStartOnBigGem()
    {
        insertBigGem(DIAMOND, 12, 0, 13, 1);
        insertAndUpdate(createFlashingGem(), 11, 0);

        insertAndUpdate(createGem(DIAMOND), 13, 7);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
        assertNull(grid.getDroppableAt(Cell.create(12, 0)));
        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));
        assertNull(grid.getDroppableAt(Cell.create(11, 0)));
        assertNull(grid.getDroppableAt(Cell.create(13, 7)));

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
    }


    public void testFlashGemDeleteBigGem()
    {
        insertBigGem(DIAMOND, 12, 0, 13, 1);

        insertAndUpdate(createGem(DIAMOND), 13, 7);
        insertAndUpdate(createFlashingGem(), 12, 7);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
        assertNull(grid.getDroppableAt(Cell.create(12, 0)));
        assertNull(grid.getDroppableAt(Cell.create(13, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 1)));
        assertNull(grid.getDroppableAt(Cell.create(12, 7)));
        assertNull(grid.getDroppableAt(Cell.create(13, 7)));

        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
    }


    public void testFlashGemDisappearsAfterDrop()
    {
        insertAndUpdate(createFlashingGem(), 11, 0);

        insertAndDropGemsPair();
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNull(grid.getDroppableAt(Cell.create(11, 0)));
        assertNull(grid.getDroppableAt(Cell.create(13, 0)));
    }
}
