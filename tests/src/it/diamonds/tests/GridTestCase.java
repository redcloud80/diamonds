package it.diamonds.tests;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.Region;
import it.diamonds.renderer.GridRenderer;
import it.diamonds.tests.helper.ComponentHelperForTest;
import it.diamonds.tests.mocks.MockDroppableGenerator;

import java.util.Iterator;


public class GridTestCase extends EnvironmentTestCase
{
    // CheckStyle_Can_You_Stop_Being_So_Pedantic_For_A_Second

    protected Grid grid;

    protected GridRenderer gridRenderer;

    protected Point gridPosition;

    protected GridController controller;

    protected InputReactor inputReactor;

    protected Input input;

    protected ScoreCalculator scoreCalculator;

    protected StoneCalculator stoneCalculator;


    // CheckStyle_Ok_Now_You_Can_Go_Back_To_Work

    protected void setUp()
    {
        super.setUp();

        input = Input.create(environment.getKeyboard(), environment.getTimer());

        input.setEventMappings(createEventMappings());

        inputReactor = new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));

        gridPosition = new Point(40, 40);

        scoreCalculator = new ScoreCalculator(environment.getConfig().getInteger("BonusPercentage"));
        stoneCalculator = new StoneCalculator();

        grid = new Grid(environment);
        gridRenderer = new GridRenderer(environment, gridPosition, grid);

        controller = new GridController(environment, gridRenderer, inputReactor, new MockDroppableGenerator(environment.getEngine()));

        grid.removeDroppable(controller.getGemsPair().getPivot());
        grid.removeDroppable(controller.getGemsPair().getSlave());
    }


    public void testDummyTestForWarnings()
    {

    }


    protected void dropGemsPair()
    {
        DroppablePair gemsPair = controller.getGemsPair();
        gemsPair.drop();

        makeAllGemsFall();
    }


    protected void insertAndUpdate(Droppable gem, int row, int column)
    {
        ComponentHelperForTest.insertAndUpdate(grid, gem, row, column);
    }


    protected void insertAndDropGemsPair()
    {
        controller.insertNewGemsPair(grid);
        controller.getGemsPair().drop();
    }


    protected void makeAllGemsFall()
    {
        ComponentHelperForTest.makeAllGemsFall(grid);

        controller.update(environment.getTimer().getTime(), scoreCalculator, grid);
    }


    protected boolean droppedGemCanMoveDown(Grid grid)
    {
        return ComponentHelperForTest.droppedGemCanMoveDown(grid);
    }


    protected void insertBigGem(DroppableColor color, int startRow, int startColumn, int endRow, int endColumn)
    {
        ComponentHelperForTest.insertBigGem(environment, grid, color, startRow, startColumn, endRow, endColumn);
    }


    protected void insert2x2BigGem(DroppableColor color, int row, int column)
    {
        insertBigGem(color, row, column, row + 1, column + 1);
    }


    protected void setDiamondsGemsPair(Grid grid, DroppablePair gemsPair)
    {
        grid.removeDroppable(gemsPair.getPivot());
        grid.removeDroppable(gemsPair.getSlave());

        Droppable droppable = createGem(DIAMOND);
        Cell cell = Cell.create(0, 4);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);
        gemsPair.setSlave(droppable);

        droppable = createGem(DIAMOND);
        Cell cell1 = Cell.create(1, 4);
        droppable.getRegion().setRow(cell1.getRow());
        droppable.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(droppable);
        gemsPair.setPivot(droppable);

    }


    protected int getNumberOfExtensibleObject()
    {
        Region gridRegion = new Region(0, 0, grid.getNumberOfColumns(), grid.getNumberOfRows());
        DroppableList droppables = grid.getDroppablesInArea(gridRegion);

        Iterator<Droppable> iterator = droppables.iterator();
        while (iterator.hasNext())
        {
            Droppable droppable = iterator.next();
            if (droppable.getRegion().getWidth() == 1
                && droppable.getRegion().getHeight() == 1)
            {
                iterator.remove();
            }
        }

        return droppables.size();
    }
}
