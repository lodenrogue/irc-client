package com.arkvis.irc.quit;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.OtherQuitEvent;

import java.util.function.Consumer;

public class TestOtherUserQuitEngine extends AbstractTestEngine {

    private Consumer<OtherQuitEvent> listener;

    @Override
    public void addOtherQuitListener(Consumer<OtherQuitEvent> listener) {
        this.listener = listener;
    }

    public void sendQuitEvent(OtherQuitEvent event) {
        listener.accept(event);
    }
}
