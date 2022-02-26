package com.arkvis.irc.model;

import java.util.List;
import java.util.function.Consumer;

public interface Engine {

    void connect(String serverName, List<String> nicks, Consumer<Connection> onSuccess, Runnable onError);

    void connect(String serverName, List<String> nicks, ResultHandler<Connection> resultHandler);
}
