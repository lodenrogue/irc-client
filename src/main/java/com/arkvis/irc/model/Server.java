package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private final Engine engine;
    private final String name;
    private final User primaryUser;
    private final List<Channel> channels;

    private final List<ResultHandler<Channel>> userJoinChannelListeners;

    public static void connect(Engine engine, Connection connection, ResultHandler<Server> resultHandler) {
        engine.connect(connection, resultHandler);
    }

    public Server(Engine engine, String name, User primaryUser) {
        this.engine = engine;
        this.name = name;
        this.primaryUser = primaryUser;

        channels = new ArrayList<>();
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
        engine._joinChannel(channelName, new SimpleResultHandler<>(
                this::addChannel,
                () -> userJoinChannelListeners.forEach(ResultHandler::onError)));
    }

    private void addChannel(Channel channel) {
        channels.add(channel);
        userJoinChannelListeners.forEach(listener -> listener.onSuccess(channel));
    }

    public List<Channel> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}
