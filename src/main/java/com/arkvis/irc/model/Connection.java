package com.arkvis.irc.model;

import java.util.Collections;
import java.util.List;

public class Connection {

    private final List<String> nicks;
    private final String serverName;

    public Connection(String serverName, List<String> nicks) {
        this.serverName = serverName;
        this.nicks = nicks;
    }

    public List<String> getNicks() {
        return Collections.unmodifiableList(nicks);
    }

    public String getServerName() {
        return serverName;
    }
}
