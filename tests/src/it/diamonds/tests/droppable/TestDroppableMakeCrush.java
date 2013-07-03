package it.diamonds.tests.droppable;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.CrushPriority;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockSingleDroppable;


public class TestDroppableMakeCrush extends EnvironmentTestCase
{
    private AbstractSingleDroppable abstractSingleDroppable1;

    private AbstractSingleDroppable abstractSingleDroppable2;

    private Grid grid;


    public void setUp()
    {
        super.setUp();
        grid = new Grid(environment, new Point(50, 100));

        Engine engine = MockEngine.create(0, 0);

        abstractSingleDroppable1 = MockSingleDroppable.create(engine);
        abstractSingleDroppable2 = MockSingleDroppable.create(engine);
        Cell cell = Cell.create(13, 4);

        abstractSingleDroppable1.getRegion().setRow(cell.getRow());
        abstractSingleDroppable1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(abstractSingleDroppable1);
        Cell cell1 = Cell.create(12, 4);
        abstractSingleDroppable2.getRegion().setRow(cell1.getRow());
        abstractSingleDroppable2.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(abstractSingleDroppable2);
    }


    public void testMakeCrushOfDroppableDontDeleteCrusherAtNormalPriority()
    {
        abstractSingleDroppable1.startCrush(grid, CrushPriority.NORMAL_PRIORITY, null, null);

        assertNotNull("crushGemsOn() of a generic Droppable is supposed "
            + "not to delete the droppable itself", grid.getDroppableAt(Cell.create(13, 4)));
    }


    public void testMakeCrushOfDroppableDontDeleteOtherGemsAtNormalPriority()
    {
        abstractSingleDroppable1.startCrush(grid, CrushPriority.NORMAL_PRIORITY, null, null);

        assertNotNull("crushGemsOn() of a generic Droppable is supposed "
            + "not to delete other gems", grid.getDroppableAt(Cell.create(12, 4)));
    }


    public void testMakeCrushOfDroppableDontDeleteCrusherAtHigherPriority()
    {
        abstractSingleDroppable1.startCrush(grid, CrushPriority.ABSOLUTE_PRIORITY, null, null);

        assertNotNull("crushGemsOn() of a generic Droppable is supposed "
            + "not to delete the droppable itself", grid.getDroppableAt(Cell.create(13, 4)));
    }


    public void testMakeCrushOfDroppableDontDeleteOtherGemsAtHigherPriority()
    {
        abstractSingleDroppable1.startCrush(grid, CrushPriority.ABSOLUTE_PRIORITY, null, null);

        assertNotNull("crushGemsOn() of a generic Droppable is supposed "
            + "not to delete other gems", grid.getDroppableAt(Cell.create(12, 4)));
    }

}
