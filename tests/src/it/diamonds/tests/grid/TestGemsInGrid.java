package it.diamonds.tests.grid;


import static it.diamonds.droppable.pair.Direction.GO_LEFT;
import static it.diamonds.droppable.pair.Direction.GO_RIGHT;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGemsInGrid extends GridTestCase
{
    private Droppable gem1;

    private Droppable gem2;


    public void setUp()
    {
        super.setUp();
        gem1 = createGem(DroppableColor.DIAMOND);
        gem2 = createGem(DroppableColor.DIAMOND);
    }


    public void testGemIsMovingWhenLowerCellIsEmpty()
    {
        Cell cell = Cell.create(4, 3);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        float oldPosition = gem1.getAnimatedSprite().getSprite().getPosition().getY();

        grid.updateDroppable(gem1);

        assertNotSame("Gem is not moving when cell under current position is empty", oldPosition, gem1.getAnimatedSprite().getSprite().getPosition().getY());
    }


    public void testGemIsStoppedWhenLowerCellIsFull()
    {
        Cell cell = Cell.create(5, 2);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        Cell cell1 = Cell.create(6, 2);
        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);

        float oldPosition = gem1.getAnimatedSprite().getSprite().getPosition().getY();

        grid.updateDroppable(gem1);

        assertEquals("Gem is not moving when cell under current position is empty", oldPosition, gem1.getAnimatedSprite().getSprite().getPosition().getY());
    }


    public void testNotMovingWithZeroGravity()
    {
        Cell cell = Cell.create(4, 5);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        grid.setGravity(0);

        float oldPosition = gem1.getAnimatedSprite().getSprite().getPosition().getY();

        grid.updateDroppable(gem1);

        float newPosition = gem1.getAnimatedSprite().getSprite().getPosition().getY();

        assertEquals("Gem is not moving when cell under current position is empty", oldPosition, newPosition);
    }


    public void testGemPositionAfterCollision()
    {
        Cell cell = Cell.create(4, 2);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        grid.setNormalGravity();

        float oldPosition = gem1.getPositionInGridLocalSpace().getY();

        grid.updateDroppable(gem1);

        float newPosition = gem1.getPositionInGridLocalSpace().getY();

        assertEquals("Gem is not in the correct position after update", oldPosition
            + grid.getActualGravity(), newPosition);
    }


    public void testGemCanMoveLeft()
    {
        Cell cell = Cell.create(3, 3);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        assertTrue("gem1 can move left", grid.droppableCanMove(gem1, GO_LEFT));
        Cell cell1 = Cell.create(3, 2);

        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);
        assertFalse("gem1 cannot move left", grid.droppableCanMove(gem1, GO_LEFT));
    }


    public void testGemCanMoveRight()
    {
        Cell cell = Cell.create(3, 2);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        assertTrue("gem1 can move right", grid.droppableCanMove(gem1, GO_RIGHT));
        Cell cell1 = Cell.create(3, 3);

        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);
        assertFalse("gem1 cannot move right", grid.droppableCanMove(gem1, GO_RIGHT));
    }
}
