package com.arkvis.irc.ui;

import com.arkvis.irc.model.ResultHandler;

import java.util.function.Consumer;

public class SimpleResultHandler<T> implements ResultHandler<T> {

    private final Consumer<T> onSuccess;
    private final Runnable onError;

    public SimpleResultHandler(Consumer<T> onSuccess, Runnable onError) {
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    public void onSuccess(T t) {
        onSuccess.accept(t);
    }

    @Override
    public void onError() {
        onError.run();
    }
}
