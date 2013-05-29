package it.diamonds.tests.network;

import it.diamonds.network.GameSocket;
import it.diamonds.network.TCPSocket;

public class MockFailsSocket implements GameSocket
{
    public static final int READ_ERROR = TCPSocket.READ_ERROR;
    

    public MockFailsSocket(String address, int port)
    {
    }


    public void close()
    {
    }

    public void write(int outputData)
    { 
    }
    
    public int read()
    {
        return READ_ERROR;
    }
    
    public boolean isConnected()
    {
        return false;
    }

}
