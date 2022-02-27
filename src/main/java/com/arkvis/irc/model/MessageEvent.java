package com.arkvis.irc.model;

public class MessageEvent {

    private final String channelName;
    private final String senderNick;
    private final String message;

    public MessageEvent(String channelName, String senderNick, String message) {
        this.channelName = channelName;
        this.senderNick = senderNick;
        this.message = message;
    }

    public MessageEvent(String channelName, String message) {
        this.channelName = channelName;
        this.message = message;
        this.senderNick = null;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getSenderNick() {
        return senderNick;
    }

    public String getMessage() {
        return message;
    }
}
