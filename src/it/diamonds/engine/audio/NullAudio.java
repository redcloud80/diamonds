package it.diamonds.engine.audio;


public class NullAudio implements Audio
{

    public Sound createSound(String name)
    {
        return new NullSound();
    }


    public boolean isCreated()
    {
        return true;
    }


    public boolean isMusicPlaying()
    {
        return false;
    }


    public void playMusic()
    {
    }


    public void shutDown()
    {
    }


    public void stopMusic()
    {
    }

}
