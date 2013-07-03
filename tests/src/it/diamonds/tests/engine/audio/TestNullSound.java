package it.diamonds.tests.engine.audio;


import it.diamonds.engine.audio.NullSound;
import it.diamonds.engine.audio.Sound;
import junit.framework.TestCase;


public class TestNullSound extends TestCase
{
    private Sound sound;


    public void setUp()
    {
        sound = new NullSound();
    }


    public void testNullSoundPlayed()
    {
        assertFalse("Sound shouldn't have been played yet", sound.wasPlayed());
        sound.play();
        assertTrue("Sound should have been played", sound.wasPlayed());
    }


    public void testNullSoundPlayedReset()
    {
        sound.play();
        sound.reset();
        assertFalse("Sound should have been reset", sound.wasPlayed());
    }


    public void testNullSoundPlayedFreeMemoryReset()
    {
        sound.play();
        sound.freeMemory();
        assertFalse("Sound should have been reset by freeing memory", sound.wasPlayed());
    }


    public void testReturnNullSoundNameNull()
    {
        assertEquals("Wrong identifier returned", sound.getName(), "null");
    }
}
