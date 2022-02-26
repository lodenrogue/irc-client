package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;

abstract class AbstractTestConnectionEngine implements Engine {

    @Override
    public void joinChannel(String channelName, ResultHandler<Channel> resultHandler) {

    }
}
