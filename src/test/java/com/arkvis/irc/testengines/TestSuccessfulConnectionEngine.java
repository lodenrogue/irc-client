package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.ResultHandler;

import java.util.List;

public class TestSuccessfulConnectionEngine extends AbstractTestConnectionEngine {

    private final Connection connection;

    public TestSuccessfulConnectionEngine(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {
        resultHandler.onSuccess(connection);
    }
}
