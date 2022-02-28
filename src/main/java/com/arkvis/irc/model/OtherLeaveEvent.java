package com.arkvis.irc.model;

public class OtherLeaveEvent {

    private final String channelName;

    public OtherLeaveEvent(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
