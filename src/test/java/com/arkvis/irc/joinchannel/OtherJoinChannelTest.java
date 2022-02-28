package com.arkvis.irc.joinchannel;

import com.arkvis.irc.TestConsumer;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.OtherJoinEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void should_correctChannelName_when_anotherUserJoinsChannel() {
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
}
