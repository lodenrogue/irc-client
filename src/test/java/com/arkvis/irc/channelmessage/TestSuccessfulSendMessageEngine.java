package com.arkvis.irc.channelmessage;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.MessageEvent;
import com.arkvis.irc.model.ResultHandler;

public class TestSuccessfulSendMessageEngine extends AbstractTestEngine {

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<MessageEvent> resultHandler) {
        resultHandler.onSuccess(new MessageEvent(channelName, message));
    }
}
