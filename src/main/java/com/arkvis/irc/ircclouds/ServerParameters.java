package com.arkvis.irc.ircclouds;

import com.ircclouds.irc.api.IServerParameters;
import com.ircclouds.irc.api.domain.IRCServer;

import java.util.List;

public class ServerParameters implements IServerParameters {

    private final String serverName;
    private final String primaryNick;
    private final List<String> altNicks;
    private final String realName;
    private final String identity;
    private final boolean isSSLServer;

    public ServerParameters(String serverName, String primaryNick, List<String> altNicks, String realName, String identity, boolean isSSLServer) {
        this.serverName = serverName;
        this.primaryNick = primaryNick;
        this.altNicks = altNicks;
        this.realName = realName;
        this.identity = identity;
        this.isSSLServer = isSSLServer;
    }

    @Override
    public String getNickname() {
        return primaryNick;
    }

    @Override
    public List<String> getAlternativeNicknames() {
        return altNicks;
    }

    @Override
    public String getIdent() {
        return identity;
    }

    @Override
    public String getRealname() {
        return realName;
    }

    @Override
    public IRCServer getServer() {
        return new IRCServer(serverName, isSSLServer);
    }
}
