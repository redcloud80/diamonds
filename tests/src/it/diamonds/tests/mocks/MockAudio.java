package it.diamonds.tests.mocks;


import it.diamonds.engine.audio.Audio;
import it.diamonds.engine.audio.Sound;

import java.util.Hashtable;


public final class MockAudio implements Audio
{
    private Hashtable<String, Sound> sounds = new Hashtable<String, Sound>();

    private boolean created;

    private boolean isMusicPlaying;


    private MockAudio()
    {
        created = true;
        isMusicPlaying = false;
    }


    public static Audio create()
    {
        return new MockAudio();
    }


    public boolean isCreated()
    {
        return created;
    }


    public void shutDown()
    {
        created = false;
    }


    public boolean isInitialised()
    {
        return created;
    }


    public Sound createSound(String name)
    {
        Sound sound = sounds.get(name);

        if (sound == null)
        {
            sound = MockSound.create(name);
            sounds.put(name, sound);
        }

        return sound;
    }


    public void playMusic()
    {
        isMusicPlaying = true;
    }


    public boolean isMusicPlaying()
    {
        return isMusicPlaying;
    }


    public void stopMusic()
    {
        isMusicPlaying = false;
    }


    public boolean isNull()
    {
        return false;
    }

}
