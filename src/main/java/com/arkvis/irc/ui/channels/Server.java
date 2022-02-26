package com.arkvis.irc.ui.channels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private final String name;
    private final List<String> channels;

    public Server(String name) {
        this.name = name;
        channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addChannel(String channelName) {
        channels.add(channelName);
    }

    public List<String> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}
