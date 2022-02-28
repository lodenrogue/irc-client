package com.arkvis.irc.joinchannel;

import com.arkvis.irc.TestConsumer;
import com.arkvis.irc.TestResultHandler;
import com.arkvis.irc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OtherJoinChannelTest {

    private String channelName;
    private String nickName;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        nickName = "TEST_NICK_NAME";
    }

    @Test
    void _should_returnCorrectNickName_when_anotherUserJoinsChannel() {
        TestSuccessfulOtherJoinChannelEngine engine = new TestSuccessfulOtherJoinChannelEngine();
        Server server = connectToServer(engine);

        TestResultHandler<Channel> userJoinChannelListener = new TestResultHandler<>();
        server.addUserJoinChannelListener(userJoinChannelListener);

        server.joinChannel(channelName);
        Channel channel = userJoinChannelListener.getAccepted();

        TestConsumer<User> otherUserJoinChannelListener = new TestConsumer<>();
        channel.addOtherUserJoinChannelListener(otherUserJoinChannelListener);
        engine.sendJoinEvent(new User(nickName));

        assertEquals(nickName, otherUserJoinChannelListener.getAccepted().getNickName());
    }

    @Test
    void should_returnCorrectChannelName_when_anotherUserJoinsChannel() {
        TestSuccessfulOtherJoinChannelEngine engine = new TestSuccessfulOtherJoinChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherJoinEvent> joinConsumer = new TestConsumer<>();
        client.addOtherUserJoinChannelListener(joinConsumer);

        engine.sendJoinEvent(new OtherJoinEvent(channelName, nickName));
        assertEquals(channelName, joinConsumer.getAccepted().getChannelName());
    }

    @Test
    void should_correctNickName_when_anotherUserJoinsChannel() {
        TestSuccessfulOtherJoinChannelEngine engine = new TestSuccessfulOtherJoinChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherJoinEvent> joinConsumer = new TestConsumer<>();
        client.addOtherUserJoinChannelListener(joinConsumer);

        engine.sendJoinEvent(new OtherJoinEvent(channelName, nickName));
        assertEquals(nickName, joinConsumer.getAccepted().getNickName());
    }

    private Server connectToServer(Engine engine) {
        TestResultHandler<Server> resultHandler = new TestResultHandler<>();
        Server.connect(
                engine,
                new Connection("TEST_SERVER", List.of("nick1", "nick2")),
                resultHandler);
        return resultHandler.getAccepted();
    }
}
