package com.arkvis.irc.connection;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.*;

import java.util.List;

public class TestSuccessfulConnectionEngine extends AbstractTestEngine {

    private ConnectionEvent connectionEvent;

    public TestSuccessfulConnectionEngine() {

    }

    public TestSuccessfulConnectionEngine(ConnectionEvent connectionEvent) {
        this.connectionEvent = connectionEvent;
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {
        resultHandler.onSuccess(connectionEvent);
    }

    @Override
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        User user = new User(connection.getNicks().get(0));
        resultHandler.onSuccess(new Server(connection.getServerName(), user));
    }
}
