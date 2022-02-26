package com.arkvis.irc.testengines.connection;

import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.testengines.AbstractTestEngine;

import java.util.List;

public class TestFailedConnectionEngine extends AbstractTestEngine {

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {
        resultHandler.onError();
    }
}
