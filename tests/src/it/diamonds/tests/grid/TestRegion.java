package it.diamonds.tests.grid;


import it.diamonds.droppable.pair.Direction;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Region;
import junit.framework.TestCase;


public class TestRegion extends TestCase
{
    private static final int DEF_ROW = 7;

    private static final int DEF_COLUMN = 3;

    private static final int DEF_WIDTH = 1;

    private static final int DEF_HEIGHT = 1;

    private Region region;


    public void setUp()
    {
        region = new Region(DEF_COLUMN, DEF_ROW, DEF_WIDTH, DEF_HEIGHT);
    }


    public void testSetColumn()
    {
        assertSetColumn(1);
        assertSetColumn(2);
    }


    private void assertSetColumn(int currentColumn)
    {
        region.setColumn(currentColumn);

        assertEquals(region, new Region(currentColumn, DEF_ROW, DEF_WIDTH, DEF_HEIGHT));
    }


    public void testSetRow()
    {
        assertSetRow(1);
        assertSetRow(2);
    }


    private void assertSetRow(int currentRow)
    {
        region.setRow(currentRow);

        assertEquals(region, new Region(DEF_COLUMN, currentRow, DEF_WIDTH, DEF_HEIGHT));
    }


    public void testWidth()
    {
        assertEquals(DEF_WIDTH, region.getWidth());
    }


    public void testHeight()
    {
        assertEquals(DEF_HEIGHT, region.getHeight());
    }


    public void testLeftColumn()
    {
        assertEquals(DEF_COLUMN, region.getLeftColumn());
    }


    public void testTopRowIsOne()
    {
        assertEquals(DEF_ROW, region.getTopRow());
    }


    public void testWidthPlusOne()
    {
        region = new Region(DEF_COLUMN, DEF_ROW, DEF_WIDTH + 1, DEF_HEIGHT);

        assertEquals(DEF_WIDTH + 1, region.getWidth());
    }


    public void testHeightPlusOne()
    {
        region = new Region(DEF_COLUMN, DEF_ROW, DEF_WIDTH, DEF_HEIGHT + 1);

        assertEquals(DEF_HEIGHT + 1, region.getHeight());
    }


    public void testLeftColumnPlusOne()
    {
        region = new Region(DEF_COLUMN + 1, DEF_ROW, DEF_WIDTH, DEF_HEIGHT);

        assertEquals(DEF_COLUMN + 1, region.getLeftColumn());
    }


    public void testTopRowPlusOne()
    {
        region = new Region(DEF_COLUMN, DEF_ROW + 1, DEF_WIDTH, DEF_HEIGHT);

        assertEquals(DEF_ROW + 1, region.getTopRow());
    }


    public void testNegativeLeftNotAllowed()
    {
        illegalParameterTest(-1, 0, 1, 1);
    }


    public void testNegativeTopNotAllowed()
    {
        illegalParameterTest(0, -1, 1, 1);
    }


    public void testNegativeWidthNotAllowed()
    {
        illegalParameterTest(0, 0, -1, 1);
    }


    public void testNegativeHeightNotAllowed()
    {
        illegalParameterTest(0, 0, 1, -1);
    }


    public void testZeroWidthNotAllowed()
    {
        illegalParameterTest(0, 0, 0, 1);
    }


    public void testZeroHeightNotAllowed()
    {
        illegalParameterTest(0, 0, 1, 0);
    }


    private void illegalParameterTest(int left, int top, int width, int height)
    {
        try
        {
            region = new Region(left, top, width, height);
            fail("Didn't throw an IllegalArgumentException");
        }
        catch (IllegalArgumentException exception)
        {
            ;
        }
    }


    public void testNegativeSetColumnNotAllowed()
    {
        try
        {
            region.setColumn(-1);
            fail("Didn't throw an IllegalArgumentException");
        }
        catch (IllegalArgumentException exception)
        {
            ;
        }
    }


    public void testNegativeSetRowNotAllowed()
    {
        try
        {
            region.setRow(-1);
            fail("Didn't throw an IllegalArgumentException");
        }
        catch (IllegalArgumentException exception)
        {
            ;
        }
    }


    public void testEquals()
    {
        Region firstCell = new Region(0, 0, 1, 1);

        assertEquals(firstCell, new Region(0, 0, 1, 1));

        firstCell = new Region(1, 5, 7, 6);

        assertEquals(firstCell, new Region(1, 5, 7, 6));
    }


    public void testNotEquals()
    {
        Region firstCell = new Region(1, 1, 1, 1);

        assertFalse(firstCell.equals(new Region(0, 1, 1, 1)));

        assertFalse(firstCell.equals(new Region(1, 0, 1, 1)));

        assertFalse(firstCell.equals(new Region(1, 1, 2, 1)));

        assertFalse(firstCell.equals(new Region(1, 1, 1, 2)));
    }


    public void testExtendOnAllSides2()
    {
        for (int left = 0; left < 3; left++)
        {
            for (int top = 0; top < 3; top++)
            {
                for (int right = 0; right < 3; right++)
                {
                    for (int bottom = 0; bottom < 3; bottom++)
                    {
                        testExtendOnAllSide(left, top, right, bottom);
                    }
                }
            }
        }
    }


    private void testExtendOnAllSide(int leftExtension, int topExtension, int rightExtension, int bottomExtension)
    {
        region = new Region(DEF_COLUMN, DEF_ROW, DEF_WIDTH, DEF_HEIGHT);

        extendToTop(region, topExtension);
        extendToBottom(region, bottomExtension);
        extendToLeft(region, leftExtension);
        extendToRight(region, rightExtension);

        assertEquals(DEF_HEIGHT + topExtension + bottomExtension, region.getHeight());
        assertEquals(DEF_WIDTH + leftExtension + rightExtension, region.getWidth());

        Region expectedResult = new Region(DEF_COLUMN - leftExtension, DEF_ROW
            - topExtension, DEF_WIDTH + leftExtension + rightExtension, DEF_HEIGHT
            + topExtension + bottomExtension);

        assertEquals(expectedResult, region);
    }


    public void testNotContainsWithDifferentRowAndColumn()
    {
        assertFalse(region.containsCell(Cell.create(DEF_ROW + DEF_HEIGHT, DEF_COLUMN
            + DEF_WIDTH)));
    }


    public void testContains()
    {
        assertTrue(region.containsCell(Cell.create(DEF_ROW, DEF_COLUMN)));
    }


    public void testNotContainsWithDifferentColumn()
    {
        assertFalse(region.containsCell(Cell.create(DEF_ROW, DEF_COLUMN
            + DEF_WIDTH)));
    }


    public void testNotContainsWithDifferentRow()
    {
        assertFalse(region.containsCell(Cell.create(DEF_ROW + DEF_HEIGHT, DEF_COLUMN)));
    }


    public void testContainsExtendingOneColumnToTheRight()
    {
        int columnToTest = DEF_COLUMN + DEF_WIDTH;

        assertFalse(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
        extendToRight(region, 1);
        assertTrue(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
    }


    public void testContainsExtendingTwoColumnsToTheRight()
    {
        int columnToTest = DEF_COLUMN + DEF_WIDTH + 1;

        assertFalse(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
        extendToRight(region, 2);
        assertTrue(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
    }


    public void testContainsExtendingOneColumnToTheLeft()
    {
        int columnToTest = DEF_COLUMN - 1;

        assertFalse(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
        extendToLeft(region, 1);
        assertTrue(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
    }


    public void testContainsExtendingTwoColumnsToTheLeft()
    {
        int columnToTest = DEF_COLUMN - 2;

        assertFalse(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
        extendToLeft(region, 2);
        assertTrue(region.containsCell(Cell.create(DEF_ROW, columnToTest)));
    }


    public void testContainsExtendingOneRowToTheTop()
    {
        int rowToTest = DEF_ROW - 1;

        assertFalse(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
        extendToTop(region, 1);
        assertTrue(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
    }


    public void testContainsExtendingTwoRowsToTheTop()
    {
        int rowToTest = DEF_ROW - 2;

        assertFalse(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
        extendToTop(region, 2);
        assertTrue(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
    }


    public void testContainsExtendingOneRowToTheBottom()
    {
        int rowToTest = DEF_ROW + DEF_HEIGHT;

        assertFalse(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
        extendToBottom(region, 1);
        assertTrue(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
    }


    public void testContainsExtendingTwoRowsToTheBottom()
    {
        int rowToTest = DEF_ROW + DEF_HEIGHT + 1;

        assertFalse(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
        extendToBottom(region, 2);
        assertTrue(region.containsCell(Cell.create(rowToTest, DEF_COLUMN)));
    }


    private void extendToRight(Region region, int amount)
    {
        assertTrue(amount >= 0);

        region.resizeToContain(new Region(region.getRightColumn() + amount, region.getTopRow(), 1, 1));
    }


    private void extendToLeft(Region region, int amount)
    {
        assertTrue(amount >= 0);

        region.resizeToContain(new Region(region.getLeftColumn() - amount, region.getTopRow(), 1, 1));
    }


    private void extendToBottom(Region region, int amount)
    {
        assertTrue(amount >= 0);

        region.resizeToContain(new Region(region.getLeftColumn(), region.getBottomRow()
            + amount, 1, 1));
    }


    private void extendToTop(Region region, int amount)
    {
        assertTrue(amount >= 0);

        region.resizeToContain(new Region(region.getLeftColumn(), region.getTopRow()
            - amount, 1, 1));
    }


    public void testNeighbourRegionUp()
    {
        Region biggerRegion = new Region(DEF_COLUMN, DEF_ROW, 2, 4);
        Region regionUp = biggerRegion.getAdjacentRegionByDirection(Direction.GO_UP);

        assertSame(DEF_COLUMN, regionUp.getLeftColumn());
        assertSame(DEF_ROW - 1, regionUp.getTopRow());
        assertSame(2, regionUp.getWidth());
        assertSame(1, regionUp.getHeight());
    }


    public void testNeighbourRegionDown()
    {
        Region biggerRegion = new Region(DEF_COLUMN, DEF_ROW, 2, 4);
        Region regionDown = biggerRegion.getAdjacentRegionByDirection(Direction.GO_DOWN);

        assertSame(DEF_COLUMN, regionDown.getLeftColumn());
        assertSame(DEF_ROW + 4, regionDown.getTopRow());
        assertSame(2, regionDown.getWidth());
        assertSame(1, regionDown.getHeight());
    }


    public void testNeighbourRegionLeft()
    {
        Region biggerRegion = new Region(DEF_COLUMN, DEF_ROW, 2, 4);
        Region regionLeft = biggerRegion.getAdjacentRegionByDirection(Direction.GO_LEFT);

        assertSame(DEF_COLUMN - 1, regionLeft.getLeftColumn());
        assertSame(DEF_ROW, regionLeft.getTopRow());
        assertSame(1, regionLeft.getWidth());
        assertSame(4, regionLeft.getHeight());
    }


    public void testNeighbourRegionRight()
    {
        Region biggerRegion = new Region(DEF_COLUMN, DEF_ROW, 2, 4);
        Region regionRight = biggerRegion.getAdjacentRegionByDirection(Direction.GO_RIGHT);

        assertSame(DEF_COLUMN + 2, regionRight.getLeftColumn());
        assertSame(DEF_ROW, regionRight.getTopRow());
        assertSame(1, regionRight.getWidth());
        assertSame(4, regionRight.getHeight());
    }


    public void testNeighbourRegionTooUp()
    {
        Region biggerRegion = new Region(0, 0, 2, 4);
        Region regionUp = biggerRegion.getAdjacentRegionByDirection(Direction.GO_UP);

        assertNull(regionUp);
    }


    public void testNeighbourRegionTooLeft()
    {
        Region biggerRegion = new Region(0, 0, 2, 4);
        Region regionLeft = biggerRegion.getAdjacentRegionByDirection(Direction.GO_LEFT);

        assertNull(regionLeft);
    }
}
