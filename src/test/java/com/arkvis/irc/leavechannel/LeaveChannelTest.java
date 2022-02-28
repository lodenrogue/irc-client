package com.arkvis.irc.leavechannel;

import com.arkvis.irc.TestConsumer;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.OtherLeaveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaveChannelTest {

    private String channelName;
    private String nickName;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        nickName = "TEST_NICK_NAME";
    }

    @Test
    void should_returnCorrectChannelName_when_otherUserLeavesChannel() {
        TestOtherUserLeaveChannelEngine engine = new TestOtherUserLeaveChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherLeaveEvent> listener = new TestConsumer<>();
        client.addOtherUserLeaveChannelListener(listener);

        engine.sendLeaveChannelEvent(new OtherLeaveEvent(channelName, nickName));
        assertEquals(channelName, listener.getAccepted().getChannelName());
    }

    @Test
    void should_returnCorrectNickName_when_otherUserLeavesChannel() {
        TestOtherUserLeaveChannelEngine engine = new TestOtherUserLeaveChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherLeaveEvent> listener = new TestConsumer<>();
        client.addOtherUserLeaveChannelListener(listener);

        engine.sendLeaveChannelEvent(new OtherLeaveEvent(channelName, nickName));
        assertEquals(nickName, listener.getAccepted().getNickName());
    }
}
