package it.diamonds.network;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServerSocket implements GameServerSocket
{
    private ServerSocket serverSocket;
    private boolean timeout;


    public TCPServerSocket(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public GameSocket accept(int timeoutInMillisecond)
    {
        TCPSocket socket = null;
        try
        {
            serverSocket.setSoTimeout(timeoutInMillisecond);
            socket = new TCPSocket(serverSocket.accept());
        }
        catch (IOException e)
        {
            this.timeout = true;
        }

        return socket;
    }


    public void close()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public boolean timeout()
    {
        return timeout;
    }

}
