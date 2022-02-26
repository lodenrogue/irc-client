package com.arkvis.irc.testengines.channelmessage;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.testengines.AbstractTestEngine;

import java.util.function.Consumer;

public class TestChannelMessageEngine extends AbstractTestEngine {

    private Consumer<ChannelEvent> listener;

    @Override
    public void addChannelMessageListener(Consumer<ChannelEvent> listener) {
        this.listener = listener;
    }

    public void sendChannelMessage(ChannelEvent channelEvent) {
        this.listener.accept(channelEvent);
    }
}
