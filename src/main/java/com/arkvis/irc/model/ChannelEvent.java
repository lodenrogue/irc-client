package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelEvent {
    private final String name;
    private final String message;
    private final String sender;
    private final List<String> users;

    public ChannelEvent(String name, String sender, String message) {
        this.name = name;
        this.users = new ArrayList<>();
        this.sender = sender;
        this.message = message;
    }

    public ChannelEvent(String name, List<String> users, String sender, String message) {
        this.name = name;
        this.users = users;
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

    public List<String> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
