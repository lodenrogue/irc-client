package com.arkvis.irc;

import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.UserJoinEvent;
import com.arkvis.irc.testengines.joinchannel.TestFailedJoinChannelEngine;
import com.arkvis.irc.testengines.joinchannel.TestSuccessfulJoinChannelEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserJoinChannelTest {

    private String channelName;
    private List<String> users;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        users = List.of("Tom", "Sally", "Tami");
    }

    @Test
    void should_returnCorrectChannelName_when_successfullyJoiningChannel() {
        UserJoinEvent joinEvent = new UserJoinEvent(channelName, users);

        IRCClient ircClient = new IRCClient(new TestSuccessfulJoinChannelEngine(joinEvent));
        TestResultHandler<UserJoinEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(channelName, resultHandler.getAccepted().getChannelName());
    }

    @Test
    void should_returnUsersList_when_successfullyJoiningChannel() {
        UserJoinEvent joinEvent = new UserJoinEvent(channelName, users);
        IRCClient ircClient = new IRCClient(new TestSuccessfulJoinChannelEngine(joinEvent));

        TestResultHandler<UserJoinEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertEquals(users, resultHandler.getAccepted().getUsers());
    }

    @Test
    void should_adviseOfError_when_joiningChannelFailed() {
        IRCClient ircClient = new IRCClient(new TestFailedJoinChannelEngine());
        TestResultHandler<UserJoinEvent> resultHandler = new TestResultHandler<>();
        ircClient.addUserJoinChannelListener(resultHandler);

        ircClient.joinChannel(channelName);
        assertFalse(resultHandler.wasOnSuccessCalled());
        assertTrue(resultHandler.wasOnErrorCalled());
    }
}
