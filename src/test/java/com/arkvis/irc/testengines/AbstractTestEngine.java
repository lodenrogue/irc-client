package com.arkvis.irc.testengines;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;

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
}
