package com.arkvis.irc.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class EventEmitter {
    private static EventEmitter instance;

    private final List<Consumer<String>> selectViewListeners;

    private EventEmitter() {
        selectViewListeners = new ArrayList<>();
    }

    public static EventEmitter getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EventEmitter();
        }
        return instance;
    }

    public void registerSelectViewListener(Consumer<String> listener) {
        selectViewListeners.add(listener);
    }

    public void emitSelectViewEvent(String viewName) {
        selectViewListeners.forEach(listener -> listener.accept(viewName));
    }
}
