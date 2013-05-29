package it.diamonds.tests.network;

import it.diamonds.network.GameServerSocket;
import it.diamonds.network.GameSocket;
import it.diamonds.network.TCPServerSocket;
import it.diamonds.network.TCPSocket;
import junit.framework.TestCase;

public class TestTCPServerSocket extends TestCase
{
    public void testTimeout()
    {
        GameServerSocket socket = new TCPServerSocket(12345);

        assertFalse(socket.timeout());
        socket.accept(1);
        assertTrue(socket.timeout());
        
        socket.close();
    }
    
    
    public void testReturnedSocketNullAfterTimeout()
    {
        GameServerSocket socket = new TCPServerSocket(12345);
        
        assertNull(socket.accept(1));
        
        socket.close();
    }
    
    
    public void testReturnedSocketConnectedAfterSuccessfulAccept()
    {
        GameServerSocket server = new TCPServerSocket(12345);
        GameSocket client = new TCPSocket("127.0.0.1", 12345);
        
        assertNotNull(server.accept(1));
        
        client.close();
        server.close();
    }

}
