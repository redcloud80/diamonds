package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.EMERALD;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.grid.Cell;
import it.diamonds.grid.GridController;
import it.diamonds.grid.Region;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockRandomGenerator;

import java.io.IOException;
import java.util.ConcurrentModificationException;


public class TestBigGemInGrid extends GridTestCase
{
    public void testFind2x2Block()
    {
        assertEquals("Grid must not have any BigGem", 0, getNumberOfExtensibleObject());

        insert2x2BigGem(EMERALD, 12, 3);
        grid.updateBigGems();

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void testRemoveBigGem()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        grid.updateBigGems();
        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));
        grid.removeDroppable(bigGem);

        assertEquals("Grid must not have any BigGem", 0, getNumberOfExtensibleObject());
    }


    public void testCreateTwoBigGems()
    {
        insert2x2BigGem(EMERALD, 12, 3);
        grid.updateBigGems();

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());

        insert2x2BigGem(EMERALD, 12, 6);
        grid.updateBigGems();

        assertEquals("Grid must have two BigGem", 2, getNumberOfExtensibleObject());
    }


    public void testSameBigGemMustNotBeCreatedTwice()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        grid.updateBigGems();
        grid.updateBigGems();

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void testGetBigGemAt()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));

        assertNotNull("Grid must return a BigGem object", bigGem);
        assertNull("There must not be any BigGem at 0, 0", grid.getDroppableAt(Cell.create(0, 0)));
    }


    public void testBigGemDimensions()
    {
        insert2x2BigGem(EMERALD, 12, 3);
        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));

        assertEquals(new Region(3, 12, 2, 2), bigGem.getRegion());
    }


    public void testBigGemContainsGem()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));
        Droppable gem = grid.getDroppableAt(Cell.create(12, 3));

        assertTrue("Emerald at 12,3 must be in the BigGem", bigGem.getRegion().containsCell(Cell.create(gem.getRegion().getTopRow(), gem.getRegion().getLeftColumn())));
    }


    public void testCantExtendIfOnTheLeftBorder()
    {
        insert2x2BigGem(EMERALD, 12, 0);
        grid.updateBigGems();

        assertEquals("BigGem cant extend on the left border", 1, getNumberOfExtensibleObject());
    }


    public void testCantExtendIfOnTheRightBorder()
    {
        insert2x2BigGem(EMERALD, 12, 6);
        grid.updateBigGems();

        assertEquals("BigGem cant extend on the right border", 1, getNumberOfExtensibleObject());
    }


    public void testCantExtendIfOnTheTopBorder()
    {
        insert2x2BigGem(EMERALD, 12, 4);
        insert2x2BigGem(DIAMOND, 10, 4);
        insert2x2BigGem(EMERALD, 8, 4);
        insert2x2BigGem(DIAMOND, 6, 4);
        insert2x2BigGem(EMERALD, 4, 4);
        insert2x2BigGem(DIAMOND, 2, 4);
        insert2x2BigGem(EMERALD, 0, 4);

        grid.updateBigGems();

        assertEquals("BigGem cant extend on the right border", 7, getNumberOfExtensibleObject());
    }


    public void test2x3BlockIsDetected()
    {
        insert2x2BigGem(EMERALD, 12, 4);

        insertAndUpdate(createGem(EMERALD), 11, 4);
        insertAndUpdate(createGem(EMERALD), 11, 5);

        grid.updateBigGems();

        assertEquals("2x3 Gems block forms one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void test2x4BlockIsDetected()
    {
        insert2x2BigGem(EMERALD, 12, 4);

        insertAndUpdate(createGem(EMERALD), 11, 4);
        insertAndUpdate(createGem(EMERALD), 11, 5);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 4));

        insertAndUpdate(createGem(EMERALD), 10, 4);
        insertAndUpdate(createGem(EMERALD), 10, 5);

        grid.updateBigGems();

        assertEquals(new Region(4, 10, 2, 4), bigGem.getRegion());
    }


    public void testTwo2x2VerticalBlocksAreMerged()
    {
        insert2x2BigGem(EMERALD, 12, 4);
        insert2x2BigGem(EMERALD, 10, 4);

        grid.updateBigGems();

        assertEquals("Two 2x2 blocks were not merged", 1, getNumberOfExtensibleObject());
        assertEquals(new Region(4, 10, 2, 4), grid.getDroppableAt(Cell.create(12, 4)).getRegion());
    }


    public void testTwo2x2HorizontalBlocksAreMerged()
    {
        insert2x2BigGem(EMERALD, 12, 4);
        insert2x2BigGem(EMERALD, 12, 2);

        grid.updateBigGems();

        assertEquals("Two 2x2 blocks were not merged", 1, getNumberOfExtensibleObject());
        assertEquals(new Region(2, 12, 4, 2), grid.getDroppableAt(Cell.create(12, 4)).getRegion());
    }


    public void test3x3BlockIsDetected()
    {
        insert3x3BlockOfGems();

        grid.updateBigGems();

        assertEquals("3x3 Gems block forms one BigGem", 1, getNumberOfExtensibleObject());
        assertEquals(new Region(4, 11, 3, 3), grid.getDroppableAt(Cell.create(12, 4)).getRegion());
    }


    public void test5x3BlockIsDetectedAfterInsertion()
    {
        DroppableColor gemColor = EMERALD;

        insertAndUpdate(createGem(gemColor), 13, 2);
        insertAndUpdate(createGem(gemColor), 13, 3);
        insert2x2BigGem(gemColor, 11, 2);

        insertAndUpdate(createGem(gemColor), 13, 5);
        insertAndUpdate(createGem(gemColor), 13, 6);
        insert2x2BigGem(gemColor, 11, 5);

        insertAndUpdate(createGem(gemColor), 13, 4);
        insertAndUpdate(createGem(gemColor), 12, 4);

        grid.updateBigGems();

        assertEquals("There must be two BigGem", 2, getNumberOfExtensibleObject());

        insertAndUpdate(createGem(gemColor), 11, 4);

        grid.updateBigGems();

        assertEquals("There must be one BigGem", 1, getNumberOfExtensibleObject());
        assertEquals(new Region(2, 11, 5, 3), grid.getDroppableAt(Cell.create(11, 4)).getRegion());
    }


    public void testBigGemDoesNotContainGem()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        insertAndUpdate(createGem(EMERALD), 11, 4);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));
        Droppable gem = grid.getDroppableAt(Cell.create(11, 4));

        assertFalse("Emerald at 11,4 must be not in the BigGem", bigGem.getRegion().containsCell(Cell.create(gem.getRegion().getTopRow(), gem.getRegion().getLeftColumn())));
    }


    private void insert3x3BlockOfGems()
    {
        insert2x2BigGem(EMERALD, 12, 4);
        insertAndUpdate(createGem(EMERALD), 13, 6);
        insertAndUpdate(createGem(EMERALD), 12, 6);

        insertAndUpdate(createGem(EMERALD), 11, 4);
        insertAndUpdate(createGem(EMERALD), 11, 5);
        insertAndUpdate(createGem(EMERALD), 11, 6);
    }


    public void testGridIsUpdatedWithBigGems() throws IOException
    {
        int randomSequence[] = { 99, 0, 99, 0 };

        InputReactor inputReactor = new InputReactor(Input.create(environment.getKeyboard(), environment.getTimer()), environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        GridController gridController = GridController.create(environment, inputReactor, new MockRandomGenerator(randomSequence), new Point(0, 0));

        grid = gridController.getGrid();

        gridController.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals("Grid must not have any BigGem", 0, getNumberOfExtensibleObject());

        insert2x2BigGem(EMERALD, 12, 2);

        environment.getTimer().advance(getUpdateRate() + 1);

        while (droppedGemCanMoveDown(grid))
        {
            gridController.update(environment.getTimer().getTime(), scoreCalculator);
        }

        gridController.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void testBigGemNotStealingFromOthers()
    {
        insert2x2BigGem(EMERALD, 12, 3);

        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(EMERALD), 12, 2);
        insertAndUpdate(createGem(EMERALD), 11, 2);
        insertAndUpdate(createGem(EMERALD), 11, 3);

        grid.updateBigGems();

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void test4x4BigGem()
    {
        insert2x2BigGem(EMERALD, 12, 3);
        grid.updateBigGems();

        insert2x2BigGem(EMERALD, 12, 5);
        grid.updateBigGems();

        insert2x2BigGem(EMERALD, 10, 3);
        grid.updateBigGems();

        insertAndUpdate(createGem(EMERALD), 11, 5);
        insertAndUpdate(createGem(EMERALD), 11, 6);
        insertAndUpdate(createGem(EMERALD), 10, 5);
        grid.updateBigGems();

        insertAndUpdate(createGem(EMERALD), 10, 6);
        grid.updateBigGems();

        assertEquals("Grid must have one BigGem", 1, getNumberOfExtensibleObject());
    }


    public void testNoBigGemWithChests()
    {
        insertAndUpdate(createChest(EMERALD), 13, 5);
        insertAndUpdate(createChest(EMERALD), 13, 6);
        insertAndUpdate(createChest(EMERALD), 12, 5);
        insertAndUpdate(createChest(EMERALD), 12, 6);

        try
        {
            grid.updateBigGems();
        }
        catch (RuntimeException exc)
        {
            fail("Grid is trying to make a BigGem with Chests");
        }
    }


    public void testNotThrowingConcurrentModificationException()
    {
        try
        {
            insert2x2BigGem(EMERALD, 12, 3);
            insert2x2BigGem(EMERALD, 12, 5);
            insert2x2BigGem(EMERALD, 10, 3);
            insert2x2BigGem(EMERALD, 10, 5);
            grid.updateBigGems();
        }
        catch (ConcurrentModificationException exc)
        {
            fail("ConcurrentModificationException thrown");
        }
    }


    public void testBigGemCanMoveDown()
    {
        insert2x2BigGem(EMERALD, 4, 4);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(4, 4));

        assertTrue("BigGem can move down in 4,4", bigGem.canMoveDown(grid));
    }


    public void testBigGemCantMoveDownOnBottom()
    {
        insert2x2BigGem(EMERALD, 12, 4);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 4));

        assertFalse("BigGem must be not able to move down in 12,4", bigGem.canMoveDown(grid));
    }


    public void testBigGemCantMoveDownWithObstacles()
    {
        insert2x2BigGem(EMERALD, 11, 4);
        insertAndUpdate(createGem(EMERALD), 13, 4);

        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(11, 4));

        assertFalse("BigGem must be not able to move down", bigGem.canMoveDown(grid));
    }


    public void testBigGemMoveDownABit()
    {
        insert2x2BigGem(EMERALD, 11, 3);
        Droppable topGem = grid.getDroppableAt(Cell.create(11, 3));

        grid.updateBigGems();
        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));

        bigGem.moveDown(grid);

        float top = topGem.getAnimatedSprite().getSprite().getPosition().getY();
        float bigGemTop = bigGem.getAnimatedSprite().getSprite().getPosition().getY();
        assertEquals("top must be increased", top + grid.getActualGravity(), bigGemTop, 0.001f);
    }


    public void testBigGemMoveDownNoFullGravity()
    {
        grid.setGravity(Cell.SIZE_IN_PIXELS * 3 / 4);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(13, 4);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        insert2x2BigGem(EMERALD, 10, 3);
        grid.updateBigGems();

        Droppable bigGem = grid.getDroppableAt(Cell.create(10, 3));
        bigGem.moveDown(grid);

        bigGem.moveDown(grid);
        float bigGemTop = bigGem.getAnimatedSprite().getSprite().getPosition().getY();

        assertEquals("Gem must stop at 11 row", grid.getRowUpperBound(11), bigGemTop, 0.001f);
    }


    public void testBigGemMoveDown()
    {
        insert2x2BigGem(EMERALD, 11, 3);

        grid.updateBigGems();
        Droppable bigGem = grid.getDroppableAt(Cell.create(12, 3));

        bigGem.moveDown(grid);
        bigGem.moveDown(grid);
        bigGem.moveDown(grid);
        bigGem.moveDown(grid);

        assertNotSame("Big Gem must be here", null, grid.getDroppableAt(Cell.create(13, 3)));
    }


    public void testNotMergeTwoBigGemSameWidth()
    {
        insertBigGem(EMERALD, 12, 1, 13, 4);
        grid.updateBigGems();

        insertBigGem(EMERALD, 10, 0, 11, 3);
        grid.updateBigGems();

        assertEquals("Grid must have two BigGem", 2, getNumberOfExtensibleObject());
    }


    public void testNotMergeTwoBigGemSameHeigth()
    {
        insertBigGem(EMERALD, 11, 0, 13, 1);
        grid.updateBigGems();

        insertBigGem(EMERALD, 10, 2, 12, 3);
        grid.updateBigGems();

        assertEquals("Grid must have two BigGem", 2, getNumberOfExtensibleObject());
    }


    public void testMergeThreeBigGemUp()
    {
        insertBigGem(EMERALD, 11, 3, 12, 6);

        grid.updateBigGems();

        insertBigGem(EMERALD, 8, 5, 10, 6);

        grid.updateBigGems();

        insertBigGem(EMERALD, 8, 3, 10, 4);

        grid.updateBigGems();

        assertSame("there isn't only one bigGem", grid.getDroppableAt(Cell.create(11, 3)), grid.getDroppableAt(Cell.create(8, 3)));

        assertSame("there isn't only one bigGem", grid.getDroppableAt(Cell.create(11, 3)), grid.getDroppableAt(Cell.create(8, 5)));
    }


    public void testMergeThreeBigGemRight()
    {
        insertBigGem(EMERALD, 8, 3, 12, 4);

        grid.updateBigGems();

        insertBigGem(EMERALD, 10, 5, 12, 6);

        grid.updateBigGems();

        insertBigGem(EMERALD, 8, 5, 9, 6);

        grid.updateBigGems();

        assertSame("there isn't only one bigGem", grid.getDroppableAt(Cell.create(8, 3)), grid.getDroppableAt(Cell.create(10, 5)));
        assertSame("there isn't only one bigGem", grid.getDroppableAt(Cell.create(8, 3)), grid.getDroppableAt(Cell.create(8, 5)));
    }


    public void testRemoveGemsAfterBigGemCreation()
    {
        insertBigGem(EMERALD, 12, 3, 13, 4);

        assertEquals(4, grid.getNumberOfDroppables());

        grid.updateBigGems();

        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testRemoveGemsAfterBigGemRowExtension()
    {
        insertBigGem(EMERALD, 12, 3, 13, 4);
        grid.updateBigGems();

        insertAndUpdate(createGem(EMERALD), 11, 3);
        insertAndUpdate(createGem(EMERALD), 11, 4);

        assertEquals(3, grid.getNumberOfDroppables());

        grid.updateBigGems();

        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testRemoveGemsAfterBigGemColumnExtension()
    {
        insertBigGem(EMERALD, 12, 3, 13, 4);
        grid.updateBigGems();

        insertAndUpdate(createGem(EMERALD), 13, 2);
        insertAndUpdate(createGem(EMERALD), 12, 2);

        assertEquals(3, grid.getNumberOfDroppables());

        grid.updateBigGems();

        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testSixPerSixBug()
    {
        makeAllGemsFall();
        insert2x2BigGem(EMERALD, 12, 0);
        grid.updateBigGems();
        insertAndUpdate(createGem(EMERALD), 11, 0);

        Droppable emerald = createGem(EMERALD);
        Droppable slave = createGem(DIAMOND);
        controller.getGemsPair().setPivot(emerald);
        controller.getGemsPair().setSlave(slave);

        emerald.drop();
        Cell cell = Cell.create(11, 1);

        emerald.getRegion().setRow(cell.getRow());
        emerald.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(emerald);
        Cell cell1 = Cell.create(13, 2);
        slave.getRegion().setRow(cell1.getRow());
        slave.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(slave);

        assertNotNull(grid.getDroppableAt(Cell.create(12, 0)));
        grid.updateBigGems();
        assertNotNull(grid.getDroppableAt(Cell.create(12, 0)));

    }


    public void testNotThrowingExceptionsWhenRowIsZero()
    {
        insert2x2BigGem(EMERALD, 0, 2);

        try
        {
            grid.updateBigGems();
        }
        catch (IllegalArgumentException exception)
        {
            fail();
        }
    }
}
