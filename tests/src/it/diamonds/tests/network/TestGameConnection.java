package it.diamonds.tests.network;

import it.diamonds.engine.Environment;
import it.diamonds.network.GameConnection;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.TestCase;

public class TestGameConnection extends TestCase
{
    private String address; 
    private int port; 
    private int serverTimeout; 
    
    private Environment environment;
    private GameConnection gameConnection;
    private GameConnection clientFailingConnection;
    private GameConnection serverFailingConnection;
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        environment = MockEnvironment.create();
        gameConnection = new GameConnection(environment, new MockSocketFactory());
        clientFailingConnection = new GameConnection(environment, new MockSocketFactory(MockSocketFactoryClientFlags.FAIL, MockSocketFactoryServerFlags.SUCCESS));
        serverFailingConnection = new GameConnection(environment, new MockSocketFactory(MockSocketFactoryClientFlags.FAIL, MockSocketFactoryServerFlags.GENERATE_TIMEOUT));
        
        address = environment.getConfig().getString("RemoteIp");
        port = environment.getConfig().getInteger("RemotePort");
        serverTimeout = environment.getConfig().getInteger("ServerTimeout");
    }

    public void testServerAddress()
    {
        assertEquals(address, gameConnection.getAddress());
    }
    
    public void testServerPort()
    {
        assertEquals(port, gameConnection.getPort());
    }
    
    public void testNotServerIfClientConnects()
    {
        gameConnection.connect();
        assertFalse(gameConnection.isServer());
        assertTrue(gameConnection.isClient());
        assertNotNull(gameConnection.getClient());
    }
    
    public void testClientIsConnectedWith()
    {
        gameConnection.connect();     
        assertTrue(gameConnection.isConnected());
    }
    
    public void testCloseClient()
    {
        gameConnection.connect();     
        gameConnection.close();
        assertFalse(gameConnection.isConnected());
    }

    public void testServerIfClientCantConnect()
    {
        clientFailingConnection.connect();
        assertTrue(clientFailingConnection.isServer());
        assertFalse(clientFailingConnection.isClient());
        assertNotNull(clientFailingConnection.getServer());
    }
    
    public void testServerIsConnectedWith()
    {
        clientFailingConnection.connect();
        assertTrue(clientFailingConnection.isConnected());
    }
    
    
    public void testServerCloseConnection()
    {
        clientFailingConnection.connect();
        MockServerSocket serverSocket = (MockServerSocket)clientFailingConnection.getServer();  
        assertTrue(serverSocket.isClosed());
    }

    public void testServerTimeout()
    {
        assertEquals(serverTimeout, gameConnection.getTimeout());
    }
    
    public void testServerNotConnectedAfterTimeout()
    {
        serverFailingConnection.connect();
        assertFalse(serverFailingConnection.isConnected());
    }
    public void testClientAddressAndPortUsed()
    {
        gameConnection.connect();
        
        MockSocket client = (MockSocket)gameConnection.getClient();
        assertEquals(address, client.getAddressUsed());
        assertEquals(port, client.getPortUsed());
    }
    
    public void testServerPortAndTimeoutUsed()
    {
        clientFailingConnection.connect();
        
        MockServerSocket server = (MockServerSocket)clientFailingConnection.getServer();
        assertEquals(port, server.getPortUsed());
        assertEquals(serverTimeout, server.getTimeoutUsed());
    }
    
    public void testWrite()
    {
        gameConnection.connect();
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        gameConnection.write(1);
        assertEquals(1, client.getLastOutputData());
        gameConnection.write(2);
        assertEquals(2, client.getLastOutputData());
    }
    
    public void testRead()
    {
        gameConnection.connect();
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        client.setLastInputData(1);
        assertEquals(1, gameConnection.read());
        client.setLastInputData(2);
        assertEquals(2, gameConnection.read());
    }
    
    public void testReadError()
    {
        gameConnection.connect();
        
        assertEquals(MockSocket.READ_ERROR, gameConnection.read());
    }
    
    public void testNoReadError()
    {
        int message = 3;
        assertFalse(message == MockSocket.READ_ERROR);
        
        gameConnection.connect();
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        client.setLastInputData(message);
        assertFalse(gameConnection.read() == MockSocket.READ_ERROR);
    }
}
