package com.arkvis.irc;

import java.util.function.Consumer;

public class TestConsumer<T> implements Consumer<T> {
    private T t;

    @Override
    public void accept(T t) {
        this.t = t;
    }

    public T getAccepted() {
        return t;
    }
}
