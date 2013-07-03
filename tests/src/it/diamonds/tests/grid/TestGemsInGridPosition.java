package it.diamonds.tests.grid;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.Point;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGemsInGridPosition extends GridTestCase
{
    private Droppable gem;


    public void setUp()
    {
        super.setUp();
        gem = createGem(DroppableColor.DIAMOND);
    }


    public void testGemSpritePositions()
    {
        Cell cell = Cell.create(4, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        assertSpritePosition(gem.getAnimatedSprite().getPosition(), 4, 2);
    }


    public void testAnotherGemSpritePositions()
    {
        Droppable anotherGem = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(3, 5);
        anotherGem.getRegion().setRow(cell.getRow());
        anotherGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(anotherGem);
        assertSpritePosition(anotherGem.getAnimatedSprite().getPosition(), 3, 5);
    }


    private void assertSpritePosition(Point spritePosition, int row, int column)
    {
        assertEquals(gridPosition.getX() + Cell.SIZE_IN_PIXELS * column, spritePosition.getX());
        assertEquals(gridPosition.getY() + Cell.SIZE_IN_PIXELS * row, spritePosition.getY());
    }
}
