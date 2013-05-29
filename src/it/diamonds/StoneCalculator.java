package it.diamonds;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.types.DroppableType;


public class StoneCalculator
{
    private int counter;


    public int getCounter()
    {
        return counter;
    }


    public void addDroppable(Droppable droppable)
    {
        if (droppable.getType() == DroppableType.CHEST
            || droppable.getType() == DroppableType.FLASHING_GEM)
        {
            return;
        }

        if (droppable.getArea() == 1)
        {
            counter++;
        }
        else
        {
            counter += droppable.getArea() * 2;
        }
    }


    public void reset()
    {
        counter = 0;
    }

}
