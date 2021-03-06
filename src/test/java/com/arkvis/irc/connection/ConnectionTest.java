package com.arkvis.irc.connection;

import com.arkvis.irc.TestResultHandler;
import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.IRCClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    private String serverName;

    @BeforeEach
    void setUp() {
        serverName = "TEST_SERVER_NAME";
    }

    @Test
    void should_returnCorrectServerName_when_connectingSuccessfully() {
        ConnectionEvent connectionEvent = new ConnectionEvent("nick1", serverName);
        IRCClient client = new IRCClient(new TestSuccessfulConnectionEngine(connectionEvent));

        TestResultHandler<ConnectionEvent> resultHandler = new TestResultHandler<>();
        client.addConnectionListener(resultHandler);

        client.connect(serverName, List.of("nick1", "nick2"));
        assertEquals(connectionEvent.getServerName(), resultHandler.getAccepted().getServerName());
        assertFalse(resultHandler.wasOnErrorCalled());
    }

    @Test
    void should_adviseOfError_when_connectionFailed() {
        IRCClient client = new IRCClient(new TestFailedConnectionEngine());
        TestResultHandler<ConnectionEvent> resultHandler = new TestResultHandler<>();
        client.addConnectionListener(resultHandler);

        client.connect(serverName, List.of("nick1", "nick2"));
        assertTrue(resultHandler.wasOnErrorCalled());
        assertFalse(resultHandler.wasOnSuccessCalled());
    }

    @Test
    void should_returnCorrectNickName_when_connectingSuccessfully() {
        String nickName = "TEST_NICK";
        ConnectionEvent connectionEvent = new ConnectionEvent(nickName, serverName);

        IRCClient client = new IRCClient(new TestSuccessfulConnectionEngine(connectionEvent));
        TestResultHandler<ConnectionEvent> resultHandler = new TestResultHandler<>();
        client.addConnectionListener(resultHandler);

        client.connect(serverName, List.of(nickName, "nick2"));
        assertEquals(nickName, resultHandler.getAccepted().getNickName());
    }
}
