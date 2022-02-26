package com.arkvis.irc.testhelper;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;

import java.util.List;

public class TestSuccessfulChannelJoinEngine implements Engine {
    private final Channel channel;

    public TestSuccessfulChannelJoinEngine(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {

    }

    @Override
    public void joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onSuccess(channel);
    }
}
