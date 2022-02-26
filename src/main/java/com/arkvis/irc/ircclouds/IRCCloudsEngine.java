package com.arkvis.irc.ircclouds;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;
import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.state.IIRCState;

import java.util.List;

public class IRCCloudsEngine implements Engine {
    private static final String REAL_NAME = "IRC Api";
    private static final String IDENTITY = "ident";
    private static final boolean IS_SSL_SERVER = true;

    private final IRCApi ircApi;

    public IRCCloudsEngine() {
        ircApi = new IRCApiImpl(true);
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler) {
        String primaryNick = nicks.get(0);
        List<String> altNicks = nicks.subList(1, nicks.size());

        ServerParameters serverParameters = new ServerParameters(
                serverName,
                primaryNick,
                altNicks,
                REAL_NAME,
                IDENTITY,
                IS_SSL_SERVER);

        ircApi.connect(serverParameters, createConnectCallback(resultHandler));
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<Channel> resultHandler) {
        ircApi.joinChannel(channelName, createJoinChannelCallback(resultHandler));
    }

    private Callback<IRCChannel> createJoinChannelCallback(ResultHandler<Channel> resultHandler) {
        return new Callback<>() {
            @Override
            public void onSuccess(IRCChannel ircChannel) {
                resultHandler.onSuccess(toChannel(ircChannel));
            }

            @Override
            public void onFailure(Exception exception) {
                resultHandler.onError();
            }

            private Channel toChannel(IRCChannel ircChannel) {
                return new Channel(ircChannel.getName());
            }
        };
    }

    private Callback<IIRCState> createConnectCallback(ResultHandler<Connection> resultHandler) {
        return new Callback<>() {
            @Override
            public void onSuccess(IIRCState state) {
                resultHandler.onSuccess(toConnection(state));
            }

            @Override
            public void onFailure(Exception ex) {
                resultHandler.onError();
            }

            private Connection toConnection(IIRCState state) {
                return new Connection(state.getNickname(), state.getServer().getHostname());
            }
        };
    }
}
