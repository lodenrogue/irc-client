package com.arkvis.irc.model;

import java.util.List;

public class Channel {

    private final String name;
    private final List<User> users;

    public Channel(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }
}
