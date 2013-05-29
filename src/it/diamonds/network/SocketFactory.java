package it.diamonds.network;


public class SocketFactory
{
    public GameSocket createSocket(String address, int port)
    {
        return new TCPSocket(address, port);
    }

    public GameServerSocket createServerSocket(int port)
    {
        return new TCPServerSocket(port);
    }

}
