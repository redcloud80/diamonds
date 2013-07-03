package it.diamonds.tests.engine;


import it.diamonds.engine.AudioFactory;
import it.diamonds.engine.Environment;
import it.diamonds.engine.audio.Audio;
import it.diamonds.engine.audio.NullAudio;
import it.diamonds.engine.audio.NullSound;
import it.diamonds.engine.audio.Sound;
import it.diamonds.tests.mocks.FailingAudioFactoryMock;
import it.diamonds.tests.mocks.MockAudio;
import it.diamonds.tests.mocks.MockAudioFactory;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.mocks.MockKeyboard;
import it.diamonds.tests.mocks.MockSound;
import junit.framework.TestCase;


public class TestEnvironment extends TestCase
{
    private Environment environment;


    public void setUp()
    {
        environment = MockEnvironment.create();
    }


    public void testCreateConcreteTimer()
    {
        assertNotNull(environment.createTimer());
    }


    public void testAudioCreationWithFactory()
    {
        AudioFactory audioFactory = new AudioFactory();
        assertNotNull(environment.createAudio(audioFactory));
    }


    public void testAudioNotNullCreationWithFactory()
    {
        AudioFactory audioFactory = new MockAudioFactory();
        Audio audio = environment.createAudio(audioFactory);
        assertNotNull((MockAudio)audio);
    }


    public void testFailingAudioFactoryMockException()
    {
        try
        {
            AudioFactory audioFactory = new FailingAudioFactoryMock();
            audioFactory.create();
            fail();
        }
        catch (RuntimeException e)
        {
        }
    }


    public void testShutDownEnvironment()
    {
        environment.getAudio().playMusic();

        environment.shutDown();

        assertTrue(environment.getEngine().isWindowClosed());
        assertFalse(((MockKeyboard)environment.getKeyboard()).isCreated());
        assertFalse(environment.getAudio().isCreated());
        assertFalse(environment.getAudio().isMusicPlaying());
    }


    public void testAudioCreationFailure()
    {
        AudioFactory audioFactory = new FailingAudioFactoryMock();

        Audio audio = environment.createAudio(audioFactory);
        assertNotNull((NullAudio)audio);
    }


    public void testSoundCreationFromNullAudio()
    {
        AudioFactory audioFactory = new FailingAudioFactoryMock();

        environment.createAudio(audioFactory);
        Sound sound = environment.getAudio().createSound("diamond");
        assertNotNull((NullSound)sound);
    }


    public void testSoundCreationFromNonNullAudio()
    {
        AudioFactory audioFactory = new MockAudioFactory();

        environment.createAudio(audioFactory);
        Sound sound = environment.getAudio().createSound("diamond");
        assertNotNull((MockSound)sound);
    }
}
