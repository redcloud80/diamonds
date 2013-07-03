package it.diamonds.tests;


import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.engine.Point;
import it.diamonds.engine.Rectangle;
import it.diamonds.engine.video.Background;
import it.diamonds.engine.video.Image;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.mocks.MockLoop;


public class TestAbstractLoop extends EnvironmentTestCase
{
    private AbstractLoop loop;

    private MockLoop mockLoop;

    private String backgroundName;


    @Override
    protected void setUp()
    {
        super.setUp();
        backgroundName = GameLoop.BACKGROUND;
        mockLoop = new MockLoop(environment, new String[] { "diamond" }, backgroundName);
        loop = mockLoop;
    }


    public void testGetNextLoop()
    {
        loop.setNextLoop(loop);
        assertSame("nextLoop() shuld return loop", loop, loop.getNextLoop());
    }


    public void testLoadTextureBackGroundOnCreate()
    {
        environment = MockEnvironment.create();
        mockEngine = (MockEngine)environment.getEngine();
        new MockLoop(environment, new String[0], backgroundName);
        assertEquals(1, environment.getEngine().getPoolSize());
        assertTrue(mockEngine.isImageCreated(backgroundName));
    }


    public void testLoadOneTextureOnCreate()
    {
        environment = MockEnvironment.create();
        mockEngine = (MockEngine)environment.getEngine();
        new MockLoop(environment, new String[] { "diamond" }, backgroundName);
        assertEquals(2, environment.getEngine().getPoolSize());
        assertTrue(mockEngine.isImageCreated(backgroundName));
        assertTrue(mockEngine.isImageCreated("diamond"));
    }


    public void testLoadTwoTextureOnCreate()
    {
        environment = MockEnvironment.create();
        mockEngine = (MockEngine)environment.getEngine();
        new MockLoop(environment, new String[] { "diamond", "ruby" }, backgroundName);
        assertEquals(3, environment.getEngine().getPoolSize());
        assertTrue(mockEngine.isImageCreated(backgroundName));
        assertTrue(mockEngine.isImageCreated("diamond"));
        assertTrue(mockEngine.isImageCreated("ruby"));
    }


    public void testInitializeGraphicsOnCreate()
    {
        assertNotNull(loop.getLayerManager());
    }


    public void testCreateEventMappingOnCreate()
    {
        assertTrue(mockLoop.isEventMappingsCreated());
    }


    public void testCreateInputOnCreateOnCreate()
    {
        assertTrue(mockLoop.isInputCreated());
    }


    public void testIsNotFinishedOnCreate()
    {
        assertFalse(loop.isFinished());
    }


    public void testIsLoopTimestampZeroOnCreate()
    {
        assertEquals(0, mockLoop.getLoopTimestamp());
    }


    public void testIsLastRenderTimeZeroOnCreate()
    {
        assertEquals(0, mockLoop.getLastRender());
    }


    public void testIsNotUpdatedOnCreate()
    {
        assertFalse(mockLoop.isStateUpdated());
    }


    public void testKeyboardIsNotUpdatedOnCreate()
    {
        assertFalse(mockKeyboard.updated());
    }


    public void testSetOneLayerOnCreate()
    {
        assertEquals(1, loop.getLayerManager().getLayersCount());
    }


    public void testLayerContainBackgroundImage()
    {
        Background background = new Background(environment, backgroundName);
        Image texture = background.getSprite().getTexture();
        loop.getLayerManager().drawLayers(environment.getEngine());
        assertTrue(mockEngine.wasImageDrawn(texture));
    }


    public void testLoopTimestampSetOnLoopStep()
    {
        long timestamp = environment.getTimer().getTime();
        loop.loopStep();
        assertEquals(timestamp, mockLoop.getLoopTimestamp());
    }


    public void testStateUpdatedOnLoopStep()
    {
        loop.loopStep();
        assertTrue(mockLoop.isStateUpdated());
    }


    public void testLastRenderOnLoopStepBeforeRefreh()
    {
        environment.getTimer().advance(getFrameRate() - 1);
        loop.loopStep();
        assertEquals(0, mockLoop.getLastRender());
    }


    public void testLastRenderOnLoopStepAfterRefreh()
    {
        environment.getTimer().advance(getFrameRate());
        long timestamp = environment.getTimer().getTime();
        loop.loopStep();
        assertEquals(timestamp, mockLoop.getLastRender());
    }


    public void testBackgroundDrawOnRenderRefresh()
    {
        Background background = new Background(environment, backgroundName);
        Image texture = background.getSprite().getTexture();

        assertFalse(mockEngine.wasImageDrawn(texture));
        assertEquals(0, mockEngine.getNumberOfQuadsDrawn());

        environment.getTimer().advance(getFrameRate());
        loop.loopStep();

        assertTrue(mockEngine.wasImageDrawn(texture));
        assertEquals(1, mockEngine.getNumberOfQuadsDrawn());
    }


    public void testEngineClearedBeforeDrawLayerOnRenderRefresh()
    {
        Image diamond = environment.getEngine().createImage("diamond");
        Image ruby = environment.getEngine().createImage("ruby");
        environment.getEngine().drawImage(new Point(0, 0), 1, 1, diamond, new Rectangle(1, 1, 1, 1));
        environment.getEngine().drawImage(new Point(10, 10), 1, 1, ruby, new Rectangle(1, 1, 1, 1));
        assertEquals(2, mockEngine.getNumberOfQuadsDrawn());
        assertTrue(mockEngine.wasImageDrawn(diamond));
        assertTrue(mockEngine.wasImageDrawn(ruby));

        environment.getTimer().advance(getFrameRate());
        loop.loopStep();

        assertEquals(1, mockEngine.getNumberOfQuadsDrawn());
        assertFalse(mockEngine.wasImageDrawn(diamond));
        assertFalse(mockEngine.wasImageDrawn(ruby));
    }


    public void testDisplayIsUpdatedOnRederRefresh()
    {
        assertFalse(mockEngine.isDisplayUpdated());

        environment.getTimer().advance(getFrameRate());
        loop.loopStep();

        assertTrue(mockEngine.isDisplayUpdated());
    }


    public void testKeyboardIsUpdatedOnLoopStep()
    {
        loop.loopStep();
        assertTrue(mockKeyboard.updated());
    }


    public void testIsFinishedOnExitloop()
    {
        loop.exitLoop();
        assertTrue(loop.isFinished());
    }
}
