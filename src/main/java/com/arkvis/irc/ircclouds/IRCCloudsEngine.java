package com.arkvis.irc.ircclouds;

import com.arkvis.irc.model.*;
import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.IRCUser;
import com.ircclouds.irc.api.domain.messages.ChanJoinMessage;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.listeners.VariousMessageListenerAdapter;
import com.ircclouds.irc.api.state.IIRCState;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class IRCCloudsEngine implements Engine {
    private static final String REAL_NAME = "IRC Api";
    private static final String IDENTITY = "ident";
    private static final boolean IS_SSL_SERVER = true;

    private final IRCApi ircApi;

    public IRCCloudsEngine() {
        ircApi = new IRCApiImpl(true);
    }

    @Override
    public void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler) {
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
    public void joinChannel(String channelName, ResultHandler<ChannelEvent> resultHandler) {
        ircApi.joinChannel(channelName, createJoinChannelCallback(resultHandler));
    }

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<ChannelEvent> resultHandler) {
        ircApi.message(channelName, message, createSendMessageCallback(channelName, message, resultHandler));
    }

    @Override
    public void addChannelMessageListener(Consumer<ChannelEvent> listener) {
        ircApi.addListener(new VariousMessageListenerAdapter() {
            @Override
            public void onChannelMessage(ChannelPrivMsg message) {
                listener.accept(toChannel(message));
            }
        });
    }

    @Override
    public void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener) {
        ircApi.addListener(new VariousMessageListenerAdapter() {
            @Override
            public void onChannelJoin(ChanJoinMessage message) {
                listener.accept(toOtherJoinEvent(message));
            }
        });

    }

    private Callback<String> createSendMessageCallback(String channelName, String message, ResultHandler<ChannelEvent> resultHandler) {
        return createCallback(
                response -> resultHandler.onSuccess(new ChannelEvent(channelName, null, message)),
                resultHandler::onError);
    }

    private Callback<IRCChannel> createJoinChannelCallback(ResultHandler<ChannelEvent> resultHandler) {
        return createCallback(
                ircChannel -> resultHandler.onSuccess(toChannel(ircChannel)),
                resultHandler::onError);
    }

    private Callback<IIRCState> createConnectCallback(ResultHandler<ConnectionEvent> resultHandler) {
        return createCallback(
                state -> resultHandler.onSuccess(toConnection(state)),
                resultHandler::onError);
    }

    private <T> Callback<T> createCallback(Consumer<T> onSuccess, Runnable onError) {
        return new Callback<>() {
            @Override
            public void onSuccess(T t) {
                onSuccess.accept(t);
            }

            @Override
            public void onFailure(Exception exception) {
                onError.run();
            }
        };
    }

    private ChannelEvent toChannel(IRCChannel ircChannel) {
        List<String> users = ircChannel.getUsers().stream()
                .map(IRCUser::getNick)
                .collect(Collectors.toList());

        return new ChannelEvent(ircChannel.getName(), users, null, null);
    }

    private ChannelEvent toChannel(ChannelPrivMsg message) {
        IRCUser sender = message.getSource();
        String senderNick = sender.getNick();
        return new ChannelEvent(message.getChannelName(), senderNick, message.getText());
    }

    private ConnectionEvent toConnection(IIRCState state) {
        return new ConnectionEvent(state.getNickname(), state.getServer().getHostname());
    }

    private OtherJoinEvent toOtherJoinEvent(ChanJoinMessage message) {
        return new OtherJoinEvent(message.getChannelName(), message.getSource().getNick());
    }
}
