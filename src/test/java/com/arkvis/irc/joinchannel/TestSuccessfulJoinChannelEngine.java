package com.arkvis.irc.joinchannel;

import com.arkvis.irc.AbstractTestEngine;
import com.arkvis.irc.model.*;

import java.util.List;

public class TestSuccessfulJoinChannelEngine extends AbstractTestEngine {
    private UserJoinEvent joinEvent;
    private List<User> users;

    public TestSuccessfulJoinChannelEngine() {
    }

    public TestSuccessfulJoinChannelEngine(List<User> users) {
        this.users = users;
    }

    public TestSuccessfulJoinChannelEngine(UserJoinEvent joinEvent) {
        this.joinEvent = joinEvent;
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {
        resultHandler.onSuccess(joinEvent);
    }

    @Override
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        User user = new User(connection.getNicks().get(0));
        resultHandler.onSuccess(new Server(this, connection.getServerName(), user));
    }

    @Override
    public void _joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        resultHandler.onSuccess(new Channel(channelName, users));
    }
}
