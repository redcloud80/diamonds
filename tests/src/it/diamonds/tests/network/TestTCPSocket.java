package it.diamonds.tests.network;

import it.diamonds.network.GameServerSocket;
import it.diamonds.network.GameSocket;
import it.diamonds.network.TCPServerSocket;
import it.diamonds.network.TCPSocket;
import junit.framework.TestCase;


public class TestTCPSocket extends TestCase
{
    public void testTcpNoDelay()
    {
        GameServerSocket serverSocket = new TCPServerSocket(12345);
        
        TCPSocket socket = new TCPSocket("127.0.0.1", 12345);
        
        serverSocket.close();
        
        assertTrue(socket.getTcpNoDelay());
    }    
    
    public void testNothingToRead()
    {
        GameServerSocket serverSocket = new TCPServerSocket(12345);
        
        GameSocket socket = new TCPSocket("127.0.0.1", 12345);
        
        assertEquals(TCPSocket.READ_ERROR, socket.read());
        
        serverSocket.close();
    }
}
