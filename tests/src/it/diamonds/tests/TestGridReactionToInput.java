package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.EventHandler;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.grid.Cell;
import it.diamonds.grid.GridController;
import it.diamonds.handlers.MirrorSlaveGemCommandHandler;
import it.diamonds.handlers.RotateClockwiseCommandHandler;
import it.diamonds.handlers.RotateCounterclockwiseCommandHandler;
import it.diamonds.tests.mocks.MockDroppableGenerator;
import it.diamonds.tests.mocks.MockTimer;


public class TestGridReactionToInput extends GridTestCase
{
    static final int GRID_COLUMNS = 8;

    static final int GRID_ROWS = 14;

    private Droppable gem;

    private Input input;

    private InputReactor inputReactor;

    private DroppablePair gemsPair;


    public void setUp()
    {
        super.setUp();

        gem = createGem(DroppableColor.DIAMOND);

        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());

        inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        controller = new GridController(environment, gridRenderer, inputReactor, new MockDroppableGenerator(environment.getEngine()));

        gemsPair = controller.getGemsPair();

        grid.removeDroppable(gemsPair.getSlave());
        gemsPair.setNoSlave();
        grid.removeDroppable(gemsPair.getPivot());
        gemsPair.setNoPivot();

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
    }


    public void testMirrorSlaveHandlerRepeatDelay()
    {
        MirrorSlaveGemCommandHandler handler = new MirrorSlaveGemCommandHandler(new DroppablePair(grid, environment.getConfig()), 500, 1000);

        assertEquals(1000, handler.getFastRepeatDelay());
        assertEquals(500, handler.getNormalRepeatDelay());
    }


    public void testRotateClockwiseHandlerRepeatDelay()
    {
        RotateClockwiseCommandHandler handler = new RotateClockwiseCommandHandler(new DroppablePair(grid, environment.getConfig()), 500, 1000);

        assertEquals(1000, handler.getFastRepeatDelay());
        assertEquals(500, handler.getNormalRepeatDelay());
    }


    public void testRotateCounterclockwiseHandlerRepeatDelay()
    {
        RotateCounterclockwiseCommandHandler handler = new RotateCounterclockwiseCommandHandler(new DroppablePair(grid, environment.getConfig()), 500, 1000);

        assertEquals(1000, handler.getFastRepeatDelay());
        assertEquals(500, handler.getNormalRepeatDelay());
    }


    public void testCustomRepeatDelay()
    {
        RotateClockwiseCommandHandler handler = new RotateClockwiseCommandHandler(new DroppablePair(grid, environment.getConfig()), environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));
        handler.setFastRepeatDelay(55512);
        handler.setNormalRepeatDelay(2354);

        assertEquals(55512, handler.getFastRepeatDelay());
        assertEquals(2354, handler.getNormalRepeatDelay());
    }


    public void testCurrentRepeatDelayInitialValue()
    {
        EventHandler handler = inputReactor.getEventHandler(Code.LEFT);

        handler.setNormalRepeatDelay(1500);

        assertEquals(1500, handler.getCurrentRepeatDelay());
    }


    public void testCurrentRepeatDelayAfterFirstCommand()
    {
        EventHandler handler = inputReactor.getEventHandler(Code.LEFT);

        handler.setFastRepeatDelay(1000);
        handler.setNormalRepeatDelay(1500);

        generateKeyPressed(Code.LEFT);

        assertEquals(1500, handler.getCurrentRepeatDelay());
    }


    public void testCurrentRepeatDelayAfterSecondCommandRepeat()
    {
        EventHandler handler = inputReactor.getEventHandler(Code.LEFT);

        handler.setFastRepeatDelay(1000);
        handler.setNormalRepeatDelay(1500);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(handler.getCurrentRepeatDelay() + 1);

        inputReactor.reactToInput(environment.getTimer().getTime());

        assertEquals(1000, handler.getCurrentRepeatDelay());
    }


    public void testRotateCounterclockwise()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.BUTTON1);
        generateKeyReleased(Code.BUTTON1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't rotate counterclockwise", grid.getDroppableAt(Cell.create(2, 3)) == gemsPair.getSlave());
    }


    public void testRotateClockwise()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.BUTTON2);
        generateKeyReleased(Code.BUTTON2);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't rotate clockwise", grid.getDroppableAt(Cell.create(2, 2)) == gemsPair.getSlave());
    }


    public void testRotateMirrorSlaveGem()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.BUTTON3);
        generateKeyReleased(Code.BUTTON3);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't mirrored", grid.getDroppableAt(Cell.create(2, 2)) == gemsPair.getSlave());
    }


    public void testMoveLeft()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't move to the left", (!grid.isCellFree(Cell.create(2, 3))));
    }


    public void testMoveLeftWithRotation()
    {
        setUpRightAndLeftMovementTests();
        gemsPair.rotateCounterClockwise();

        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 3)), gemsPair.getSlave());
        assertSame("pivotGem's not right to the slave", grid.getDroppableAt(Cell.create(1, 4)), gemsPair.getPivot());

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertSame("slaveGem didn't move to the left", grid.getDroppableAt(Cell.create(1, 2)), gemsPair.getSlave());

        assertSame("pivotGem didn't move to the left", grid.getDroppableAt(Cell.create(1, 3)), gemsPair.getPivot());
    }


    public void testMoveRightWithRotation()
    {
        setUpRightAndLeftMovementTests();
        gemsPair.rotateCounterClockwise();
        gemsPair.rotateCounterClockwise();
        gemsPair.rotateCounterClockwise();

        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getSlave());

        assertSame("pivotGem's not left to the slave", grid.getDroppableAt(Cell.create(1, 4)), gemsPair.getPivot());

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertSame("slaveGem didn't move to the left", grid.getDroppableAt(Cell.create(1, 6)), gemsPair.getSlave());

        assertSame("pivotGem didn't move to the left", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getPivot());
    }


    private void setUpRightAndLeftMovementTests()
    {
        Cell pivotCell = Cell.create(0, 4);
        Cell slaveCell = Cell.create(1, 4);

        if (!grid.isCellFree(pivotCell))
        {
            grid.removeDroppable(grid.getDroppableAt(pivotCell));
        }

        if (!grid.isCellFree(slaveCell))
        {
            grid.removeDroppable(grid.getDroppableAt(slaveCell));
        }

        controller.insertNewGemsPair(grid);
        gemsPair = controller.getGemsPair();

        assertEquals("slaveGem is not on the top", grid.getDroppableAt(pivotCell), gemsPair.getSlave());

        assertEquals("pivotGem is not under the slave", grid.getDroppableAt(slaveCell), gemsPair.getPivot());
    }


    public void testMoveLeftWithCollision()
    {
        Cell cell = Cell.create(2, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem must not move to the left", (!grid.isCellFree(Cell.create(2, 0))));
    }


    public void testMoveRight()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't move to the right", (!grid.isCellFree(Cell.create(2, 5))));
    }


    public void testMoveRightWithCollision()
    {
        Cell cell = Cell.create(2, GRID_COLUMNS - 1);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem must not move to the right", (!grid.isCellFree(Cell.create(2, GRID_COLUMNS - 1))));
    }


    public void testRapidInputs()
    {
        try
        {
            Cell cell = Cell.create(0, 1);
            gem.getRegion().setRow(cell.getRow());
            gem.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(gem);
            generateKeyPressed(Code.RIGHT);
            generateKeyPressed(Code.LEFT);
            inputReactor.reactToInput(environment.getTimer().getTime());
        }
        catch (Exception e)
        {
            fail("Rapid input sequences make the program crash");
        }
    }


    public void testIsNotFalling()
    {
        Cell cell = Cell.create(GRID_ROWS - 1, 0);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        grid.updateDroppable(gem);

        assertFalse(gem.isFalling());
    }


    public void testLeftAndRightBothPressed()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left & Right key are both pressed", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testLeftAndRightSequenceInTwoPollings()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() - 1);

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left & Right key are both pressed", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testLeftStillPressedAndRightPressed()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left key is repeated & Right key is pressed", (!grid.isCellFree(Cell.create(2, 3))));

    }


    public void testRightStillPressedAndLeftPressed()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Right key is repeated & Left key is pressed", (!grid.isCellFree(Cell.create(2, 5))));
    }


    public void testLeftStillPressedAndRightPressedAfterFirstRepetition()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() / 2);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left key is repeated & Right key is pressed", (!grid.isCellFree(Cell.create(2, 2))));
    }


    public void testRightStillPressedAndLeftPressedAfterFirstRepetition()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() / 2);
        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left key is repeated & Right key is pressed", (!grid.isCellFree(Cell.create(2, 6))));
    }


    public void testLeftAndRightStillBothPressed()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        ((MockTimer)environment.getTimer()).setTime(environment.getTimer().getTime()
            + inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem may not move when Left & Right key are both pressed", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testGravityWhileDownKeyIsPressed()
    {
        Cell cell = Cell.create(0, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        int multiplied = grid.getActualGravity()
            * environment.getConfig().getInteger("GravityMultiplier");

        generateKeyPressed(Code.DOWN);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertEquals(multiplied, grid.getActualGravity());

        generateKeyReleased(Code.DOWN);
        inputReactor.reactToInput(environment.getTimer().getTime());
    }


    public void testReactionToDownKey()
    {
        Cell cell = Cell.create(2, 4);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        float oldYPosition = gem.getPositionInGridLocalSpace().getY();
        generateKeyPressed(Code.DOWN);

        inputReactor.reactToInput(environment.getTimer().getTime());
        grid.updateDroppable(gemsPair.getPivot());

        float movement = getDeltaYStrongerGravity();
        float newYPosition = gem.getPositionInGridLocalSpace().getY();
        assertEquals("gem doesn't move down as expected", oldYPosition
            + movement, newYPosition);
    }


    public void updateGridAndGenerateInput(int gravity, Code key)
    {
        gemsPair.setPivot(gem);
        grid.setGravity(gravity);
        grid.updateDroppable(gemsPair.getPivot());

        input = Input.create(environment.getKeyboard(), environment.getTimer());
        input.setEventMappings(createEventMappings());
        generateKeyPressed(key);

        try
        {
            inputReactor.reactToInput(environment.getTimer().getTime());
        }
        catch (IllegalArgumentException e)
        {
            fail("fail due to exception when erroneusly trying to move gem");
        }
    }


    public void testRightCollisionWithGem()
    {
        Cell cell = Cell.create(5, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(5, 3);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);

        updateGridAndGenerateInput(0, Code.RIGHT);

        assertEquals("gem has moved", 2, gem.getRegion().getLeftColumn());
    }


    public void testLeftCollisionWithGem()
    {
        Cell cell = Cell.create(5, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(5, 1);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);

        updateGridAndGenerateInput(0, Code.LEFT);

        assertEquals("gem has moved", 2, gem.getRegion().getLeftColumn());
    }


    public void testLeftCollisionWithGemWhileMoving()
    {
        Cell cell = Cell.create(4, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(5, 1);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);

        updateGridAndGenerateInput(1, Code.LEFT);

        assertEquals("gem has moved", 2, gem.getRegion().getLeftColumn());
    }


    public void testRightCollisionWithGemWhileMoving()
    {
        Cell cell = Cell.create(4, 2);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell1 = Cell.create(5, 3);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);

        updateGridAndGenerateInput(1, Code.RIGHT);

        assertEquals("gem has moved", 2, gem.getRegion().getLeftColumn());
    }


    public void testKeyLeftLessThanDelay()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);

        inputReactor.reactToInput(environment.getTimer().getTime());
        ((MockTimer)environment.getTimer()).setTime(environment.getTimer().getTime()
            + inputReactor.getNormalRepeatDelay() - 1);
        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue("Gem has moved more than once with Left being pressed for less than Delay", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testKeyLeftMoreThanDelay()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem isn't moving according to the correct delay with Left Key being pressed", (!grid.isCellFree(Cell.create(2, 3))));
    }


    public void testKeyRightLessThanDelay()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);

        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() - 1);
        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue("Gem has moved more than once with Left being pressed for less than Delay", (!grid.isCellFree(Cell.create(2, 6))));
    }


    public void testKeyRightMoreThanDelay()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem isn't moving according to the correct delay with Right Key being pressed", (!grid.isCellFree(Cell.create(2, 7))));
    }


    public void testKeyRightNotRepeatedAfterFirstDelay()
    {
        Cell cell = Cell.create(2, 3);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem isn't moving according to the correct delay with Right Key being pressed", (!grid.isCellFree(Cell.create(2, 5))));
    }


    public void testKeyLeftNotRepeatedAfterFirstDelay()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem isn't moving according to the correct delay with Right Key being pressed", (!grid.isCellFree(Cell.create(2, 3))));
    }


    public void testRepeatDelayUpdateOnKeyPression()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        long fastDelay = inputReactor.getEventHandler(Code.RIGHT).getFastRepeatDelay();

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertEquals("UpdateRate wasn't set correctly after a repetition", fastDelay, inputReactor.getEventHandler(Code.RIGHT).getFastRepeatDelay());
    }


    public void testRepeatDelayUpdateOnKeyRelease()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        long delayBefore = inputReactor.getNormalRepeatDelay();

        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay() + 1);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(1);

        generateKeyReleased(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertEquals("UpdateRate wasn't set back correctly after a key release", delayBefore, inputReactor.getNormalRepeatDelay());
    }


    public void testKeyLeftReleased()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        inputReactor.reactToInput(environment.getTimer().getTime());

        environment.getTimer().advance(1);
        generateKeyReleased(Code.LEFT);
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay());
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't stop moving after Left Key being released", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testKeyRightReleased()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        generateKeyPressed(Code.RIGHT);
        inputReactor.reactToInput(environment.getTimer().getTime());
        environment.getTimer().advance(1);
        generateKeyReleased(Code.RIGHT);
        environment.getTimer().advance(inputReactor.getNormalRepeatDelay());
        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Gem didn't stop moving after Right Key being released", (!grid.isCellFree(Cell.create(2, 6))));
    }


    public void testEmptyInputQueue()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        generateKeyReleased(Code.LEFT);
        generateKeyPressed(Code.RIGHT);
        generateKeyReleased(Code.RIGHT);

        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue(input.isEmpty());
    }


    public void testMultipleLeftKeyPressed()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        generateKeyReleased(Code.LEFT);
        generateKeyPressed(Code.LEFT);
        generateKeyReleased(Code.LEFT);

        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue("Gem didn't move twice with Left Key pressed twice by user.", (!grid.isCellFree(Cell.create(2, 3))));
    }


    public void testMultipleRightKeyPressed()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.RIGHT);
        generateKeyReleased(Code.RIGHT);
        generateKeyPressed(Code.RIGHT);
        generateKeyReleased(Code.RIGHT);

        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue("Gem didn't move twice with Right Key pressed twice by user.", (!grid.isCellFree(Cell.create(2, 7))));
    }


    public void testRapidSequence()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);

        generateKeyPressed(Code.LEFT);
        generateKeyReleased(Code.LEFT);
        generateKeyPressed(Code.LEFT);
        generateKeyReleased(Code.LEFT);
        generateKeyPressed(Code.RIGHT);
        generateKeyReleased(Code.RIGHT);

        inputReactor.reactToInput(environment.getTimer().getTime());

        assertTrue("Grid didn't react correctly to fast sequence.", (!grid.isCellFree(Cell.create(2, 4))));
    }


    public void testReactionWithEmptyQueue()
    {
        Cell cell = Cell.create(2, 5);
        gem.getRegion().setRow(cell.getRow());
        gem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem);
        gemsPair.setPivot(gem);
        inputReactor.reactToInput(environment.getTimer().getTime());
        assertTrue("Gem moved in response to no input", (!grid.isCellFree(Cell.create(2, 5))));
    }


    public void testEmptyInputQueuefromInputReactor()
    {
        generateKeyPressed(Code.LEFT);
        inputReactor.emptyQueue();
        assertTrue(input.isEmpty());
    }


    public void testRotateCounterclockwiseOnZ()
    {
        controller.insertNewGemsPair(grid);
        generateKeyPressed(Code.BUTTON1);

        inputReactor.reactToInput(environment.getTimer().getTime());

        assertSame("gems pair doesn't rotate", gemsPair.getSlave(), grid.getDroppableAt(Cell.create(1, 3)));
    }


    private void generateKeyPressed(Code code)
    {
        input.notify(Event.create(code, State.PRESSED));
    }


    private void generateKeyReleased(Code code)
    {
        input.notify(Event.create(code, State.RELEASED));
    }
}
