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
    private String partingMessage;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        nickName = "TEST_NICK_NAME";
        partingMessage = "Hasta la vista, baby";
    }

    @Test
    void should_returnCorrectChannelName_when_otherUserLeavesChannel() {
        TestOtherUserLeaveChannelEngine engine = new TestOtherUserLeaveChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherLeaveEvent> listener = new TestConsumer<>();
        client.addOtherUserLeaveChannelListener(listener);

        engine.sendLeaveChannelEvent(new OtherLeaveEvent(channelName, nickName, partingMessage));
        assertEquals(channelName, listener.getAccepted().getChannelName());
    }

    @Test
    void should_returnCorrectNickName_when_otherUserLeavesChannel() {
        TestOtherUserLeaveChannelEngine engine = new TestOtherUserLeaveChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherLeaveEvent> listener = new TestConsumer<>();
        client.addOtherUserLeaveChannelListener(listener);

        engine.sendLeaveChannelEvent(new OtherLeaveEvent(channelName, nickName, partingMessage));
        assertEquals(nickName, listener.getAccepted().getNickName());
    }

    @Test
    void should_returnCorrectPartingMessage_when_otherUserLeavesChannel() {
        TestOtherUserLeaveChannelEngine engine = new TestOtherUserLeaveChannelEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherLeaveEvent> listener = new TestConsumer<>();
        client.addOtherUserLeaveChannelListener(listener);

        engine.sendLeaveChannelEvent(new OtherLeaveEvent(channelName, nickName, partingMessage));
        assertEquals(partingMessage, listener.getAccepted().getPartingMessage());
    }
}
