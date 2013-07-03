package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.types.DroppableType.STONE;
import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.grid.Cell;
import it.diamonds.grid.state.StoneTurnState;
import it.diamonds.tests.PlayFieldTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestStoneTurnState extends PlayFieldTestCase
{
    private StoneTurnState state;

    private Droppable stone;

    private int animationUpdateRate;


    public void setUp()
    {
        super.setUp();

        state = new StoneTurnState(environment);
        stone = gemFactory.create(STONE, DIAMOND, 0);
        animationUpdateRate = environment.getConfig().getInteger("GemAnimationUpdateRate");
    }


    public void testStateName()
    {
        assertTrue(state.isCurrentState("StoneTurn"));
    }


    public void testWrongStateName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testStoneTurn()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        long time = environment.getTimer().getTime();
        state.update(time, controller, new ScoreCalculator(0), null);
        grid.updateDroppableAnimations(time);
        gridControllerMockUpdate();
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertEquals(6, droppable.getAnimatedSprite().getCurrentFrame());
    }


    public void testStoneBecomesAGem()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        long time = environment.getTimer().getTime();
        state.update(time, controller, new ScoreCalculator(0), null);
        grid.updateDroppableAnimations(time);
        gridControllerMockUpdate();
        gridControllerMockUpdate();
        gridControllerMockUpdate();
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertTrue(droppable instanceof Gem);
    }


    private void gridControllerMockUpdate()
    {
        long time;
        environment.getTimer().advance(animationUpdateRate);
        time = environment.getTimer().getTime();
        state.update(time, controller, new ScoreCalculator(0), new StoneCalculator());
        grid.updateDroppableAnimations(time);
    }


    public void testStoneIsTransformedAfterStoneTurnState()
    {
        stone.getAnimatedSprite().setCurrentFrame(7);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        long time = environment.getTimer().getTime();
        state.update(time, controller, new ScoreCalculator(0), null);
        grid.updateDroppableAnimations(time);
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertNotSame("The Stone must be a Gem", droppable, stone);
    }


    public void testStateChangeWithEmptyGrid()
    {
        assertFalse(state.update(0, controller, new ScoreCalculator(0), new StoneCalculator()).isCurrentState("StoneTurn"));
    }


    public void testStateChangeWithNoTurningStones()
    {
        Droppable morphingGem = gemFactory.createMorphingGem(DIAMOND);
        morphingGem.getAnimatedSprite().setCurrentFrame(7);
        Cell cell = Cell.create(13, 0);
        morphingGem.getRegion().setRow(cell.getRow());
        morphingGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(morphingGem);
        long time = environment.getTimer().getTime();
        state.update(time, controller, new ScoreCalculator(0), new StoneCalculator());
        grid.updateDroppableAnimations(time);
        assertFalse(state.update(0, controller, new ScoreCalculator(0), new StoneCalculator()).isCurrentState("StoneTurn"));
    }


    public void testStateNotChangedWithMorphingGem()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        state.update(0, controller, new ScoreCalculator(0), null);
        environment.getTimer().advance(animationUpdateRate);
        assertTrue(state.update(0, controller, null, null).isCurrentState("StoneTurn"));
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
