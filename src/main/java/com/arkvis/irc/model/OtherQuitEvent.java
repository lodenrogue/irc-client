package com.arkvis.irc.model;

public class OtherQuitEvent {

    private final String nickName;
    private final String message;

    public OtherQuitEvent(String nickName) {
        this.nickName = nickName;
        this.message = null;
    }

    public OtherQuitEvent(String nickName, String message) {
        this.nickName = nickName;
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMessage() {
        return message;
    }
}
