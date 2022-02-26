package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IRCClient {
    private final Engine engine;
    private final List<ResultHandler<ConnectionEvent>> connectionListeners;
    private final List<ResultHandler<ChannelEvent>> joinChannelListeners;
    private final List<Consumer<ChannelEvent>> channelMessageListeners;

    public IRCClient(Engine engine) {
        this.engine = engine;
        connectionListeners = new ArrayList<>();
        joinChannelListeners = new ArrayList<>();
        channelMessageListeners = new ArrayList<>();

        createChannelMessageListener();
    }

    public void connect(String serverName, List<String> nicks) {
        engine.connect(serverName, nicks, createResultHandler(connectionListeners));
    }

    public void joinChannel(String channelName) {
        engine.joinChannel(channelName, createResultHandler(joinChannelListeners));
    }

    public void addConnectionListener(ResultHandler<ConnectionEvent> listener) {
        connectionListeners.add(listener);
    }

    public void addJoinChannelListener(ResultHandler<ChannelEvent> listener) {
        joinChannelListeners.add(listener);
    }

    public void addChannelMessageListener(Consumer<ChannelEvent> listener) {
        channelMessageListeners.add(listener);
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

    private void createChannelMessageListener() {
        engine.addChannelMessageListener(this::notifyChannelMessageListeners);
    }

    private void notifyChannelMessageListeners(ChannelEvent channelEvent) {
        channelMessageListeners.forEach(listener -> listener.accept(channelEvent));
    }
}
