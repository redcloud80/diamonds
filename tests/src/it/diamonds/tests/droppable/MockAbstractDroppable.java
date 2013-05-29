package it.diamonds.tests.droppable;


import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.types.DroppableType;


public class MockAbstractDroppable extends AbstractDroppable
{
    public MockAbstractDroppable()
    {
        super(new DroppableDescription(DroppableType.GEM, DroppableColor.DIAMOND));
    }


    @Override
    public int getScore()
    {
        // TODO Auto-generated method stub
        return 0;
    }


    public void drop()
    {
        // TODO Auto-generated method stub

    }


    public boolean isFalling()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
