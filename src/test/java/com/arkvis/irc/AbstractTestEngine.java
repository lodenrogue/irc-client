package com.arkvis.irc;

import com.arkvis.irc.model.*;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractTestEngine implements Engine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {

    }

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {

    }

    @Override
    public void addChannelMessageListener(Consumer<MessageEvent> listener) {

    }

    @Override
    public void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener) {

    }

    @Override
    public void addOtherLeaveChannelListener(Consumer<OtherLeaveEvent> listener) {

    }

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<MessageEvent> resultHandler) {

    }
}
