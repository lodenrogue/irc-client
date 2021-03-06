package com.arkvis.irc.joinchannel;

import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.model.UserJoinEvent;
import com.arkvis.irc.AbstractTestEngine;

public class TestFailedJoinChannelEngine extends AbstractTestEngine {

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {
        resultHandler.onError();
    }
}
