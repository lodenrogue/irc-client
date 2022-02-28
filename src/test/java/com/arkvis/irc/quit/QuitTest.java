package com.arkvis.irc.quit;

import com.arkvis.irc.TestConsumer;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.OtherQuitEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuitTest {

    private String nickName;
    private String message;

    @BeforeEach
    void setUp() {
        nickName = "TEST_NICK";
        message = "This is a message";
    }

    @Test
    void should_returnCorrectNickName_when_userQuits() {
        TestOtherUserQuitEngine engine = new TestOtherUserQuitEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherQuitEvent> listener = new TestConsumer<>();
        client.addOtherUserQuitListener(listener);

        engine.sendQuitEvent(new OtherQuitEvent(nickName));
        assertEquals(nickName, listener.getAccepted().getNickName());
    }

    @Test
    void should_returnCorrectQuitMessage_when_userQuits() {
        TestOtherUserQuitEngine engine = new TestOtherUserQuitEngine();
        IRCClient client = new IRCClient(engine);

        TestConsumer<OtherQuitEvent> listener = new TestConsumer<>();
        client.addOtherUserQuitListener(listener);

        engine.sendQuitEvent(new OtherQuitEvent(nickName, message));
        assertEquals(message, listener.getAccepted().getMessage());
    }
}
