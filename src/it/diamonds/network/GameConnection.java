package it.diamonds.network;

import it.diamonds.engine.Environment;


public class GameConnection
{
    private SocketFactory socketFactory;

    private GameSocket clientkSocket;

    private GameServerSocket serverkSocket;

    private String address;

    private int port;

    private int timeout;


    public GameConnection(Environment environment, SocketFactory socketFactory)
    {
        address = environment.getConfig().getString("RemoteIp");
        port = environment.getConfig().getInteger("RemotePort");
        timeout = environment.getConfig().getInteger("ServerTimeout");

        this.socketFactory = socketFactory;
    }


    public void connect()
    {
        clientkSocket = socketFactory.createSocket(address, port);

        if (!clientkSocket.isConnected())
        {
            clientkSocket = null;

            serverkSocket = socketFactory.createServerSocket(port);
            clientkSocket = serverkSocket.accept(timeout);
            serverkSocket.close();
        }
    }


    public void close()
    {
        clientkSocket.close();
    }
    
    public void write(int data)
    {
        clientkSocket.write(data);
    }
    
    public int read()
    {
        return clientkSocket.read();
    }


    public boolean isServer()
    {
        return serverkSocket != null;
    }


    public boolean isClient()
    {
        return !isServer();
    }


    public String getAddress()
    {
        return address;
    }


    public int getPort()
    {
        return port;
    }


    public int getTimeout()
    {
        return timeout;
    }


    public boolean isConnected()
    {
        return clientkSocket != null && clientkSocket.isConnected();
    }

    public GameSocket getClient()
    {
        return clientkSocket;
    }

    public GameServerSocket getServer()
    {
        return serverkSocket;
    }
}
