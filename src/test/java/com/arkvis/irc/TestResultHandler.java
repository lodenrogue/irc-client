package com.arkvis.irc;

import com.arkvis.irc.model.ResultHandler;

import java.util.Objects;

class TestResultHandler<T> implements ResultHandler<T> {
    private T accepted;
    private boolean onErrorCalled;

    @Override
    public void onSuccess(T t) {
        accepted = t;
    }

    @Override
    public void onError() {
        onErrorCalled = true;
    }

    public T getAccepted() {
        return accepted;
    }

    public boolean wasOnErrorCalled() {
        return onErrorCalled;
    }

    public boolean wasOnSuccessCalled() {
        return Objects.nonNull(accepted);
    }
}
