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
    private String senderNick;
    private String messageText;

    @BeforeEach
    void setUp() {
        channelName = "TEST_CHANNEL";
        senderNick = "TEST_SENDER";
        messageText = "This is a message";
    }

    @Test
    void _should_returnCorrectMessage_when_receivingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        Server server = TestUtils.connectToServer(engine);
        Channel channel = TestUtils.joinChannel(server, channelName);

        TestConsumer<Message> messageListener = new TestConsumer<>();
        channel.addReceiveMessageListener(messageListener);

        Message message = new Message(new User(senderNick), messageText);
        engine.sendChannelMessage(message);
        assertEquals(message.getMessage(), messageListener.getAccepted().getMessage());
    }

    @Test
    void _should_returnCorrectSender_when_receivingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        Server server = TestUtils.connectToServer(engine);
        Channel channel = TestUtils.joinChannel(server, channelName);

        TestConsumer<Message> messageListener = new TestConsumer<>();
        channel.addReceiveMessageListener(messageListener);

        User sender = new User(senderNick);
        engine.sendChannelMessage(new Message(sender, messageText));
        assertEquals(senderNick, messageListener.getAccepted().getSender().getNickName());
    }

    @Test
    void _should_returnCorrectMessage_when_sendingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        Server server = TestUtils.connectToServer(engine);
        Channel channel = TestUtils.joinChannel(server, channelName);

        Message message = new Message(new User(senderNick), messageText);
        TestResultHandler<Message> messageHandler = new TestResultHandler<>();
        channel.sendMessage(message, messageHandler);

        assertEquals(messageText, messageHandler.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessage_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<MessageEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        MessageEvent messageEvent = new MessageEvent(channelName, senderNick, messageText);
        engine.sendChannelMessage(messageEvent);
        assertEquals(messageText, messageConsumer.getAccepted().getMessage());
    }

    @Test
    void should_returnCorrectMessageSender_when_gettingChannelMessage() {
        TestChannelMessageEngine engine = new TestChannelMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<MessageEvent> messageConsumer = new TestConsumer<>();
        client.addChannelMessageListener(messageConsumer);

        MessageEvent messageEvent = new MessageEvent(channelName, senderNick, messageText);
        engine.sendChannelMessage(messageEvent);
        assertEquals(senderNick, messageConsumer.getAccepted().getSenderNick());
    }

    @Test
    void should_returnCorrectMessage_when_sendingChannelMessage() {
        TestSuccessfulSendMessageEngine engine = new TestSuccessfulSendMessageEngine();
        IRCClient client = new IRCClient(engine);

        TestResultHandler<MessageEvent> resultHandler = new TestResultHandler<>();
        client.addSendMessageListener(resultHandler);

        client.sendMessage(channelName, messageText);
        assertEquals(messageText, resultHandler.getAccepted().getMessage());
    }
}
