package com.arkvis.irc.testengines.channelmessage;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.testengines.AbstractTestEngine;

import java.util.function.Consumer;

public class TestChannelMessageEngine extends AbstractTestEngine {

    private final ChannelEvent channelEvent;
    private Consumer<ChannelEvent> listener;

    public TestChannelMessageEngine(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }

    @Override
    public void addChannelMessageListener(Consumer<ChannelEvent> listener) {
        this.listener = listener;
    }

    public void sendChannelMessage() {
        this.listener.accept(channelEvent);
    }
}
