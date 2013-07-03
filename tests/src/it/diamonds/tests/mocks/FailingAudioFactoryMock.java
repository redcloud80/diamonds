package it.diamonds.tests.mocks;


import it.diamonds.engine.AudioFactory;
import it.diamonds.engine.audio.Audio;


public class FailingAudioFactoryMock extends AudioFactory
{
    public Audio create()
    {
        throw new RuntimeException();
    }
}
