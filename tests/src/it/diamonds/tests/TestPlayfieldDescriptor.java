package it.diamonds.tests;


import it.diamonds.PlayFieldDescriptor;
import it.diamonds.engine.Point;
import junit.framework.TestCase;


public class TestPlayfieldDescriptor extends TestCase
{

    private PlayFieldDescriptor playerOne;

    private PlayFieldDescriptor playerTwo;


    @Override
    protected void setUp() throws Exception
    {
        playerOne = PlayFieldDescriptor.createForPlayerOne();
        playerTwo = PlayFieldDescriptor.createForPlayerTwo();
    }


    public void test()
    {
        try
        {
            PlayFieldDescriptor.loadFromFile("aa");
        }
        catch (RuntimeException e)
        {
            return;
        }
        fail();
    }


    public void testGridOrigin()
    {
        assertEquals(new Point(20, 32), playerOne.getGridOrigin());
        assertEquals(new Point(524, 32), playerTwo.getGridOrigin());
    }


    public void testNextGemsPanelOrigin()
    {
        assertEquals(new Point(292, 32), playerOne.getNextGemsPanelOrigin());
        assertEquals(new Point(476, 32), playerTwo.getNextGemsPanelOrigin());
    }


    public void testScoreOrigin()
    {
        assertEquals(new Point(291, 421), playerOne.getScoreOrigin());
        assertEquals(new Point(419, 421), playerTwo.getScoreOrigin());
    }


    public void testGameOverOrigin()
    {
        assertEquals(new Point(20, 224), playerOne.getGameOverOrigin());
        assertEquals(new Point(524, 224), playerTwo.getGameOverOrigin());
    }


    public void testWarningBoxOrigin()
    {
        assertEquals(new Point(120, 492), playerOne.getWarningBoxOrigin());
        assertEquals(new Point(521, 492), playerTwo.getWarningBoxOrigin());
    }


    public void testCounterBoxOrigin()
    {
        assertEquals(new Point(120, 492), playerOne.getCounterBoxOrigin());
        assertEquals(new Point(521, 492), playerTwo.getCounterBoxOrigin());
    }


    public void testCrushBoxOrigin()
    {
        assertEquals(new Point(-50, 192), playerOne.getCrushBoxOrigin());
        assertEquals(new Point(594, 192), playerTwo.getCrushBoxOrigin());
    }
}
