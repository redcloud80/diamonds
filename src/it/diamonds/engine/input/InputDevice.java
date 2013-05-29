package it.diamonds.engine.input;


public interface InputDevice
{
    void update();


    void setListener(Listener listener);


    void notify(Event event);
    
    
    void removeListener(Listener listener);
}
