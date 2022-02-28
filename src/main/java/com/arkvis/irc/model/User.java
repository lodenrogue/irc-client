package com.arkvis.irc.model;

public class User {

    private final String nickName;

    public User(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
