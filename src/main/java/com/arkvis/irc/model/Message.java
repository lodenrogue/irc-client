package com.arkvis.irc.model;

public class Message {
    private final String message;
    private final User sender;

    public Message(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }
}
