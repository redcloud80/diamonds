package it.diamonds.engine.audio;


import static org.lwjgl.openal.AL10.AL_NONE;
import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.Hashtable;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

import trb.sound.OggPlayer;


public final class OpenALAudio implements Audio
{
    private float[] listenerOrientation = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

    private Hashtable<String, Sound> sounds = new Hashtable<String, Sound>();

    private OggPlayer musicPlayer;

    private String musicDir = "data/audio/music/";

    private boolean isMusicPlaying = false;


    private OpenALAudio()
    {
        try
        {
            AL.create();
        }
        catch (LWJGLException e)
        {
            throw new RuntimeException("Unable to initialize Audio System: "
                + e);
        }

        AL10.alDistanceModel(AL_NONE);

        initListener();

        musicPlayer = new OggPlayer();
    }


    public static Audio create()
    {
        return new OpenALAudio();
    }


    public boolean isCreated()
    {
        return AL.isCreated();
    }


    public void shutDown()
    {
        if (AL.isCreated())
        {
            AL.destroy();
        }
    }


    private void initListener()
    {
        AL10.alListener3f(AL_POSITION, 0.0f, 0.0f, 0.0f);
        AL10.alListener3f(AL_VELOCITY, 0.0f, 0.0f, 0.0f);

        FloatBuffer floatBuff = BufferUtils.createFloatBuffer(7);

        floatBuff.put(listenerOrientation);

        AL10.alListener(AL_ORIENTATION, floatBuff);
    }


    public Sound createSound(String name)
    {
        Sound sound = sounds.get(name);

        if (sound == null)
        {
            sound = OpenALSound.create(name);
            sounds.put(name, sound);
        }

        return sound;
    }


    public void playMusic()
    {
        try
        {
            File audioFile = new File(musicDir + "theme_rock.ogg");
            musicPlayer.setLoop(true);
            musicPlayer.open(audioFile);
            musicPlayer.playInNewThread(1);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("Audio can't find the background music file.");
        }

        isMusicPlaying = true;
    }


    public boolean isMusicPlaying()
    {
        return isMusicPlaying;
    }


    public void stopMusic()
    {
        musicPlayer.stop();
        isMusicPlaying = false;
    }

}
