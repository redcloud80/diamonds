package it.diamonds.tests.mocks;


import it.diamonds.engine.Environment;


public final class MockEnvironment
{
    private MockEnvironment()
    {
        // Checkstyle!
    }


    public static Environment create(int width, int height)
    {
        return new Environment(MockEngine.create(width, height), MockAudio.create(), MockKeyboard.create(), MockTimer.create());
    }


    public static Environment create()
    {
        return create(800, 600);
    }
}
