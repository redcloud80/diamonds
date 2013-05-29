package it.diamonds.engine.audio;


public interface Audio
{

    boolean isCreated();


    void shutDown();


    Sound createSound(String name);


    void playMusic();


    boolean isMusicPlaying();


    void stopMusic();
}
