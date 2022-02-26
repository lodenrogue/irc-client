package com.arkvis.irc.ircclouds;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.ResultHandler;
import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.state.IIRCState;

import java.util.List;
import java.util.function.Consumer;

public class IRCCloudsEngine implements Engine {
    private static final String REAL_NAME = "IRC Api";
    private static final String IDENTITY = "ident";
    private static final boolean IS_SSL_SERVER = true;

    private final IRCApi ircApi;

    public IRCCloudsEngine() {
        ircApi = new IRCApiImpl(true);
    }

    @Override
    public void connect(String serverName, List<String> nicks, Consumer<Connection> onSuccess, Runnable onError) {
        String primaryNick = nicks.get(0);
        List<String> altNicks = nicks.subList(1, nicks.size());

        ServerParameters serverParameters = new ServerParameters(
                serverName,
                primaryNick,
                altNicks,
                REAL_NAME,
                IDENTITY,
                IS_SSL_SERVER);

        ircApi.connect(serverParameters, createCallback(onSuccess, onError));
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

        ircApi.connect(serverParameters, createCallback(resultHandler));
    }

    private Callback<IIRCState> createCallback(ResultHandler<Connection> resultHandler) {
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

    private Callback<IIRCState> createCallback(Consumer<Connection> onSuccess, Runnable onError) {
        return new Callback<>() {
            @Override
            public void onSuccess(IIRCState state) {
                onSuccess.accept(toConnection(state));
            }

            @Override
            public void onFailure(Exception ex) {
                onError.run();
            }

            private Connection toConnection(IIRCState state) {
                return new Connection(state.getNickname(), state.getServer().getHostname());
            }
        };
    }
}
