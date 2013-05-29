package it.diamonds.tests.grid.iteration;

import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.iteration.ExtendIteration;

public class TestExtendIteration extends DroppableIterationTestCase
{   
    public void testIsExtending()
    {
        Grid grid = new Grid(new Environment(null, null, null, null));
        ExtendIteration iteration =  new ExtendIteration(grid);
        
        MockDroppable droppable = new MockDroppable();
        iteration.executeOn(droppable);
        
        assertSame(grid, droppable.getLastGridExtendedOn());
    }
}
