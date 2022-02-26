package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.ResultHandler;

public class TestSuccessfulJoinChannelEngine extends AbstractTestJoinChannelEngine {
    private final Channel channel;

    public TestSuccessfulJoinChannelEngine(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onSuccess(channel);
    }
}
