package it.diamonds.tests.droppable.pair;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GemsPairTestCase;


public class TestDroppablePairPulsation extends GemsPairTestCase
{
    public void testWithPivotAndSlaveGems()
    {
        assertTrue("Pivot and slave gems aren't set-up", gemsPair.isPulsing());
    }


    public void testNoSlaveGem()
    {
        gemsPair.setNoSlave();
        assertFalse("Slave gem was set-up", gemsPair.isPulsing());
    }


    public void testNoPivotGem()
    {
        gemsPair.setNoPivot();
        assertFalse("Pivot gem was set-up", gemsPair.isPulsing());
    }


    public void testPivotIsFalling()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(2, 4);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue("Pivot gem was falling down", gemsPair.isPulsing());
    }


    public void testSlaveIsFalling()
    {
        Droppable gem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 3);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(2, 3);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertTrue("Pivot gem was falling down", gemsPair.isPulsing());
    }


    public void testPivotIsNotFalling()
    {
        insertBigGem(DroppableColor.DIAMOND, 2, 4, 13, 4);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertFalse("Pivot gem was falling down", gemsPair.isPulsing());
    }


    public void testSlaveIsNotFalling()
    {
        gemsPair.rotateClockwise();
        insertBigGem(DroppableColor.DIAMOND, 2, 5, 13, 5);
        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
        assertFalse("Pivot gem was falling down", gemsPair.isPulsing());
    }


    public void testGemsPariBlockedByBigGemBlockedBySingleGemIsNotFalling()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(13, 4);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        insertBigGem(DroppableColor.DIAMOND, 2, 4, 12, 5);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);

        assertFalse("Pivot gem was falling down", gemsPair.isPulsing());
    }


    public void testPivotGemPulsingWhenGemsPairPulsing()
    {
        assertEquals("Pivot and GemsPair aren't pulsing together", gemsPair.isPulsing(), gemsPair.getPivot().getAnimatedSprite().getSprite().isPulsing());
    }
}
