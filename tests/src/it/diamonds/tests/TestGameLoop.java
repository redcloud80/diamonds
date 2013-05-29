package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.cloneRegion;
import static it.diamonds.tests.helper.ComponentHelperForTest.isSingleDroppableEquals;
import it.diamonds.GameLoop;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.engine.video.Background;
import it.diamonds.engine.video.LayerManager;
import it.diamonds.engine.video.Number;
import it.diamonds.grid.Region;
import it.diamonds.network.GameConnection;
import it.diamonds.tests.engine.input.MockInputFactory;
import it.diamonds.tests.mocks.MockKeyboard;
import it.diamonds.tests.network.MockSocketFactory;
import it.diamonds.tests.network.input.MockNetworkInputDevice;
import it.diamonds.tests.network.input.MockNetworkInputFactory;

import java.io.IOException;


public class TestGameLoop extends EnvironmentTestCase
{
    private GameLoop gameLoop;


    public void setUp()
    {
        super.setUp();
        gameLoop = new GameLoop(environment, new InputFactory(environment));
    }


    public void testIsPlayerOneInputSet()
    {
        assertNotNull(gameLoop.getPlayerOneInput());
    }


    public void testIsPlayerTwoInputSet()
    {
        assertNotNull(gameLoop.getPlayerTwoInput());
    }


    public void testPlayerFieldOneIsSet()
    {
        assertNotNull("PlayField two is null", gameLoop.getPlayFieldOne());
    }


    public void testPlayerFieldTwoIsSet()
    {
        assertNotNull("PlayField two is null", gameLoop.getPlayFieldTwo());
    }


    public void testPlayFieldsAreAttached()
    {
        assertEquals("playfield1 and opponent of playfield2 are not the same", gameLoop.getPlayFieldTwo().getOpponentPlayField(), gameLoop.getPlayFieldOne());

        assertEquals("playfield2 and opponent of playfield1 are not the same", gameLoop.getPlayFieldOne().getOpponentPlayField(), gameLoop.getPlayFieldTwo());
    }


    public void testGameLoopMustBeRunning()
    {
        assertFalse("The Gsme Loop must be running", gameLoop.isFinished());
    }


    public void testGameLoopMustBeFinished()
    {
        gameLoop.exitLoop();
        assertTrue("The Game Loop must be running", gameLoop.isFinished());
    }


    public void testPlayFielsHaveTheSameSeed()
    {
        DroppableGenerator generatorFieldOne = gameLoop.getPlayFieldOne().getGridController().getGemGenerator();
        DroppableGenerator generatorFieldTwo = gameLoop.getPlayFieldTwo().getGridController().getGemGenerator();

        assertTrue(isSingleDroppableEquals(generatorFieldOne.extract(), generatorFieldTwo.extract()));
        assertTrue(isSingleDroppableEquals(generatorFieldOne.extract(), generatorFieldTwo.extract()));
        assertTrue(isSingleDroppableEquals(generatorFieldOne.extract(), generatorFieldTwo.extract()));
        assertTrue(isSingleDroppableEquals(generatorFieldOne.extract(), generatorFieldTwo.extract()));
    }


    public void testGameLoopFinishedAfterEscapePlayerOne() throws IOException
    {
        gameLoop.getPlayerOneInput().notify(Event.create(Code.KEY_ESCAPE, State.RELEASED));

        environment.getTimer().advance(getInputRate());

        gameLoop.loopStep();

        assertTrue("The GameLoop must be stopped", gameLoop.isFinished());
    }


    public void testGameLoopFinishAfterEscapePlayerTwo() throws IOException
    {
        gameLoop.getPlayerTwoInput().notify(Event.create(Code.KEY_ESCAPE, State.RELEASED));

        environment.getTimer().advance(getInputRate());

        gameLoop.loopStep();

        assertTrue("The GameLoop must be stopped", gameLoop.isFinished());
    }


    public void testAllTexturesLoadedBeforeStartPlaying()
    {
        assertEquals(38, environment.getEngine().getPoolSize());
    }


    public void testBackgroundIsDrawnBeforeScore()
    {
        Background background = new Background(environment, GameLoop.BACKGROUND);

        LayerManager layerManager = gameLoop.getLayerManager();
        layerManager.drawLayers(environment.getEngine());

        int scoreDrawOrder = mockEngine.getImageDrawOrder(Number.NUMBER_16X24_TEXTURE_NAME);
        int backgroundDrawOrder = mockEngine.getImageDrawOrder(background.getSprite().getTexture().getName());

        assertTrue(scoreDrawOrder > backgroundDrawOrder);
    }
    
    
    public void testUpdateInputDevices()
    {
        GameLoop loop = new GameLoop(environment, new MockInputFactory(environment)); 
        
        MockKeyboard playerOneInputDevice = (MockKeyboard)loop.getPlayerOneInput().getInputDevice();
        MockKeyboard playerTwoInputDevice = (MockKeyboard)loop.getPlayerTwoInput().getInputDevice();
        
        assertFalse(playerOneInputDevice.updated());
        assertFalse(playerTwoInputDevice.updated());
        
        loop.updateInputDevices();
        
        assertTrue(playerOneInputDevice.updated());
        assertTrue(playerTwoInputDevice.updated());       
    }
    
    
    public void testSameInputDeviceIsUpdatedOnce()
    {
        GameLoop loop = new GameLoop(environment, new MockInputFactory(environment)); 
        
        MockKeyboard playerOneInputDevice = (MockKeyboard)loop.getPlayerOneInput().getInputDevice();
        MockKeyboard playerTwoInputDevice = (MockKeyboard)loop.getPlayerTwoInput().getInputDevice();
        
        loop.updateInputDevices();
        
        assertEquals(playerOneInputDevice.getUpdatesNumber(), 1);
        assertEquals(playerTwoInputDevice.getUpdatesNumber(), 1);        
    }
    
    
    public void testDifferentInputDevicesAreUpdatedOnce()
    {
        GameConnection gameConnection = new GameConnection(environment, new MockSocketFactory());
        GameLoop loop = new GameLoop(environment, new MockNetworkInputFactory(environment, gameConnection)); 
        
        MockKeyboard playerOneInputDevice = (MockKeyboard)loop.getPlayerOneInput().getInputDevice();
        MockNetworkInputDevice playerTwoInputDevice = (MockNetworkInputDevice)loop.getPlayerTwoInput().getInputDevice();
        
        loop.updateInputDevices();
        
        assertEquals(playerOneInputDevice.getUpdatesNumber(), 1);
        assertEquals(playerTwoInputDevice.getUpdatesNumber(), 1);        
    }
    
    
    public void testPlayFieldTimeStampsResetInInitBeforeGameLoop() throws IOException
    {
        gameLoop.getPlayFieldOne().reactToInput(environment.getTimer().getTime());
        gameLoop.getPlayFieldOne().reactToInput(environment.getTimer().getTime());

        assertEquals(0, gameLoop.getPlayFieldOne().wasInputServed());

        gameLoop.getPlayFieldTwo().reactToInput(environment.getTimer().getTime());
        gameLoop.getPlayFieldTwo().reactToInput(environment.getTimer().getTime());

        assertEquals(0, gameLoop.getPlayFieldTwo().wasInputServed());
    }


    public void testPlayFieldOneUpdated()
    {
        Droppable drop = gameLoop.getPlayFieldOne().getGridController().getGemsPair().getPivot();
        Region oldRectangle = cloneRegion(drop.getRegion());

        environment.getTimer().advance(getUpdateRate());
        gameLoop.loopStep();

        assertFalse(oldRectangle.equals(drop.getRegion()));
    }


    public void testPlayFieldTwoUpdated()
    {
        Droppable drop = gameLoop.getPlayFieldTwo().getGridController().getGemsPair().getPivot();
        Region oldRectangle = cloneRegion(drop.getRegion());

        environment.getTimer().advance(getUpdateRate());
        gameLoop.loopStep();

        assertFalse(oldRectangle.equals(drop.getRegion()));
    }

}
