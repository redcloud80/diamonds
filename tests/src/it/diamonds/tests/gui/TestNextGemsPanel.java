package it.diamonds.tests.gui;


import it.diamonds.droppable.GemQueue;
import it.diamonds.engine.Point;
import it.diamonds.gui.NextGemsPanel;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockRandomGenerator;


public class TestNextGemsPanel extends GridTestCase
{
    private GemQueue gemQueue;

    private NextGemsPanel indicator;


    public void setUp()
    {
        super.setUp();

        int[] randomSequence = { 20, 60 };

        gemQueue = GemQueue.create(environment, new MockRandomGenerator(randomSequence));
        indicator = new NextGemsPanel(gemQueue, new Point(292, 32));
    }


    public void testDraw()
    {
        indicator.draw(environment.getEngine());
        assertEquals(2, ((MockEngine)environment.getEngine()).getNumberOfQuadsDrawn());
    }


    public void testDrawTwoGemPositionAndTexture()
    {
        indicator.draw(environment.getEngine());

        assertEquals(292f, ((MockEngine)environment.getEngine()).getQuadPosition(1).getX());
        assertEquals(32f, ((MockEngine)environment.getEngine()).getQuadPosition(1).getY());

        assertEquals(292f, ((MockEngine)environment.getEngine()).getQuadPosition(0).getX());
        assertEquals(64f, ((MockEngine)environment.getEngine()).getQuadPosition(0).getY());

    }

}
