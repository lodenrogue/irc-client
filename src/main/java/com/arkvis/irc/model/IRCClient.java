package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IRCClient {
    private final Engine engine;
    private final List<ResultHandler<ConnectionEvent>> connectionListeners;
    private final List<ResultHandler<UserJoinEvent>> userJoinChannelListeners;
    private final List<Consumer<OtherJoinEvent>> otherJoinChannelListeners;
    private final List<Consumer<OtherLeaveEvent>> otherLeaveChannelListeners;
    private final List<Consumer<OtherQuitEvent>> otherQuitListeners;
    private final List<Consumer<MessageEvent>> channelMessageListeners;
    private final List<ResultHandler<MessageEvent>> sendMessageListeners;

    public IRCClient(Engine engine) {
        this.engine = engine;
        connectionListeners = new ArrayList<>();
        userJoinChannelListeners = new ArrayList<>();
        otherJoinChannelListeners = new ArrayList<>();
        otherLeaveChannelListeners = new ArrayList<>();
        otherQuitListeners = new ArrayList<>();
        channelMessageListeners = new ArrayList<>();
        sendMessageListeners = new ArrayList<>();

        engine.addChannelMessageListener(this::notifyChannelMessageListeners);
        engine.addOtherJoinChannelListener(this::notifyOtherJoinChannelListeners);
        engine.addOtherLeaveChannelListener(this::notifyOtherLeaveChannelListeners);
        engine.addOtherQuitListener(this::notifyOtherQuitListeners);
    }

    public void connect(String serverName, List<String> nicks) {
        engine.connect(serverName, nicks, createResultHandler(connectionListeners));
    }

    public void joinChannel(String channelName) {
        engine.joinChannel(channelName, createResultHandler(userJoinChannelListeners));
    }

    public void sendMessage(String channelName, String message) {
        engine.sendMessage(channelName, message, createResultHandler(sendMessageListeners));
    }

    public void addConnectionListener(ResultHandler<ConnectionEvent> listener) {
        connectionListeners.add(listener);
    }

    public void addUserJoinChannelListener(ResultHandler<UserJoinEvent> listener) {
        userJoinChannelListeners.add(listener);
    }

    public void addOtherUserJoinChannelListener(Consumer<OtherJoinEvent> listener) {
        otherJoinChannelListeners.add(listener);
    }

    public void addChannelMessageListener(Consumer<MessageEvent> listener) {
        channelMessageListeners.add(listener);
    }

    public void addSendMessageListener(ResultHandler<MessageEvent> listener) {
        sendMessageListeners.add(listener);
    }

    public void addOtherUserLeaveChannelListener(Consumer<OtherLeaveEvent> listener) {
        otherLeaveChannelListeners.add(listener);
    }

    public void addOtherUserQuitListener(Consumer<OtherQuitEvent> listener) {
        otherQuitListeners.add(listener);
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

    private void notifyChannelMessageListeners(MessageEvent event) {
        channelMessageListeners.forEach(listener -> listener.accept(event));
    }

    private void notifyOtherJoinChannelListeners(OtherJoinEvent event) {
        otherJoinChannelListeners.forEach(listener -> listener.accept(event));
    }

    private void notifyOtherLeaveChannelListeners(OtherLeaveEvent event) {
        otherLeaveChannelListeners.forEach(listener -> listener.accept(event));
    }

    private void notifyOtherQuitListeners(OtherQuitEvent event) {
        otherQuitListeners.forEach(listener -> listener.accept(event));
    }
}
