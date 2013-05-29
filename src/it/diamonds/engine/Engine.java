package it.diamonds.engine;


import it.diamonds.engine.video.Image;


public interface Engine
{

    int getDisplayWidth();


    int getDisplayHeight();


    void shutDown();


    boolean isWindowClosed();


    void updateDisplay();


    void clearDisplay();


    int getPoolSize();


    Image createImage(String name);


    void drawImage(Point position, float width, float height, Image image, Rectangle imageRect);

}
