package com.arkvis.irc;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testengines.joinchannel.TestFailedJoinChannelEngine;
import com.arkvis.irc.testengines.joinchannel.TestSuccessfulJoinChannelEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserJoinChannelTest {

    private String channelName;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
    }

    @Test
    void should_returnCorrectChannelName_when_successfullyJoiningChannel() {
        ChannelEvent channelEvent = new ChannelEvent(channelName, null, null);

        IRCClient ircClient = new IRCClient(new TestSuccessfulJoinChannelEngine(channelEvent));
        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(channelName, resultHandler.getAccepted().getName());
    }

    @Test
    void should_returnUsersList_when_successfullyJoiningChannel() {
        List<String> users = List.of("Tom", "Sally", "Tami");

        ChannelEvent channelEvent = new ChannelEvent(
                channelName,
                users,
                null,
                null);

        IRCClient ircClient = new IRCClient(new TestSuccessfulJoinChannelEngine(channelEvent));
        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(users, resultHandler.getAccepted().getUsers());
    }

    @Test
    void should_adviseOfError_when_joiningChannelFailed() {
        IRCClient ircClient = new IRCClient(new TestFailedJoinChannelEngine());
        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel("test123");
        assertFalse(resultHandler.wasOnSuccessCalled());
        assertTrue(resultHandler.wasOnErrorCalled());
    }
}
