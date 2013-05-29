package it.diamonds.tests.droppable;


import it.diamonds.droppable.DroppableList;
import it.diamonds.tests.grid.iteration.MockDroppable;
import junit.framework.TestCase;


public class TestDroppableList extends TestCase
{
    private DroppableList droppableList;


    protected void setUp()
    {
        droppableList = new DroppableList();
    }


    public void testEqualsCopyConstructedEmptyList()
    {
        DroppableList newList = new DroppableList(droppableList);

        assertEquals(newList, droppableList);
    }


    public void testEqualsCopyConstructedListWithOneElement()
    {
        droppableList.add(new MockDroppable());

        DroppableList newList = new DroppableList(droppableList);

        assertEquals(newList, droppableList);
    }


    public void testEqualsCopyConstructedListWithTwoElements()
    {
        droppableList.add(new MockDroppable());
        droppableList.add(new MockDroppable());

        DroppableList newList = new DroppableList(droppableList);

        assertEquals(newList, droppableList);
    }

}
