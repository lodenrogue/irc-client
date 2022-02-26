package com.arkvis.irc;

import com.arkvis.irc.model.ResultHandler;

public class TestResultHandler<T> implements ResultHandler<T> {
    private T accepted;
    private boolean onErrorCalled;
    private boolean onSuccessCalled;

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
        return onSuccessCalled;
    }
}
