package com.arkvis.irc.connection;

import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.AbstractTestEngine;

import java.util.List;

public class TestSuccessfulConnectionEngine extends AbstractTestEngine {

    private final ConnectionEvent connectionEvent;

    public TestSuccessfulConnectionEngine(ConnectionEvent connectionEvent) {
        this.connectionEvent = connectionEvent;
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {
        resultHandler.onSuccess(connectionEvent);
    }
}
