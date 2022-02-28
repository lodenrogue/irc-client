package com.arkvis.irc.channelmessage;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.*;

import java.util.Collections;
import java.util.function.Consumer;

public class TestChannelMessageEngine extends AbstractTestEngine {

    private Consumer<MessageEvent> listener;
    private Consumer<Message> messageConsumer;

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
    public void addChannelMessageListener(Consumer<MessageEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void _addReceiveChannelMessageListener(String channelName, Consumer<Message> listener) {
        messageConsumer = listener;
    }

    public void sendChannelMessage(MessageEvent messageEvent) {
        listener.accept(messageEvent);
    }

    public void sendChannelMessage(Message message) {
        messageConsumer.accept(message);
    }
}
