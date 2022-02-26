package com.arkvis.irc;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testhelper.TestFailedChannelJoinEngine;
import com.arkvis.irc.testhelper.TestResultHandler;
import com.arkvis.irc.testhelper.TestSuccessfulChannelJoinEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinChannelTest {

    @Test
    void should_returnCorrectChannelName_when_successfullyJoiningChannel() {
        String channelName = "TEST_CHANNEL";
        Channel channel = new Channel(channelName);

        IRCClient ircClient = new IRCClient(new TestSuccessfulChannelJoinEngine(channel));
        TestResultHandler<Channel> resultHandler = new TestResultHandler<>();
        ircClient.registerJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(channelName, resultHandler.getAccepted().getName());
    }

    @Test
    void should_adviseOfError_when_joiningChannelFailed() {
        IRCClient ircClient = new IRCClient(new TestFailedChannelJoinEngine());
        TestResultHandler<Channel> resultHandler = new TestResultHandler<>();
        ircClient.registerJoinChannelListener(resultHandler);

        ircClient.joinChannel("test123");
        assertFalse(resultHandler.wasOnSuccessCalled());
        assertTrue(resultHandler.wasOnErrorCalled());
    }
}
