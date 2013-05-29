package it.diamonds.network;

public interface GameSocket
{
    void close();
    
    boolean isConnected();
    
    void write(int outputData);
    
    int read();
}
