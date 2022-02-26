package com.arkvis.irc.ui;

import com.arkvis.irc.ircclouds.IRCCloudsEngine;
import com.arkvis.irc.model.IRCClient;

import java.util.Objects;

public class IRC {
    private static IRCClient client;

    public static IRCClient getClient() {
        if (Objects.isNull(client)) {
            client = new IRCClient(new IRCCloudsEngine());
        }
        return client;
    }
}
