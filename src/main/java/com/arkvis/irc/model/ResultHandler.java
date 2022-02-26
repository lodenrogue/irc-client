package com.arkvis.irc.model;

public interface ResultHandler<T> {

    void onSuccess(T t);

    void onError();
}
