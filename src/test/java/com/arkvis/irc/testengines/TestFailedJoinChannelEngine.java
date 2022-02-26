package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.ResultHandler;

public class TestFailedJoinChannelEngine extends AbstractTestJoinChannelEngine {

    @Override
    public void joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onError();
    }
}
