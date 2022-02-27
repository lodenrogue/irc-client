package com.arkvis.irc.testengines.channelmessage;

import com.arkvis.irc.model.MessageEvent;
import com.arkvis.irc.testengines.AbstractTestEngine;

import java.util.function.Consumer;

public class TestChannelMessageEngine extends AbstractTestEngine {

    private Consumer<MessageEvent> listener;

    @Override
    public void addChannelMessageListener(Consumer<MessageEvent> listener) {
        this.listener = listener;
    }

    public void sendChannelMessage(MessageEvent messageEvent) {
        this.listener.accept(messageEvent);
    }
}
