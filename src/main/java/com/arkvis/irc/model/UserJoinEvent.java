package com.arkvis.irc.model;

import java.util.List;

public class UserJoinEvent {
    private final String channelName;
    private final List<String> users;

    public UserJoinEvent(String channelName, List<String> users) {
        this.channelName = channelName;
        this.users = users;
    }

    public String getChannelName() {
        return channelName;
    }

    public List<String> getUsers() {
        return users;
    }
}
