package com.arkvis.irc.channelmessage;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.AbstractTestEngine;

public class TestSuccessfulSendMessageEngine extends AbstractTestEngine {

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<ChannelEvent> resultHandler) {
        resultHandler.onSuccess(new ChannelEvent(channelName, null, message));
    }
}
