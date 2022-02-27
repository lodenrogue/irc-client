package com.arkvis.irc;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testengines.channelmessage.TestChannelMessageEngine;
import com.arkvis.irc.testengines.channelmessage.TestSuccessfulSendMessageEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelMessageTest {

    private String channelName;
    private String sender;
    private String message;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        sender = "TEST_SENDER";
        message = "This is a message";
    }

    @Test
    void should_returnCorrectMessage_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<ChannelEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        ChannelEvent channelEvent = new ChannelEvent(channelName, sender, message);
        engine.sendChannelMessage(channelEvent);
        assertEquals(message, messageConsumer.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessageSender_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<ChannelEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        ChannelEvent channelEvent = new ChannelEvent(channelName, sender, message);
        engine.sendChannelMessage(channelEvent);
        assertEquals(sender, messageConsumer.getAccepted().getSender());
    }

    @Test
    void should_returnCorrectMessage_when_sendingChannelMessage() {
        TestSuccessfulSendMessageEngine engine = new TestSuccessfulSendMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestResultHandler<ChannelEvent> resultHandler = new TestResultHandler<>();
        client.addSendMessageListener(resultHandler);

        client.sendMessage(channelName, message);
        assertEquals(message, resultHandler.getAccepted().getMessage());
    }

}