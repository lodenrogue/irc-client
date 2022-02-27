package com.arkvis.irc.testengines;

import com.arkvis.irc.model.*;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractTestEngine implements Engine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {

    }

    @Override
    public void joinChannel(String channelName, ResultHandler<ChannelEvent> resultHandler) {

    }

    @Override
    public void addChannelMessageListener(Consumer<ChannelEvent> listener) {

    }

    @Override
    public void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener) {

    }

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<ChannelEvent> resultHandler) {

    }
}
