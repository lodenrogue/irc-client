package com.arkvis.irc.model;

public class ChannelEvent {
    private final String name;
    private final String message;
    private final String sender;

    public ChannelEvent(String name, String sender, String message) {
        this.name = name;
        this.sender = sender;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
