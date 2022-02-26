package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IRCClient {
    private final Engine engine;
    private final List<ResultHandler<Connection>> connectionListeners;

    public IRCClient(Engine engine) {
        this.engine = engine;
        connectionListeners = new ArrayList<>();
    }

    public void connect(String serverName, List<String> nicks, Consumer<Connection> onSuccess, Consumer<String> onError) {
        String errorMessage = String.format("Failed to connect to %s", serverName);
        engine.connect(serverName, nicks, onSuccess, () -> onError.accept(errorMessage));
    }

    public void connect(String serverName, List<String> nicks) {
        ResultHandler<Connection> resultHandler = new ResultHandler<>() {
            public void onSuccess(Connection connection) {
                connectionListeners.forEach(listener -> listener.onSuccess(connection));
            }

            public void onError() {
                connectionListeners.forEach(ResultHandler::onError);
            }
        };

        engine.connect(serverName, nicks, resultHandler);
    }

    public void registerConnectionListener(ResultHandler<Connection> listener) {
        connectionListeners.add(listener);
    }
}
