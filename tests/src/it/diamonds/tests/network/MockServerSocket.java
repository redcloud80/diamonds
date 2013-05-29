package it.diamonds.tests.network;


import it.diamonds.network.GameServerSocket;
import it.diamonds.network.GameSocket;


public class MockServerSocket implements GameServerSocket
{
    private int localPort = 0;

    private boolean isBound = false;

    private boolean isClosed = false;

    private boolean forceTimeout = false;

    private int usedPort = 0;

    private int timeout = -1;


    public MockServerSocket(int port, boolean forceTimeout)
    {
        usedPort = port;
        localPort = port;
        this.forceTimeout = forceTimeout;
    }


    public GameSocket accept(int timeout)
    {
        this.timeout = timeout;

        if (timeout != 0 && forceTimeout)
        {
            return null;
        }

        isBound = true;

        return new MockSocket();
    }


    public void close()
    {
        localPort = 0;
        isBound = false;
        isClosed = true;
    }


    public int getLocalPort()
    {
        return localPort;
    }


    public boolean isBound()
    {
        return isBound;
    }


    public boolean isClosed()
    {
        return isClosed;
    }


    public boolean timeout()
    {
        return forceTimeout;
    }


    public int getPortUsed()
    {
        return usedPort;
    }


    public int getTimeoutUsed()
    {
        return timeout;
    }
}
