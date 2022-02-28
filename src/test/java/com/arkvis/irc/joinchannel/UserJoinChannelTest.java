package com.arkvis.irc.joinchannel;

import com.arkvis.irc.TestResultHandler;
import com.arkvis.irc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
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
    void _should_returnCorrectChannelName_when_successfullyJoiningChannel() {
        Server server = connectToServer(new TestSuccessfulJoinChannelEngine(Collections.emptyList()));
        TestResultHandler<Channel> resultHandler = new TestResultHandler<>();
        server.addUserJoinChannelListener(resultHandler);

        server.joinChannel(channelName);
        assertEquals(channelName, resultHandler.getAccepted().getName());
    }

    @Test
    void _should_returnUsersList_when_successfullyJoiningChannel() {
        List<User> users = List.of(new User("USER1"), new User("USER2"));

        Server server = connectToServer(new TestSuccessfulJoinChannelEngine(users));
        TestResultHandler<Channel> resultHandler = new TestResultHandler<>();
        server.addUserJoinChannelListener(resultHandler);

        server.joinChannel(channelName);
        assertEquals(users, resultHandler.getAccepted().getUsers());
    }

    @Test
    void _should_adviseOfError_when_joiningChannelFailed() {
        Server server = connectToServer(new TestFailedJoinChannelEngine());
        TestResultHandler<Channel> resultHandler = new TestResultHandler<>();
        server.addUserJoinChannelListener(resultHandler);

        server.joinChannel(channelName);
        assertFalse(resultHandler.wasOnSuccessCalled());
        assertTrue(resultHandler.wasOnErrorCalled());
    }

    @Test
    void _should_returnCorrectChannels_when_successfullyJoiningChannel() {
        String firstChannel = "FIRST_CHANNEL";
        String secondChannel = "SECOND_CHANNEL";

        Server server = connectToServer(new TestSuccessfulJoinChannelEngine(Collections.emptyList()));
        server.joinChannel(firstChannel);
        server.joinChannel(secondChannel);

        assertEquals(firstChannel, server.getChannels().get(0).getName());
        assertEquals(secondChannel, server.getChannels().get(1).getName());
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

    private Server connectToServer(Engine engine) {
        TestResultHandler<Server> resultHandler = new TestResultHandler<>();
        Server.connect(
                engine,
                new Connection("TEST_SERVER", List.of("nick1", "nick2")),
                resultHandler);
        return resultHandler.getAccepted();
    }
}
