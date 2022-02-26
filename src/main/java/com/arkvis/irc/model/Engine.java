package com.arkvis.irc.model;

import java.util.List;

public interface Engine {

    void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler);

    void joinChannel(String channelName, ResultHandler<Channel> resultHandler);
}
