package com.arkvis.irc.testengines;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.ResultHandler;

import java.util.List;

public class TestFailedConnectionEngine extends AbstractTestConnectionEngine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {
        resultHandler.onError();
    }
}
