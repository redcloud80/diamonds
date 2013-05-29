package it.diamonds.tests.network;

import it.diamonds.network.GameServerSocket;
import it.diamonds.network.GameSocket;
import it.diamonds.network.SocketFactory;

public class MockSocketFactory extends SocketFactory
{
    
    private boolean mustFail;
    private boolean serverTimeout;
    
    public MockSocketFactory()
    {
        mustFail = false;
        serverTimeout = false;
    }
    
    public MockSocketFactory(MockSocketFactoryClientFlags clientFlags, MockSocketFactoryServerFlags serverFlags)
    {
        this.mustFail = clientFlags.equals(MockSocketFactoryClientFlags.FAIL); 
        this.serverTimeout = serverFlags.equals(MockSocketFactoryServerFlags.GENERATE_TIMEOUT); 
    }
    
    public GameSocket createSocket(String address, int port)
    {
        if (mustFail)
        {
            return new MockFailsSocket(address, port);
        }
        
        return new MockSocket(address, port);
    }

    public GameServerSocket createServerSocket(int port)
    {
        return new MockServerSocket(port, serverTimeout);
    }
}
