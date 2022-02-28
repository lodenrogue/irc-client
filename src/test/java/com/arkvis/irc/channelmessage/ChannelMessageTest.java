package com.arkvis.irc.channelmessage;

import com.arkvis.irc.TestConsumer;
import com.arkvis.irc.TestResultHandler;
import com.arkvis.irc.TestUtils;
import com.arkvis.irc.model.*;
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
    void _should_returnCorrectMessage_when_receivingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        Server server = TestUtils.connectToServer(engine);
        Channel channel = TestUtils.joinChannel(server, channelName);

        TestConsumer<Message> messageListener = new TestConsumer<>();
        channel.addReceiveMessageListener(messageListener);
        engine.sendChannelMessage(new Message(message));

        assertEquals(message, messageListener.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessage_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<MessageEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        MessageEvent messageEvent = new MessageEvent(channelName, sender, message);
        engine.sendChannelMessage(messageEvent);
        assertEquals(message, messageConsumer.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessage_when_sendingChannelMessage() {
        TestSuccessfulSendMessageEngine engine = new TestSuccessfulSendMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestResultHandler<MessageEvent> resultHandler = new TestResultHandler<>();
        client.addSendMessageListener(resultHandler);

        client.sendMessage(channelName, message);
        assertEquals(message, resultHandler.getAccepted().getMessage());
    }


    @Test
    void should_returnCorrectMessageSender_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<MessageEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        MessageEvent messageEvent = new MessageEvent(channelName, sender, message);
        engine.sendChannelMessage(messageEvent);
        assertEquals(sender, messageConsumer.getAccepted().getSenderNick());
    }
}
