package it.diamonds.network;


public interface GameServerSocket
{
    GameSocket accept(int timeout);

    void close();

    boolean timeout();
}
