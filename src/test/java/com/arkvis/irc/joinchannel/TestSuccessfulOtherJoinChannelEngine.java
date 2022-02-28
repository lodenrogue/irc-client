package com.arkvis.irc.joinchannel;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.*;

import java.util.Collections;
import java.util.function.Consumer;

public class TestSuccessfulOtherJoinChannelEngine extends AbstractTestEngine {

    private Consumer<OtherJoinEvent> listener;
    private Consumer<User> userConsumer;

    @Override
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        User user = new User(connection.getNicks().get(0));
        resultHandler.onSuccess(new Server(this, connection.getServerName(), user));
    }

    @Override
    public void _joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onSuccess(new Channel(this, channelName, Collections.emptyList()));
    }

    @Override
    public void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void _addOtherJoinChannelListener(String channelName, Consumer<User> listener) {
        userConsumer = listener;
    }

    public void sendJoinEvent(OtherJoinEvent event) {
        listener.accept(event);
    }

    public void sendJoinEvent(User user) {
        userConsumer.accept(user);
    }
}
