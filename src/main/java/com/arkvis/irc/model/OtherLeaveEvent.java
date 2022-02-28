package com.arkvis.irc.model;

public class OtherLeaveEvent {

    private final String channelName;
    private final String nickName;

    public OtherLeaveEvent(String channelName, String nickName) {
        this.channelName = channelName;
        this.nickName = nickName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getNickName() {
        return nickName;
    }
}
