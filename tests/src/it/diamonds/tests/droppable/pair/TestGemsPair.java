package it.diamonds.tests.droppable.pair;


import static it.diamonds.droppable.pair.Direction.GO_LEFT;
import static it.diamonds.droppable.pair.Direction.GO_RIGHT;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GemsPairTestCase;


public class TestGemsPair extends GemsPairTestCase
{
    private Droppable gem;


    public void setUp()
    {
        super.setUp();

        gem = createGem(DroppableColor.DIAMOND);
    }


    public void testInsertGemsPair()
    {
        assertTrue((!grid.isCellFree(Cell.create(0, 4))));
        assertTrue((!grid.isCellFree(Cell.create(1, 4))));
    }


    public void testSetGemUnderControl()
    {
        gemsPair = new DroppablePair(grid, environment.getConfig());
        assertTrue("slaveGem must be null before insertNewGemsPair", gemsPair.getPivot() == null);
        Cell cell = Cell.create(0, 0);

        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        assertSame("Wrong gem under control", gem, gemsPair.getPivot());
    }


    public void testInsertionOnFullColumn()
    {
        grid.removeDroppable(gemsPair.getSlave());
        grid.removeDroppable(gemsPair.getPivot());

        if (grid.isCellFree(Cell.create(1, 4)))
        {
            Cell cell = Cell.create(1, 4);
            gem.getRegion().setRow(cell.getRow());
            gem.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(gem);
        }

        try
        {
            controller.insertNewGemsPair();
        }
        catch (IllegalArgumentException exceptionFullColumn)
        {
            fail("insertNewGemsPair insertion raised an exception");
        }

        assertNull("slaveGem should be null", gemsPair.getSlave());

        assertTrue("pivotGem should be in (x:4,y:0)", (gemsPair.getPivot().getRegion().getTopRow() == 0)
            && (gemsPair.getPivot().getRegion().getLeftColumn() == 4));

        assertFalse("single gem on top shouldn't be able to move", gemsPair.canReactToInput());
    }


    public void testSetNoGemUnderControl()
    {
        Cell cell = Cell.create(4, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        gemsPair.setNoPivot();

        assertNull("gem under control is not null", gemsPair.getPivot());
    }


    public void testGetSlaveGem()
    {
        gemsPair = new DroppablePair(grid, environment.getConfig());
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(0, 1);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        gemsPair.setPivot(grid.getDroppableAt(Cell.create(0, 1)));

        assertNull("slaveGem must be null before insertNewGemsPair", gemsPair.getSlave());
        Cell cell1 = Cell.create(0, 0);

        gem.getRegion().setRow(cell1.getRow());
        gem.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setSlave(gem);

        assertNotNull("slaveGem must be not null after insertNewGemsPair", gemsPair.getSlave());
    }


    public void testSetNoSlaveGem()
    {
        Cell cell = Cell.create(4, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setSlave(gem);
        gemsPair.setNoSlave();

        assertNull("slaveGem is not null", gemsPair.getSlave());
    }


    public void testGemsPairUnderGravity()
    {
        grid.setNormalGravity();

        environment.getTimer().advance(getUpdateRate() + 1);
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertTrue("slaveGem didn't move correctly after gravity applied", (!grid.isCellFree(Cell.create(1, 4))));
        assertTrue("pivotGem didn't move correctly after gravity applied", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testGemsPairMoveLeft()
    {
        gemsPair.move(GO_LEFT);

        assertTrue("slaveGem didn't move correctly after moveLeft", (!grid.isCellFree(Cell.create(0, 3))));
        assertTrue("pivotGem didn't move correctly after moveLeft", (!grid.isCellFree(Cell.create(1, 3))));
    }


    public void testGemsPairMoveRight()
    {
        gemsPair.move(GO_RIGHT);

        assertTrue("slaveGem didn't move correctly after moveRight", (!grid.isCellFree(Cell.create(0, 5))));
        assertTrue("pivotGem didn't move correctly after moveRight", (!grid.isCellFree(Cell.create(1, 5))));
    }


    public void testMoveRightWhileHorizontal()
    {
        controller.getGemsPair().rotateCounterclockwise();
        controller.getGemsPair().move(GO_RIGHT);

        assertTrue("slaveGem didn't move correctly after moveRight", (!grid.isCellFree(Cell.create(1, 5))));
        assertTrue("pivotGem didn't move correctly after moveRight", (!grid.isCellFree(Cell.create(1, 4))));

    }


    public void testGemsPairReactingToMoveLeftEvent()
    {
        input.notify(Event.create(Code.LEFT, State.PRESSED));

        controller.reactToInput(environment.getTimer().getTime());

        assertTrue("slaveGem didn't move correctly after MoveLeft Event", (!grid.isCellFree(Cell.create(0, 3))));
        assertTrue("pivotGem didn't move correctly after MoveLeft Event", (!grid.isCellFree(Cell.create(1, 3))));
    }


    public void testGemsPairReactingToMoveRightEvent()
    {
        input.notify(Event.create(Code.RIGHT, State.PRESSED));

        controller.reactToInput(environment.getTimer().getTime());

        assertTrue("slaveGem didn't move correctly after MoveRight Event", (!grid.isCellFree(Cell.create(0, 5))));

        assertTrue("pivotGem didn't move correctly after MoveRight Event", (!grid.isCellFree(Cell.create(1, 5))));
    }


    public void testGemsPairDoesntSplitOnCommands()
    {
        Droppable gem1 = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 3);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        Droppable gem2 = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(1, 5);
        gem2.getRegion().setRow(cell1.getRow());
        gem2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem2);

        gemsPair.move(GO_LEFT);

        assertEquals("slaveGem must not move left", 4, gemsPair.getSlave().getRegion().getLeftColumn());

        gemsPair.move(GO_RIGHT);
        assertEquals("slaveGem must not move right", 4, gemsPair.getSlave().getRegion().getLeftColumn());

        grid.removeDroppable(gem1);
        grid.removeDroppable(gem2);

        gem1 = createGem(DroppableColor.DIAMOND);
        gem2 = createGem(DroppableColor.DIAMOND);
        Cell cell2 = Cell.create(0, 3);
        gem1.getRegion().setRow(cell2.getRow());
        gem1.getRegion().setColumn(cell2.getColumn());

        grid.insertDroppable(gem1);
        Cell cell3 = Cell.create(0, 5);
        gem2.getRegion().setRow(cell3.getRow());
        gem2.getRegion().setColumn(cell3.getColumn());

        grid.insertDroppable(gem2);

        assertEquals("pivotGem must not move left", 4, gemsPair.getPivot().getRegion().getLeftColumn());

        gemsPair.move(GO_RIGHT);
        assertEquals("pivotGem must not move right", 4, gemsPair.getPivot().getRegion().getLeftColumn());
    }


    public void testGemsPairUpdate()
    {
        gemsPair.update(environment.getTimer().getTime());

        assertEquals("pivot gem doesn't move", 2, gemsPair.getPivot().getRegion().getTopRow());
        assertEquals("slave doesn't move", 1, gemsPair.getSlave().getRegion().getTopRow());
    }


    public void testUpdateWithSlaveUnder()
    {
        grid.removeDroppable(gemsPair.getPivot());
        grid.removeDroppable(gemsPair.getSlave());
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 4);

        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        Droppable droppable1 = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(2, 4);
        droppable1.getRegion().setRow(cell1.getRow());
        droppable1.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable1);

        gemsPair.setPivot(grid.getDroppableAt(Cell.create(1, 4)));
        gemsPair.setSlave(grid.getDroppableAt(Cell.create(2, 4)));

        gemsPair.update(environment.getTimer().getTime());

        assertEquals("pivot gem doesn't move", 2, gemsPair.getPivot().getRegion().getTopRow());
        assertEquals("slave doesn't move", 3, gemsPair.getSlave().getRegion().getTopRow());
    }


    public void testUpdateWithNoSlave()
    {
        gemsPair.setNoSlave();

        gemsPair.update(environment.getTimer().getTime());

        assertEquals("pivot gem doesn't move down", 2, gemsPair.getPivot().getRegion().getTopRow());
    }


    public void testUpdateWithPivotStopped()
    {
        gemsPair.rotateCounterclockwise();
        gemsPair.getPivot().drop();

        gemsPair.update(environment.getTimer().getTime());

        assertEquals("slave gem doesn't move down", 2, gemsPair.getSlave().getRegion().getTopRow());
    }


    public void testUpdateWithSlaveStopped()
    {
        gemsPair.getSlave().drop();

        gemsPair.update(environment.getTimer().getTime());

        assertEquals("pivot gem doesn't move down", 2, gemsPair.getPivot().getRegion().getTopRow());
    }


    public void testNoReactionToInputWhenPivotStopped()
    {
        gemsPair.rotateCounterclockwise();
        gemsPair.getPivot().drop();

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals("slave gem has moved", 3, gemsPair.getSlave().getRegion().getLeftColumn());
    }


    public void testNoReactionToInputWhenSlaveStopped()
    {
        gemsPair.getSlave().drop();

        input.notify(Event.create(Code.LEFT, State.PRESSED));
        controller.reactToInput(environment.getTimer().getTime());

        assertEquals("pivot gem has moved", 4, gemsPair.getPivot().getRegion().getLeftColumn());
    }


    public void testPivotFinishesFallingFast()
    {
        gemsPair.getSlave().drop();
        controller.update(environment.getTimer().getTime(), scoreCalculator);
        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }


    public void testSlaveFinishesFallingFast()
    {
        gemsPair.rotateClockwise();
        gemsPair.getPivot().drop();
        controller.update(environment.getTimer().getTime(), scoreCalculator);
        assertEquals(getDeltaYStrongestGravity(), grid.getActualGravity());
    }


    public void testOneGemIsNotFallingWithBothGemsFalling()
    {
        assertFalse(gemsPair.oneDroppableIsNotFalling());
    }


    public void testOneGemIsNotFallingWithSlaveGemNotFalling()
    {
        gemsPair.rotateClockwise();
        gemsPair.getSlave().drop();

        assertTrue(gemsPair.oneDroppableIsNotFalling());
    }


    public void testOneGemIsNotFallingWithPivotNotFalling()
    {
        gemsPair.rotateClockwise();
        gemsPair.getPivot().drop();

        assertTrue(gemsPair.oneDroppableIsNotFalling());
    }


    public void testOneGemIsNotFallingWithBothGemsNotFalling()
    {
        gemsPair.drop();

        assertFalse(gemsPair.oneDroppableIsNotFalling());
    }


    public void testBothGemsAreNotFallingWithBothGemsFalling()
    {
        assertFalse(gemsPair.bothDroppablesAreNotFalling());
    }


    public void testBothGemsAreNotFallingWithBothGemsNotFalling()
    {
        gemsPair.drop();

        assertTrue(gemsPair.bothDroppablesAreNotFalling());
    }


    public void testBothGemsAreNotFallingWithPivotGemFalling()
    {
        gemsPair.rotateClockwise();
        gemsPair.getSlave().drop();

        assertFalse(gemsPair.bothDroppablesAreNotFalling());
    }


    public void testBothGemsAreNotFallingWithSlaveGemFalling()
    {
        gemsPair.rotateClockwise();
        gemsPair.getPivot().drop();

        assertFalse(gemsPair.bothDroppablesAreNotFalling());
    }


    public void testSlaveGemFallsFaster()
    {
        final int strongestGravity = getDeltaYStrongestGravity();

        controller.getGemsPair().rotateClockwise();
        controller.getGemsPair().getPivot().drop();
        controller.update(environment.getTimer().getTime(), scoreCalculator);

        assertEquals(strongestGravity, grid.getActualGravity());

        input.notify(Event.create(Code.DOWN, State.PRESSED));

        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(strongestGravity, grid.getActualGravity(), 0.01);

        input.notify(Event.create(Code.DOWN, State.RELEASED));

        controller.reactToInput(environment.getTimer().getTime());

        assertEquals(strongestGravity, grid.getActualGravity(), 0.01);
    }


    public void testPivotIsFallingUntilBottom()
    {
        Droppable pivot = gemsPair.getPivot();
        while (gemsPair.isPulsing())
        {
            controller.update(environment.getTimer().getTime(), scoreCalculator);
        }
        assertFalse(pivot.canMoveDown(grid));
    }
}
