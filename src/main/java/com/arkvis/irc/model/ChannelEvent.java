package com.arkvis.irc.model;

public class ChannelEvent {
    private final String name;
    private final String message;

    public ChannelEvent(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
