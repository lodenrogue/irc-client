package com.arkvis.irc;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;

import java.util.List;
import java.util.function.Consumer;

public class TestSuccessfulConnectionEngine implements Engine {

    private final Connection connection;

    public TestSuccessfulConnectionEngine(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void connect(String serverName, List<String> nicks, Consumer<Connection> onSuccess, Runnable onError) {
        onSuccess.accept(connection);
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {
        resultHandler.onSuccess(connection);
    }
}
