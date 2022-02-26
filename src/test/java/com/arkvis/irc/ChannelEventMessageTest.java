package com.arkvis.irc;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.testengines.channelmessage.TestChannelMessageEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelEventMessageTest {

    @Test
    void should_returnCorrectMessage_when_gettingChannelMessage() {
        String message = "This is a message";
        ChannelEvent channelEvent = new ChannelEvent("TEST_CHANNEL", message);
        TestConsumer<ChannelEvent> messageConsumer = new TestConsumer<>();

        TestChannelMessageEngine engine = new TestChannelMessageEngine(channelEvent);
        IRCClient client = new IRCClient(engine);

        client.addChannelMessageListener(messageConsumer);
        engine.sendChannelMessage();

        assertEquals(message, messageConsumer.getAccepted().getMessage());
    }

}
