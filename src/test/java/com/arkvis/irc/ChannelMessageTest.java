package com.arkvis.irc;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testengines.channelmessage.TestChannelMessageEngine;
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
        ChannelEvent channelEvent = new ChannelEvent(channelName, sender, message);
        TestConsumer<ChannelEvent> messageConsumer = new TestConsumer<>();

        TestChannelMessageEngine engine = new TestChannelMessageEngine(channelEvent);
        IRCClient client = new IRCClient(engine);

        client.addChannelMessageListener(messageConsumer);
        engine.sendChannelMessage();

        assertEquals(message, messageConsumer.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessageSender_when_gettingChannelMessage() {
        ChannelEvent channelEvent = new ChannelEvent(channelName, sender, message);
        TestConsumer<ChannelEvent> messageConsumer = new TestConsumer<>();

        TestChannelMessageEngine engine = new TestChannelMessageEngine(channelEvent);
        IRCClient client = new IRCClient(engine);

        client.addChannelMessageListener(messageConsumer);
        engine.sendChannelMessage();

        assertEquals(sender, messageConsumer.getAccepted().getSender());
    }

}
