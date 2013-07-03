package it.diamonds.tests.engine.audio;


import it.diamonds.engine.audio.Audio;
import it.diamonds.engine.audio.NullAudio;
import it.diamonds.engine.audio.NullSound;
import it.diamonds.engine.audio.Sound;
import junit.framework.TestCase;


public class TestNullAudio extends TestCase
{

    public void testNullAudioIsCreated()
    {
        Audio audio = new NullAudio();
        assertTrue(audio.isCreated());
        assertFalse(audio.isMusicPlaying());
        audio.playMusic();
        audio.stopMusic();
        audio.shutDown();
    }


    public void testNullAudioCreatesNullSound()
    {
        Audio audio = new NullAudio();
        Sound sound = audio.createSound("diamonds");

        assertNotNull((NullSound)sound);
    }
}
