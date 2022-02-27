package com.arkvis.irc.model;

public class OtherJoinEvent {
    private final String channelName;
    private final String nickName;

    public OtherJoinEvent(String channelName, String nickName) {
        this.channelName = channelName;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getChannelName() {
        return channelName;
    }
}
