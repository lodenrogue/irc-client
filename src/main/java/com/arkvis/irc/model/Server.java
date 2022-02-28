package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final Engine engine;
    private final String name;
    private final User primaryUser;

    private final List<ResultHandler<Channel>> userJoinChannelListeners;

    public static void connect(Engine engine, Connection connection, ResultHandler<Server> resultHandler) {
        engine.connect(connection, resultHandler);
    }

    public Server(Engine engine, String name, User primaryUser) {
        this.engine = engine;
        this.name = name;
        this.primaryUser = primaryUser;
        userJoinChannelListeners = new ArrayList<>();
    }

    public void addUserJoinChannelListener(ResultHandler<Channel> listener) {
        userJoinChannelListeners.add(listener);
    }

    public String getName() {
        return name;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void joinChannel(String channelName) {
        engine._joinChannel(channelName, createResultHandler(userJoinChannelListeners));
    }

    private <T> ResultHandler<T> createResultHandler(List<ResultHandler<T>> listeners) {
        return new SimpleResultHandler<>(
                (t) -> listeners.forEach(listener -> listener.onSuccess(t)),
                () -> listeners.forEach(ResultHandler::onError));
    }
}
