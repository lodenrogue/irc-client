package com.arkvis.irc.connection;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.model.Server;

import java.util.List;

public class TestFailedConnectionEngine extends AbstractTestEngine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {
        resultHandler.onError();
    }

    @Override
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        resultHandler.onError();
    }
}
