package com.arkvis.irc.ircclouds;

import com.arkvis.irc.model.*;
import com.ircclouds.irc.api.Callback;
import com.ircclouds.irc.api.IRCApi;
import com.ircclouds.irc.api.IRCApiImpl;
import com.ircclouds.irc.api.domain.IRCChannel;
import com.ircclouds.irc.api.domain.IRCUser;
import com.ircclouds.irc.api.domain.messages.ChanJoinMessage;
import com.ircclouds.irc.api.domain.messages.ChanPartMessage;
import com.ircclouds.irc.api.domain.messages.ChannelPrivMsg;
import com.ircclouds.irc.api.domain.messages.QuitMessage;
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
    public void connect(Connection connection, ResultHandler<Server> resultHandler) {
        String primaryNick = connection.getNicks().get(0);
        List<String> altNicks = connection.getNicks().subList(1, connection.getNicks().size());

        ServerParameters serverParameters = new ServerParameters(
                connection.getServerName(),
                primaryNick,
                altNicks,
                REAL_NAME,
                IDENTITY,
                IS_SSL_SERVER);

        ircApi.connect(serverParameters, _createConnectCallback(resultHandler));
    }

    @Override
    public void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler) {
        ircApi.joinChannel(channelName, createUserJoinChannelCallback(resultHandler));
    }

    @Override
    public void sendMessage(String channelName, String message, ResultHandler<MessageEvent> resultHandler) {
        ircApi.message(channelName, message, createSendMessageCallback(channelName, message, resultHandler));
    }

    @Override
    public void addChannelMessageListener(Consumer<MessageEvent> listener) {
        ircApi.addListener(new VariousMessageListenerAdapter() {
            @Override
            public void onChannelMessage(ChannelPrivMsg message) {
                listener.accept(toMessageEvent(message));
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

    @Override
    public void addOtherLeaveChannelListener(Consumer<OtherLeaveEvent> listener) {
        ircApi.addListener(new VariousMessageListenerAdapter() {
            @Override
            public void onChannelPart(ChanPartMessage message) {
                listener.accept(toOtherLeaveEvent(message));
            }
        });
    }

    @Override
    public void addOtherQuitListener(Consumer<OtherQuitEvent> listener) {
        ircApi.addListener(new VariousMessageListenerAdapter() {
            @Override
            public void onUserQuit(QuitMessage message) {
                listener.accept(toOtherQuitEvent(message));
            }
        });
    }

    private Callback<String> createSendMessageCallback(String channelName, String message, ResultHandler<MessageEvent> resultHandler) {
        return createCallback(
                response -> resultHandler.onSuccess(new MessageEvent(channelName, message)),
                resultHandler::onError);
    }

    private Callback<IRCChannel> createUserJoinChannelCallback(ResultHandler<UserJoinEvent> resultHandler) {
        return createCallback(
                ircChannel -> resultHandler.onSuccess(toUserJoinEvent(ircChannel)),
                resultHandler::onError);
    }

    private Callback<IIRCState> createConnectCallback(ResultHandler<ConnectionEvent> resultHandler) {
        return createCallback(
                state -> resultHandler.onSuccess(toConnection(state)),
                resultHandler::onError);
    }

    private Callback<IIRCState> _createConnectCallback(ResultHandler<Server> resultHandler) {
        return createCallback(
                state -> resultHandler.onSuccess(toServer(state)),
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

    private UserJoinEvent toUserJoinEvent(IRCChannel ircChannel) {
        List<String> users = ircChannel.getUsers().stream()
                .map(IRCUser::getNick)
                .collect(Collectors.toList());

        return new UserJoinEvent(ircChannel.getName(), users);
    }

    private MessageEvent toMessageEvent(ChannelPrivMsg message) {
        IRCUser sender = message.getSource();
        String senderNick = sender.getNick();
        return new MessageEvent(message.getChannelName(), senderNick, message.getText());
    }

    private ConnectionEvent toConnection(IIRCState state) {
        return new ConnectionEvent(state.getNickname(), state.getServer().getHostname());
    }

    private Server toServer(IIRCState state) {
        User user = new User(state.getNickname());
        return new Server(state.getServer().getHostname(), user);
    }

    private OtherJoinEvent toOtherJoinEvent(ChanJoinMessage message) {
        return new OtherJoinEvent(message.getChannelName(), message.getSource().getNick());
    }

    private OtherLeaveEvent toOtherLeaveEvent(ChanPartMessage message) {
        return new OtherLeaveEvent(
                message.getChannelName(),
                message.getSource().getNick(),
                message.getPartMsg());
    }

    private OtherQuitEvent toOtherQuitEvent(QuitMessage message) {
        return new OtherQuitEvent(message.getSource().getNick(), message.getQuitMsg());
    }
}
