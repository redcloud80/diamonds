package it.diamonds.tests.grid.iteration;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.droppable.types.DroppableType.GEM;
import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.gems.MorphingGem;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.iteration.TransformingIteration;
import it.diamonds.renderer.GridRenderer;
import it.diamonds.tests.mocks.MockDroppableGenerator;
import it.diamonds.tests.mocks.MockEnvironment;

import java.util.ConcurrentModificationException;


public class TestTransformingIteration extends DroppableIterationTestCase
{
    private TransformingIteration iteration;

    private Droppable stone;

    private Grid grid;

    private GridController controller;


    public void setUp()
    {
        super.setUp();
        environment = MockEnvironment.create();

        Input input = Input.create(environment.getKeyboard(), environment.getTimer());

        input.setEventMappings(createEventMappings());

        InputReactor inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        Point gridPosition = new Point(40, 40);

        grid = new Grid(environment);
        GridRenderer gridRenderer = new GridRenderer(environment, gridPosition, grid);

        controller = new GridController(environment, gridRenderer, inputReactor, new MockDroppableGenerator(environment.getEngine()));

        iteration = new TransformingIteration(controller);
        stone = createStone(DIAMOND);
    }


    public void testNoStoneTransforming()
    {
        Droppable droppable = new MockDroppable();
        insertIntoFakeGrid(droppable);
        iterateFakeGrid(iteration);
        assertFalse(iteration.areThereMorphingGems());
    }


    public void testStoneTransforming()
    {
        stone.getAnimatedSprite().setCurrentFrame(6);
        insertIntoFakeGrid(stone);
        iterateFakeGrid(iteration);
        assertTrue(iteration.areThereMorphingGems());
    }


    public void testTransformingWithOtherDroppable()
    {
        DroppableFactory gemFactory = new DroppableFactory(environment);
        Droppable gem = gemFactory.create(GEM, DIAMOND, 0);
        stone.getAnimatedSprite().setCurrentFrame(6);
        insertIntoFakeGrid(stone);
        insertIntoFakeGrid(gem);
        iterateFakeGrid(iteration);
        assertTrue(iteration.areThereMorphingGems());
    }


    public void testStonesTransform()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        grid.updateDroppableAnimations(0);
        grid.runIteration(iteration);
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertNotSame("droppable must be not same of stone", droppable, stone);
        assertNotNull("droppable must be not null", droppable);
    }


    public void testStonesTransforming()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        Cell cell = Cell.create(13, 0);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        grid.runIteration(iteration);
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertTrue(droppable instanceof MorphingGem);
    }


    public void testDroppableNoTransform()
    {
        Droppable flashingGem = createFlashingGem();
        Cell cell = Cell.create(13, 0);
        flashingGem.getRegion().setRow(cell.getRow());
        flashingGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(flashingGem);
        grid.runIteration(iteration);
        Droppable droppable = grid.getDroppableAt(Cell.create(13, 0));
        assertSame("droppable must be not same of stone", droppable, flashingGem);
    }


    public void testStonesTransformInRightPlace()
    {
        stone.getAnimatedSprite().setCurrentFrame(5);
        stone.drop();
        Cell cell = Cell.create(5, 2);
        stone.getRegion().setRow(cell.getRow());
        stone.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(stone);
        grid.runIteration(iteration);
        Droppable droppable = grid.getDroppableAt(Cell.create(5, 2));
        assertNotSame("droppable must be not same of stone", droppable, stone);
    }


    public void testDroppableLeavedInRightPlace()
    {
        Droppable flashingGem = createFlashingGem();
        Cell cell = Cell.create(5, 0);
        flashingGem.getRegion().setRow(cell.getRow());
        flashingGem.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(flashingGem);
        float oldY = flashingGem.getPositionInGridLocalSpace().getY();
        grid.updateFalls();
        grid.runIteration(iteration);
        float newY = flashingGem.getPositionInGridLocalSpace().getY();
        assertEquals(newY, oldY + getDeltaYGravity(), 0.001);
    }


    public void testNoCuncurrentModificationOnStoneTransformation()
    {
        try
        {
            Droppable anotherStone = createStone(DIAMOND);
            stone.getAnimatedSprite().setCurrentFrame(5);
            anotherStone.getAnimatedSprite().setCurrentFrame(5);
            Cell cell = Cell.create(5, 0);
            anotherStone.getRegion().setRow(cell.getRow());
            anotherStone.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(anotherStone);
            Cell cell1 = Cell.create(5, 2);
            stone.getRegion().setRow(cell1.getRow());
            stone.getRegion().setColumn(cell1.getColumn());

            grid.insertDroppable(stone);
            grid.runIteration(iteration);
        }
        catch (ConcurrentModificationException e)
        {
            fail();
        }

    }

}
