package com.arkvis.irc;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testengines.joinchannel.TestFailedJoinChannelEngine;
import com.arkvis.irc.testengines.joinchannel.TestSuccessfulJoinChannelEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinChannelTest {

    @Test
    void should_returnCorrectChannelName_when_successfullyJoiningChannel() {
        String channelName = "TEST_CHANNEL";
        ChannelEvent channelEvent = new ChannelEvent(channelName, null);

        IRCClient ircClient = new IRCClient(new TestSuccessfulJoinChannelEngine(channelEvent));
        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        ircClient.addJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(channelName, resultHandler.getAccepted().getName());
    }

    @Test
    void should_adviseOfError_when_joiningChannelFailed() {
        IRCClient ircClient = new IRCClient(new TestFailedJoinChannelEngine());
        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        ircClient.addJoinChannelListener(resultHandler);

        ircClient.joinChannel("test123");
        assertFalse(resultHandler.wasOnSuccessCalled());
        assertTrue(resultHandler.wasOnErrorCalled());
    }
}
