package com.arkvis.irc.leavechannel;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.OtherLeaveEvent;

import java.util.function.Consumer;

public class TestOtherUserLeaveChannelEngine extends AbstractTestEngine {
    private Consumer<OtherLeaveEvent> listener;

    @Override
    public void addOtherLeaveChannelListener(Consumer<OtherLeaveEvent> listener) {
        this.listener = listener;
    }

    public void sendLeaveChannelEvent(OtherLeaveEvent event) {
        listener.accept(event);
    }
}
