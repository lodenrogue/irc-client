package com.arkvis.irc.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class EventEmitter {
    private static EventEmitter instance;

    private final List<Consumer<String>> selectChannelListeners;

    private EventEmitter() {
        selectChannelListeners = new ArrayList<>();
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

    public void emitSelectChannelEvent(String channelName) {
        selectChannelListeners.forEach(listener -> listener.accept(channelName));
    }
}
