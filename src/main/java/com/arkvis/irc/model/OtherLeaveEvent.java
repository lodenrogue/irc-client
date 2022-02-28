package com.arkvis.irc.model;

public class OtherLeaveEvent {

    private final String channelName;
    private final String nickName;
    private final String partingMessage;

    public OtherLeaveEvent(String channelName, String nickName, String partingMessage) {
        this.channelName = channelName;
        this.nickName = nickName;
        this.partingMessage = partingMessage;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPartingMessage() {
        return partingMessage;
    }
}
