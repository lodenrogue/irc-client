package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelEvent {
    private final String name;
    private final String message;
    private final List<String> users;

    public ChannelEvent(String name, List<String> users, String message) {
        this.name = name;
        this.users = users;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
