package com.arkvis.irc.testengines.joinchannel;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.testengines.AbstractTestEngine;

public class TestFailedJoinChannelEngine extends AbstractTestEngine {

    @Override
    public void joinChannel(String channelName, ResultHandler<ChannelEvent> resultHandler) {
        resultHandler.onError();
    }
}