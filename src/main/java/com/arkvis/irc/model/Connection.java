package com.arkvis.irc.model;

public class Connection {

    private final String nickName;
    private final String serverName;

    public Connection(String nickName, String serverName) {
        this.nickName = nickName;
        this.serverName = serverName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getServerName() {
        return serverName;
    }
}
