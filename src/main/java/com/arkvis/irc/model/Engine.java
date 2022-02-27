package com.arkvis.irc.model;

import java.util.List;
import java.util.function.Consumer;

public interface Engine {

    void connect(String serverName, List<String> nicks, ResultHandler<ConnectionEvent> resultHandler);

    void joinChannel(String channelName, ResultHandler<ChannelEvent> resultHandler);

    void addChannelMessageListener(Consumer<MessageEvent> listener);

    void addOtherJoinChannelListener(Consumer<OtherJoinEvent> listener);

    void sendMessage(String channelName, String message, ResultHandler<ChannelEvent> resultHandler);
}
