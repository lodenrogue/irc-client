package com.arkvis.irc;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.Engine;
import com.arkvis.irc.model.Server;

import java.util.List;

public class TestUtils {

    public static Server connectToServer(Engine engine) {
        TestResultHandler<Server> resultHandler = new TestResultHandler<>();
        Server.connect(
                engine,
                new Connection("TEST_SERVER", List.of("nick1", "nick2")),
                resultHandler);
        return resultHandler.getAccepted();
    }

    public static Channel joinChannel(Server server, String channelName) {
        TestResultHandler<Channel> joinChannelListener = new TestResultHandler<>();
        server.addUserJoinChannelListener(joinChannelListener);
        server.joinChannel(channelName);
        return joinChannelListener.getAccepted();
    }
}
