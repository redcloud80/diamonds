package it.diamonds.tests.engine;


import it.diamonds.engine.AudioFactory;
import junit.framework.TestCase;


public class TestAudioFactory extends TestCase
{
    public void testAudioFactoryCreateAudioInterface()
    {
        AudioFactory factory = new AudioFactory();
        assertNotNull(factory.create());
    }

}
