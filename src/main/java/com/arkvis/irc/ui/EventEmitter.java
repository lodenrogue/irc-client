package com.arkvis.irc.ui;

import com.arkvis.irc.model.OtherQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventEmitter {
    private static EventEmitter instance;

    private final List<Consumer<String>> selectChannelListeners;
    private final List<BiConsumer<String, OtherQuitEvent>> otherUserQuitListeners;

    private EventEmitter() {
        selectChannelListeners = new ArrayList<>();
        otherUserQuitListeners = new ArrayList<>();
    }

    public static EventEmitter getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EventEmitter();
        }
        return instance;
    }

    public void registerSelectChannelListener(Consumer<String> listener) {
        selectChannelListeners.add(listener);
    }

    public void registerOtherUserQuitListener(BiConsumer<String, OtherQuitEvent> listener) {
        otherUserQuitListeners.add(listener);
    }

    public void emitSelectChannelEvent(String channelName) {
        selectChannelListeners.forEach(listener -> listener.accept(channelName));
    }

    public void emitOtherUserQuitEvent(String channelName, OtherQuitEvent event) {
        otherUserQuitListeners.forEach(listener -> listener.accept(channelName, event));
    }
}
