package it.diamonds.tests.mocks;


import it.diamonds.engine.AudioFactory;
import it.diamonds.engine.audio.Audio;


public class MockAudioFactory extends AudioFactory
{
    public Audio create()
    {
        return MockAudio.create();
    }
}
