package it.diamonds.tests.network;


import it.diamonds.network.GameSocket;
import it.diamonds.network.TCPSocket;

public class MockSocket implements GameSocket
{
    public static final int READ_ERROR = TCPSocket.READ_ERROR;
    
    private boolean isConnected;

    private String address;

    private int port;

    private int outData;

    private int inData;

    private boolean inputBufferEmpty;
    
    private boolean outputBufferEmpty;

    private int blockingTime;


    public MockSocket()
    {
        port = 0;
        address = "";
        isConnected = true;
        inputBufferEmpty = true;
        outputBufferEmpty = true;
        blockingTime = 0;
    }


    public MockSocket(String address, int port)
    {
        this();
        this.address = address;
        this.port = port;
    }


    public void close()
    {
        isConnected = false;
    }


    public boolean isConnected()
    {
        return isConnected;
    }


    public String getAddressUsed()
    {
        return address;
    }


    public int getPortUsed()
    {
        return port;
    }


    public void write(int outputData)
    {
        outputBufferEmpty = false;
        outData = outputData;
    }


    public int getLastOutputData()
    {
        outputBufferEmpty = true;
        return outData;
    }


    public int read()
    {
        if (inputBufferEmpty)
        {
            long currentTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - currentTime) < blockingTime)
            {
                ;
            }
            
            return READ_ERROR;
        }

        inputBufferEmpty = true;
        return inData;
    }


    public void setLastInputData(int inputData)
    {
        inputBufferEmpty = false;
        inData = inputData;
    }


    public boolean isInputBufferEmpty()
    {
        return inputBufferEmpty;
    }
    
    
    public boolean isOutputBufferEmpty()
    {
        return outputBufferEmpty;
    }


    public void setInputBlockingTime(int milliseconds)
    {
        blockingTime = milliseconds;
    }
}
