package it.diamonds.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSocket implements GameSocket
{
    public static final int READ_ERROR = -1;
    
    private Socket socket;

    public TCPSocket(String address, int port)
    {
        try
        {
            socket = new Socket(InetAddress.getByName(address), port);
            socket.setTcpNoDelay(true);
        }
        catch (UnknownHostException e)
        {
         // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            socket = new Socket();
        }
    }


    public TCPSocket()
    {
        socket = new Socket();
    }


    public TCPSocket(Socket socket)
    {
        this.socket = socket;
    }



    public void close()
    {
        if (socket == null)
        {
            return;
        }
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void write(int outputData)
    {
        try
        {
            socket.getOutputStream().write(outputData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public int read()
    {
        try
        {
            if(socket.getInputStream().available() > 0)
            {
                return socket.getInputStream().read();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return READ_ERROR;
    }
    
    
    public boolean getTcpNoDelay()
    {
        try
        {
            return socket.getTcpNoDelay();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public boolean isConnected()
    {
        return socket.isConnected();
    }
}
