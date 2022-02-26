package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;

public class IRCClient {
    private final Engine engine;
    private final List<ResultHandler<Connection>> connectionListeners;
    private final List<ResultHandler<Channel>> joinChannelListeners;

    public IRCClient(Engine engine) {
        this.engine = engine;
        connectionListeners = new ArrayList<>();
        joinChannelListeners = new ArrayList<>();
    }

    public void connect(String serverName, List<String> nicks) {
        engine.connect(serverName, nicks, createResultHandler(connectionListeners));
    }

    public void joinChannel(String channelName) {
        engine.joinChannel(channelName, createResultHandler(joinChannelListeners));
    }

    public void registerConnectionListener(ResultHandler<Connection> listener) {
        connectionListeners.add(listener);
    }

    public void registerJoinChannelListener(ResultHandler<Channel> listener) {
        joinChannelListeners.add(listener);
    }

    private <T> ResultHandler<T> createResultHandler(List<ResultHandler<T>> listeners) {
        return new ResultHandler<>() {
            @Override
            public void onSuccess(T t) {
                listeners.forEach(listener -> listener.onSuccess(t));
            }

            @Override
            public void onError() {
                listeners.forEach(ResultHandler::onError);
            }
        };
    }
}
