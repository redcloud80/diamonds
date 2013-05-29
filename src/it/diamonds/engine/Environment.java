package it.diamonds.engine;


import it.diamonds.engine.audio.Audio;
import it.diamonds.engine.audio.NullAudio;
import it.diamonds.engine.audio.OpenALAudio;
import it.diamonds.engine.input.Keyboard;
import it.diamonds.engine.input.LWJGLKeyboard;
import it.diamonds.engine.video.LWJGLEngine;


public final class Environment
{
    private Config config;

    private Engine engine;

    private Audio audio;

    private Keyboard keyboard;

    private Timer timer;


    public Environment(Engine engine, Audio audio, Keyboard keyboard, Timer timer)
    {
        createConfig();
        this.engine = engine;
        this.audio = audio;
        this.keyboard = keyboard;
        this.timer = timer;
    }


    public Environment()
    {
        createConfig();
        createAudio();
        createEngine(config.getInteger("width"), config.getInteger("height"), "Diamond Crush");
        createKeyboard();
        createTimer();
    }


    private void createConfig()
    {
        config = Config.create();
    }


    public Config getConfig()
    {
        return config;
    }


    public Engine createEngine(int width, int height, String title)
    {
        int fullScreenOnConfig = config.getInteger("FullScreen");
        boolean fullscreen = fullScreenOnConfig != 0;
        engine = new LWJGLEngine(width, height, title, fullscreen);
        return engine;
    }


    public Engine getEngine()
    {
        return engine;
    }


    public Audio createAudio()
    {
        AudioFactory factory = new AudioFactory()
        {
            public Audio create()
            {
                return OpenALAudio.create();
            }
        };

        return createAudio(factory);
    }


    public Audio createAudio(AudioFactory factory)
    {
        try
        {
            audio = factory.create();
        }
        catch (RuntimeException e)
        {
            audio = new NullAudio();
        }

        return audio;
    }


    public Audio getAudio()
    {
        return audio;
    }


    public Keyboard createKeyboard()
    {
        keyboard = LWJGLKeyboard.create();
        return keyboard;
    }


    public Keyboard getKeyboard()
    {
        return keyboard;
    }


    public Timer createTimer()
    {
        timer = ConcreteTimer.create();
        return timer;
    }


    public Timer getTimer()
    {
        return timer;
    }


    public static Environment create()
    {
        return new Environment(null, null, null, null);
    }


    public void shutDown()
    {
        getKeyboard().shutDown();
        getAudio().stopMusic();
        getAudio().shutDown();
        getEngine().shutDown();
    }
}
