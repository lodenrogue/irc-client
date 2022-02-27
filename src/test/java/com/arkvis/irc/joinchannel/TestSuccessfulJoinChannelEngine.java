package com.arkvis.irc.joinchannel;

import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.model.UserJoinEvent;
import com.arkvis.irc.AbstractTestEngine;

public class TestSuccessfulJoinChannelEngine extends AbstractTestEngine {
    private final UserJoinEvent joinEvent;

    public TestSuccessfulJoinChannelEngine(UserJoinEvent joinEvent) {
        this.joinEvent = joinEvent;
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {
        resultHandler.onSuccess(joinEvent);
    }
}
