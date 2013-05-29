package it.diamonds.engine;


import it.diamonds.engine.audio.Audio;
import it.diamonds.engine.audio.NullAudio;


public class AudioFactory
{
    public Audio create()
    {
        return new NullAudio();
    }
}
