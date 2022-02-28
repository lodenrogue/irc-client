package com.arkvis.irc.model;

import java.util.List;
import java.util.function.Consumer;

public interface Engine {

    void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler);

    void connect(Connection connection, ResultHandler<Server> resultHandler);

    void joinChannel(String channelName, ResultHandler<UserJoinEvent> resultHandler);

    void _joinChannel(String channelName, ResultHandler<Channel> resultHandler);

    void addChannelMessageListener(Consumer<MessageEvent> listener);

    void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener);

    void addOtherLeaveChannelListener(Consumer<OtherLeaveEvent> listener);

    void _addOtherJoinChannelListener(String channelName, Consumer<User> listener);

    void addOtherQuitListener(Consumer<OtherQuitEvent> listener);

    void sendMessage(String channelName, String message, ResultHandler<MessageEvent> resultHandler);
}
