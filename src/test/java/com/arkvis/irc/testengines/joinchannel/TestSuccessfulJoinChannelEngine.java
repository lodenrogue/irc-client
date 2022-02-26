package com.arkvis.irc.testengines.joinchannel;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.testengines.AbstractTestEngine;

public class TestSuccessfulJoinChannelEngine extends AbstractTestEngine {
    private final ChannelEvent channelEvent;

    public TestSuccessfulJoinChannelEngine(ChannelEvent channelEvent) {
        this.channelEvent = channelEvent;
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<ChannelEvent> resultHandler) {
        resultHandler.onSuccess(channelEvent);
    }
}
