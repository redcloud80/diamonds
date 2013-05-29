package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.SAPPHIRE;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.gems.MorphingGem;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestStoneUpdate extends GridTestCase
{

    private DroppableFactory gemFactory;


    public void setUp()
    {
        super.setUp();

        gemFactory = new DroppableFactory(environment);
    }


    public void testStoneIncrement()
    {
        Droppable gem = createStone(SAPPHIRE);
        gem.getAnimatedSprite().setCurrentFrame(0);
        Cell cell = Cell.create(0, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateStones();

        assertEquals("Stone not updated correctly", 1, gem.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneIncrementTwice()
    {
        Droppable gem = createStone(SAPPHIRE);
        gem.getAnimatedSprite().setCurrentFrame(1);
        Cell cell = Cell.create(0, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateStones();

        assertEquals("Stone non updated correctly", 2, gem.getAnimatedSprite().getCurrentFrame());
    }


    public void testNotStoneNotIncremented()
    {
        Droppable gem = createGem(SAPPHIRE);
        gem.getAnimatedSprite().setCurrentFrame(0);
        Cell cell = Cell.create(0, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);

        grid.updateStones();

        assertEquals("Stone non updated correctly", 0, gem.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneIncrementOnInsertGemsPair()
    {
        insertAndUpdate(gemFactory.createStone(SAPPHIRE), 13, 0);
        assertEquals(0, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
        insertAndDropGemsPair();
        advanceToNextInsert();
        assertEquals(1, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
        advanceToNextInsert();
        assertEquals(2, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
        advanceToNextInsert();
        assertEquals(3, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
        advanceToNextInsert();
        assertEquals(4, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneNotIncrementAfterCrush()
    {
        insertAndUpdate(gemFactory.createStone(SAPPHIRE), 13, 0);
        assertEquals(0, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
        controller.insertNewGemsPair(grid);
        insertAndUpdate(gemFactory.create(DroppableType.GEM, DIAMOND, 0), 12, 2);
        insertAndUpdate(gemFactory.create(DroppableType.CHEST, DIAMOND, 0), 11, 2);
        while (droppedGemCanMoveDown(grid))
        {
            controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        }
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getDelayBetweenCrushes());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertEquals("The stone framemust not be incremented after a crush", 1, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
    }


    public void testCreationBigGemOnInsertGemsPair()
    {
        Droppable[] stones = new MorphingGem[4];
        for (int i = 0; i < 4; i++)
        {
            stones[i] = gemFactory.createMorphingGem(SAPPHIRE);
            stones[i].getAnimatedSprite().setCurrentFrame(7);
        }

        insertAndUpdate(stones[0], 13, 0);
        insertAndUpdate(stones[1], 12, 0);
        insertAndUpdate(stones[2], 13, 1);
        insertAndUpdate(stones[3], 12, 1);

        insertAndDropGemsPair();

        advanceToNextInsert();

        assertEquals("The gems from stones must generate a BigGem", DroppableType.BIG_GEM, grid.getDroppableAt(Cell.create(13, 0)).getType());
    }


    public void testCreationdBigGemWithAlreadyPresentGemOnInsertGemsPair()
    {
        Droppable[] stones = new MorphingGem[2];
        for (int i = 0; i < 2; i++)
        {
            stones[i] = gemFactory.createMorphingGem(SAPPHIRE);
            stones[i].getAnimatedSprite().setCurrentFrame(7);
        }

        insertAndUpdate(createGem(SAPPHIRE), 13, 0);
        insertAndUpdate(createGem(SAPPHIRE), 12, 0);
        insertAndUpdate(stones[0], 13, 1);
        insertAndUpdate(stones[1], 12, 1);

        insertAndDropGemsPair();
        advanceToNextInsert();

        assertEquals("The gems from stones must generate a BigGem", DroppableType.BIG_GEM, grid.getDroppableAt(Cell.create(13, 0)).getType());
    }


    private void advanceToNextInsert()
    {
        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertNotNull(grid.getDroppableAt(Cell.create(0, 4)));
        assertNotNull(grid.getDroppableAt(Cell.create(1, 4)));

        grid.removeDroppable(grid.getDroppableAt(Cell.create(13, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(12, 4)));
    }


    public void testStoneIncrementTiming()
    {
        insertAndUpdate(gemFactory.createStone(SAPPHIRE), 13, 0);

        insertAndDropGemsPair();

        makeAllGemsFall();

        controller.update(environment.getTimer().getTime(), null, grid);
        environment.getTimer().advance(getUpdateRate());
        controller.update(environment.getTimer().getTime(), null, grid);
        assertEquals(1, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());

        environment.getTimer().advance(getNewGemDelay());
        controller.update(environment.getTimer().getTime(), null, grid);

        grid.removeDroppable(grid.getDroppableAt(Cell.create(13, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(12, 4)));
        assertEquals(1, grid.getDroppableAt(Cell.create(13, 0)).getAnimatedSprite().getCurrentFrame());
    }


    public void testExStonePosition()
    {
        Droppable stone = gemFactory.createStone(DroppableColor.EMERALD);
        Cell cell = Cell.create(5, 6);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        stone.getAnimatedSprite().setCurrentFrame(6);

        grid.updateStones();

        Droppable gem = grid.getDroppableAt(Cell.create(5, 6));
        assertEquals(192.0f, gem.getPositionInGridLocalSpace().getX());
        assertEquals(160.0f, gem.getPositionInGridLocalSpace().getY());
    }


    public void testStoneIsUpdatedIfGridContainsGems()
    {
        Droppable stone = gemFactory.createStone(DroppableColor.EMERALD);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(5, 7);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        Cell cell1 = Cell.create(5, 6);
        stone.getRegion().setRow(cell1.getRow());
        stone.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(stone);

        stone.getAnimatedSprite().setCurrentFrame(0);

        grid.updateStones();

        assertEquals(1, stone.getAnimatedSprite().getCurrentFrame());
    }

}
