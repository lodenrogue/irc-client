package com.arkvis.irc.joinchannel;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.*;

public class TestFailedJoinChannelEngine extends AbstractTestEngine {

    @Override
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        User user = new User(connection.getNicks().get(0));
        resultHandler.onSuccess(new Server(this, connection.getServerName(), user));
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {
        resultHandler.onError();
    }

    @Override
    public void _joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onError();
    }
}
