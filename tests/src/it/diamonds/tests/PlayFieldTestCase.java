package it.diamonds.tests;


import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.PlayFieldDescriptor;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.tests.helper.ComponentHelperForTest;


public abstract class PlayFieldTestCase extends EnvironmentTestCase
{
    // CheckStyle_Can_You_Stop_Being_So_Pedantic_For_A_Second

    protected GridController controller;

    protected Grid grid;

    protected GameLoop loop;

    protected DroppableFactory gemFactory;

    protected PlayField field;

    protected PlayFieldDescriptor fieldDescriptorOne;


    // CheckStyle_Ok_Now_You_Can_Go_Back_To_Work

    public void setUp()
    {
        super.setUp();

        loop = new GameLoop(environment, new InputFactory(environment));
        field = loop.getPlayFieldOne();
        controller = field.getGridController();
        controller.getGemsPair().drop();
        grid = controller.getGridRenderer().getGrid();
        grid.removeDroppable(grid.getDroppableAt(Cell.create(0, 4)));
        grid.removeDroppable(grid.getDroppableAt(Cell.create(1, 4)));
        gemFactory = new DroppableFactory(environment);

        fieldDescriptorOne = PlayFieldDescriptor.createForPlayerOne();
    }


    protected void makeAllGemsFall()
    {
        ComponentHelperForTest.makeAllGemsFall(grid);
    }


    protected boolean droppedGemCanMoveDown(Grid grid)
    {
        return ComponentHelperForTest.droppedGemCanMoveDown(grid);
    }


    protected void updateLoop(int amount)
    {
        environment.getTimer().advance(amount);
        loop.loopStep();
    }

}
