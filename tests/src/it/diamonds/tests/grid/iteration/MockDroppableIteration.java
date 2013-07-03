package it.diamonds.tests.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableList;
import it.diamonds.grid.iteration.DroppableIteration;


public class MockDroppableIteration implements DroppableIteration
{
    private DroppableList iteratedDroppables = new DroppableList();


    public void executeOn(Droppable droppable)
    {
        iteratedDroppables.add(droppable);
    }


    public int getIterationsCount()
    {
        return iteratedDroppables.size();
    }


    public boolean hasBeenIterated(Droppable droppable)
    {
        return iteratedDroppables.contains(droppable);
    }

}
