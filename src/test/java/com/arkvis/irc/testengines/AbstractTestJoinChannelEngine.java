package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;

import java.util.List;

abstract class AbstractTestJoinChannelEngine implements Engine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {

    }
}
