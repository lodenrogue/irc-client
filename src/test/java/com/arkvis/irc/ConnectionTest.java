package com.arkvis.irc;

import com.arkvis.irc.model.Connection;
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
        Connection connection = new Connection("nick1", serverName);
        IRCClient client = new IRCClient(new TestSuccessfulConnectionEngine(connection));

        TestResultHandler<Connection> resultHandler = new TestResultHandler<>();
        client.registerConnectionListener(resultHandler);

        client.connect(serverName, List.of("nick1", "nick2"));
        assertEquals(connection.getServerName(), resultHandler.getAccepted().getServerName());
        assertFalse(resultHandler.wasOnErrorCalled());
    }

    @Test
    void should_adviseOfError_when_connectionFailed() {

        IRCClient client = new IRCClient(new TestFailedConnectionEngine());
        TestResultHandler<Connection> resultHandler = new TestResultHandler<>();
        client.registerConnectionListener(resultHandler);

        client.connect(serverName, List.of("nick1", "nick2"));
        assertTrue(resultHandler.wasOnErrorCalled());
        assertFalse(resultHandler.wasOnSuccessCalled());
    }

    @Test
    void should_returnCorrectNickName_when_connectingSuccessfully() {
        String nickName = "TEST_NICK";
        Connection connection = new Connection(nickName, serverName);

        IRCClient client = new IRCClient(new TestSuccessfulConnectionEngine(connection));
        TestResultHandler<Connection> resultHandler = new TestResultHandler<>();
        client.registerConnectionListener(resultHandler);

        client.connect(serverName, List.of(nickName, "nick2"));
        assertEquals(nickName, resultHandler.getAccepted().getNickName());
    }
}
