package it.diamonds.tests.droppable.gems;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.DroppableColor.RUBY;
import it.diamonds.tests.GridTestCase;


public class TestStoneCrush extends GridTestCase
{
    public void testStoneIsCrushing()
    {
        insertAndUpdate(createStone(DIAMOND), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testStoneIsCrushingWithDifferentType()
    {
        insertAndUpdate(createStone(RUBY), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testNotAdjacentStoneIsNotCrushing()
    {
        insertAndUpdate(createStone(RUBY), 13, 2);
        insertAndUpdate(createStone(RUBY), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testStonesCrushingOnce()
    {
        insertAndUpdate(createStone(RUBY), 13, 7);
        insertAndUpdate(createStone(RUBY), 13, 3);
        insertAndUpdate(createGem(DIAMOND), 13, 4);
        insertAndUpdate(createChest(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(1, grid.getNumberOfDroppables());
    }


    public void testStoneIsNotCrushingOnChest()
    {
        insertAndUpdate(createStone(RUBY), 13, 7);
        insertAndUpdate(createChest(RUBY), 13, 6);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(2, grid.getNumberOfDroppables());
    }


    public void testStoneCrushingWithChestOfDifferentColorOnTheRightCrushing()
    {
        insertAndUpdate(createStone(RUBY), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);
        insertAndUpdate(createGem(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testStoneCrushingWithChestOfSameColorOnTheRightCrushing()
    {
        insertAndUpdate(createStone(DIAMOND), 13, 3);
        insertAndUpdate(createChest(DIAMOND), 13, 4);
        insertAndUpdate(createGem(DIAMOND), 13, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testStoneCrushingWithChestOfDifferentColorOnTheLeftCrushing()
    {
        insertAndUpdate(createStone(RUBY), 13, 5);
        insertAndUpdate(createChest(DIAMOND), 13, 4);
        insertAndUpdate(createGem(DIAMOND), 13, 3);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testStoneCrushingWithChestOfSameColorOnTheLeftCrushing()
    {
        insertAndUpdate(createStone(DIAMOND), 13, 5);
        insertAndUpdate(createChest(DIAMOND), 13, 4);
        insertAndUpdate(createGem(DIAMOND), 13, 3);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }


    public void testTwoStonesAreCrushing()
    {
        insertAndUpdate(createStone(RUBY), 13, 5);
        insertAndUpdate(createStone(RUBY), 13, 6);

        insertAndUpdate(createGem(DIAMOND), 12, 5);
        insertAndUpdate(createGem(DIAMOND), 12, 6);
        insertAndUpdate(createChest(DIAMOND), 11, 5);

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        assertEquals(0, grid.getNumberOfDroppables());
    }
}
