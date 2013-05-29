package it.diamonds.tests;


import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.droppable.gems.FlashingGem;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.gems.Stone;
import it.diamonds.engine.Environment;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.mocks.MockKeyboard;
import junit.framework.TestCase;


public class EnvironmentTestCase extends TestCase
{
    // CheckStyle_Can_You_Stop_Being_So_Pedantic_For_A_Second

    protected Environment environment;

    protected MockEngine mockEngine;

    protected MockKeyboard mockKeyboard;


    protected void setUp()
    {
        environment = MockEnvironment.create();
        mockEngine = (MockEngine)environment.getEngine();
        mockKeyboard = (MockKeyboard)environment.getKeyboard();
    }


    public void testDummyTestForWarnings()
    {

    }


    protected FlashingGem createFlashingGem()
    {
        return new FlashingGem(environment.getEngine(), 1, 0);
    }


    protected Stone createStone(DroppableColor color)
    {
        return new Stone(environment, color, 0);
    }


    protected Chest createChest(DroppableColor color)
    {
        return new Chest(environment.getEngine(), color, 3500, 0);
    }


    protected Gem createGem(DroppableColor color)
    {
        return new Gem(environment.getEngine(), color, 3500, 100);
    }


    protected Gem createGem(DroppableColor color, int delay, int animationUpdateRate)
    {
        return new Gem(environment.getEngine(), color, delay, animationUpdateRate);
    }


    protected Gem createGem(DroppableColor color, int delay)
    {
        return new Gem(environment.getEngine(), color, delay, 0);
    }


    // CheckStyle_Ok_Now_You_Can_Go_Back_To_Work

    public int getDeltaYStrongestGravity()
    {
        return environment.getConfig().getInteger("StrongestGravityMultiplier")
            * environment.getConfig().getInteger("NormalGravity");
    }


    public int getDeltaYStrongerGravity()
    {
        return environment.getConfig().getInteger("GravityMultiplier")
            * environment.getConfig().getInteger("NormalGravity");
    }


    public int getDeltaYGravity()
    {
        return environment.getConfig().getInteger("NormalGravity");
    }


    public int getFrameRate()
    {
        return environment.getConfig().getInteger("FrameRate");
    }


    public int getInputRate()
    {
        return environment.getConfig().getInteger("InputRate");
    }


    public int getUpdateRate()
    {
        return environment.getConfig().getInteger("UpdateRate");
    }


    public int getNewGemDelay()
    {
        return environment.getConfig().getInteger("NewGemDelay");
    }


    public int getCrushBoxSpeed()
    {
        return environment.getConfig().getInteger("CrushBoxSpeed");
    }


    public int getScreenWidth()
    {
        return environment.getConfig().getInteger("width");
    }


    public int getDelayBetweenCrushes()
    {
        return environment.getConfig().getInteger("DelayBetweenCrushes");
    }


    public int getGemAnimationDelay()
    {
        return environment.getConfig().getInteger("GemAnimationDelay");
    }


    public int getGemAnimationUpdateRate()
    {
        return environment.getConfig().getInteger("GemAnimationUpdateRate");
    }
    
    
    public String getRemoteIp()
    {
        return environment.getConfig().getString("RemoteIp"); 
    }
    
    
    public int getRemotePort()
    {
        return environment.getConfig().getInteger("RemotePort");
    }
}
