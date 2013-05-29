package it.diamonds.tests.grid;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.RUBY;
import static it.diamonds.droppable.pair.Direction.GO_DOWN;
import static it.diamonds.droppable.pair.Direction.GO_UP;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGrid extends GridTestCase
{
    private Droppable gem1;

    private Droppable gem2;

    private int numberOfRows;


    public void setUp()
    {
        super.setUp();
        numberOfRows = grid.getNumberOfRows();

        gem1 = createGem(DroppableColor.DIAMOND);
        gem2 = createGem(DroppableColor.DIAMOND);
    }


    public void testIsColumnFull()
    {
        Cell cell = Cell.create(0, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        gem1.drop();

        assertTrue("column 4 is not full", grid.isColumnFull(4));
    }


    public void testIsColumnNotFull()
    {
        Cell cell = Cell.create(0, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        assertFalse("column 4 is full", grid.isColumnFull(4));

        grid.setGravity(Cell.SIZE_IN_PIXELS);

        grid.updateDroppable(gem1);

        assertFalse("column 4 is full", grid.isColumnFull(4));
    }


    public void testMoveWithNullGem()
    {
        try
        {
            grid.translateDroppable(null, GO_DOWN);
            fail("exception not raised");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testMoveDown()
    {
        Cell cell = Cell.create(2, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.translateDroppable(gem1, GO_DOWN);
        assertTrue("Gem didn't move down", (!grid.isCellFree(Cell.create(3, 4))));
    }


    public void testMoveDownWithCollision()
    {
        Cell cell = Cell.create(numberOfRows - 1, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.translateDroppable(gem1, GO_DOWN);
        assertTrue("Gem must remain to the bottom", (!grid.isCellFree(Cell.create(numberOfRows - 1, 4))));
    }


    public void testMoveUp()
    {
        Cell cell = Cell.create(2, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.translateDroppable(gem1, GO_UP);
        assertTrue("Gem didn't move up", (!grid.isCellFree(Cell.create(1, 4))));
    }


    public void testMoveUpWithCollision()
    {
        Cell cell = Cell.create(0, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.translateDroppable(gem1, GO_UP);
        assertTrue("Gem must remain to the top", (!grid.isCellFree(Cell.create(0, 4))));
    }


    public void testGemMoveWithNormalGravity()
    {
        grid.setNormalGravity();
        gemMoveWithGravity();
    }


    private void gemMoveWithGravity()
    {
        Cell cell = Cell.create(11, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        float oldPosY = gem1.getPositionInGridLocalSpace().getY();
        grid.updateDroppable(gem1);
        float newPosY = gem1.getPositionInGridLocalSpace().getY();

        assertEquals(oldPosY + grid.getActualGravity(), newPosY, 0.001f);
    }


    public void testGemMoveWithStrongerGravity()
    {
        grid.setStrongerGravity();
        gemMoveWithGravity();
    }


    public void testGemMoveWithStrongestGravity()
    {
        grid.setStrongestGravity();
        gemMoveWithGravity();
    }


    public void testGemVerticalMoveByYStep()
    {
        grid.setGravity(Cell.SIZE_IN_PIXELS);
        Cell cell = Cell.create(1, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.updateDroppable(gem1);
        assertTrue(grid.isCellFree(Cell.create(1, 4)));
        assertTrue((!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testGemVerticalMoveNotRow()
    {
        grid.setNormalGravity();
        Cell cell = Cell.create(11, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.updateDroppable(gem1);
        grid.updateDroppable(gem1);
        assertTrue((!grid.isCellFree(Cell.create(12, 4)))
            && !(!grid.isCellFree(Cell.create(13, 4))));
    }


    public void testBottomCollision()
    {
        grid.setGravity(Cell.SIZE_IN_PIXELS + 1);
        Cell cell1 = Cell.create(12, 4);
        gem1.getRegion().setRow(cell1.getRow());
        gem1.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem1);

        grid.updateDroppable(gem1);

        Cell cell = Cell.create(13, 4);
        assertTrue((!grid.isCellFree(cell)));

        float newYPosition = grid.getDroppableAt(cell).getPositionInGridLocalSpace().getY();
        assertEquals(grid.getRowUpperBound(13), newYPosition, 0.0001f);
    }


    public void testCanMoveWithNullGem()
    {
        assertFalse("gemCanMov should return false with null gem", grid.droppableCanMove(null, GO_DOWN));

    }


    public void testDroppedGemDoesntMoves()
    {
        insertAndUpdateGems();

        grid.removeDroppable(gem1);

        grid.updateDroppable(gem2);

        assertEquals("stopped gem must not move", 12, gem2.getRegion().getTopRow());
    }


    private void insertAndUpdateGems()
    {
        Cell cell = Cell.create(13, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        Cell cell1 = Cell.create(12, 4);
        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);

        grid.updateDroppable(gem1);
        grid.updateDroppable(gem2);
    }


    public void testDroppedGemCanMove()
    {
        insertAndUpdateGems();

        grid.removeDroppable(gem1);

        assertTrue("stopped gem must move", droppedGemCanMoveDown(grid));
    }


    public void testDroppedGemCantMove()
    {
        insertAndUpdateGems();

        assertFalse("stopped gem must not move", droppedGemCanMoveDown(grid));
    }


    public void testDroppedBigGemCanMove()
    {
        Cell cell = Cell.create(13, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        Cell cell1 = Cell.create(13, 3);
        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);

        grid.updateDroppable(gem1);
        grid.updateDroppable(gem2);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 4);
        insertAndUpdate(createGem(DIAMOND), 11, 3);
        insertAndUpdate(createGem(DIAMOND), 11, 4);

        grid.removeDroppable(gem1);
        grid.removeDroppable(gem2);

        grid.updateBigGems();

        assertTrue("stopped gem must move", droppedGemCanMoveDown(grid));
    }


    public void testDroppedBigGemCantMove()
    {
        insertAndUpdate(createGem(RUBY), 13, 3);
        Cell cell = Cell.create(13, 4);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        grid.updateDroppable(gem1);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 4);
        insertAndUpdate(createGem(DIAMOND), 11, 3);
        insertAndUpdate(createGem(DIAMOND), 11, 4);

        grid.removeDroppable(gem1);

        grid.updateBigGems();

        assertFalse("stopped gem must not move", droppedGemCanMoveDown(grid));
    }


    public void testChainCounterAfterCreation()
    {
        assertEquals("chainCounter Must be zero after the grid creation", 0, scoreCalculator.getChainCounter());
    }


    public void testBigGemCrushedIncrementChainCounter()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 2);
        insertAndUpdate(createGem(DIAMOND), 12, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertTrue("chainCounter can not be zero after a successful crush", scoreCalculator.getChainCounter() != 0);
    }


    public void testGemCrushIncrementChainCounter()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 2);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertTrue("chainCounter can not be zero after a successful crush", scoreCalculator.getChainCounter() != 0);
    }


    public void testNoGemCrush()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        insertAndUpdate(createGem(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 12, 2);

        grid.updateCrushes(scoreCalculator, null);

        assertEquals("chainCounter must be zero when no gem are crushed", 0, scoreCalculator.getChainCounter());
        assertEquals("crushedGemsCounter must be zero when no gem are crushed", 0, scoreCalculator.getChainCounter());
    }


    public void testColumnHeightIsZero()
    {
        assertEquals("Column height must be 0", 0, grid.getHeightOfColumn(0));
    }


    public void testColumnHeightIsOne()
    {
        insertAndUpdate(createGem(DIAMOND), 13, 2);
        assertEquals("Column height must be 1", 1, grid.getHeightOfColumn(2));
    }


    public void testColumnHeightWithHoles()
    {
        Droppable floatingGem = createGem(DIAMOND);
        floatingGem.drop();
        insertAndUpdate(floatingGem, 4, 2);

        assertEquals("Column height must be 10", 10, grid.getHeightOfColumn(2));
    }


    public void testColumnHeightWithFallingGems()
    {
        Droppable floatingGem = createGem(DIAMOND);
        insertAndUpdate(floatingGem, 4, 2);

        assertEquals("Column height must be 10", 0, grid.getHeightOfColumn(2));
    }
}
