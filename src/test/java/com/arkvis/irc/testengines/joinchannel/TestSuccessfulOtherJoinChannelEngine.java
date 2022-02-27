package com.arkvis.irc.testengines.joinchannel;

import com.arkvis.irc.model.OtherJoinEvent;
import com.arkvis.irc.testengines.AbstractTestEngine;

import java.util.function.Consumer;

public class TestSuccessfulOtherJoinChannelEngine extends AbstractTestEngine {

    private Consumer<OtherJoinEvent> listener;

    @Override
    public void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener) {
        this.listener = listener;
    }

    public void sendJoinEvent(OtherJoinEvent event) {
        listener.accept(event);
    }
}
