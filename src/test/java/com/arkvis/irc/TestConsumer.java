package com.arkvis.irc;

import java.util.function.Consumer;

public class TestConsumer<T> implements Consumer<T> {
    private T accepted;
    private boolean wasCalled;

    @Override
    public void accept(T t) {
        accepted = t;
        wasCalled = true;
    }

    public boolean wasCalled() {
        return wasCalled;
    }

    public T getAccepted() {
        return accepted;
    }
}
