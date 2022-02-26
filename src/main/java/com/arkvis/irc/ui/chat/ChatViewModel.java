package com.arkvis.irc.ui.chat;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class ChatViewModel {
    private final StringProperty chatText;
    private final StringProperty nickName;
    private final StringProperty userInput;

    private final IRCClient client;

    public ChatViewModel() {
        chatText = new SimpleStringProperty("");
        nickName = new SimpleStringProperty("");
        userInput = new SimpleStringProperty("");
        client = IRC.getClient();
    }

    public void init() {
        client.registerConnectionListener(createConnectionResultHandler());
        client.registerJoinChannelListener(createJoinChannelResultHandler());

        appendToChatText("Connecting to server...");
        client.connect("irc.libera.chat", Arrays.asList("nick", "altNick1", "altNick2"));
    }

    public void processUserInput() {
        String input = userInput.getValue();
        userInput.setValue("");

        if (input.startsWith("/join ")) {
            String[] inputSplit = input.split(" ");
            client.joinChannel(inputSplit[1]);
        }
    }

    public StringProperty getChatTextProperty() {
        return chatText;
    }

    public StringProperty getUserInputProperty() {
        return userInput;
    }

    public StringProperty getNickNameProperty() {
        return nickName;
    }

    private ResultHandler<Channel> createJoinChannelResultHandler() {
        return new SimpleResultHandler<>(
                this::onJoinChannelSuccess,
                () -> appendToChatText("Error joining channel"));
    }

    private ResultHandler<Connection> createConnectionResultHandler() {
        return new SimpleResultHandler<>(
                this::onConnectionSuccess,
                () -> appendToChatText("Error connecting to server"));
    }

    private void onJoinChannelSuccess(Channel channel) {
        String message = String.format("Successfully joined channel %s", channel.getName());
        appendToChatText(message);
    }

    private void onConnectionSuccess(Connection connection) {
        String message = String.format("Successfully connected to %s", connection.getServerName());
        appendToChatText(message);
        updateNickName(connection.getNickName());
    }

    private void updateNickName(String newName) {
        Platform.runLater(() -> nickName.setValue(newName));
    }

    private void appendToChatText(String message) {
        String currentText = chatText.getValue();
        String newText = String.format("%s%s\n", currentText, message);
        Platform.runLater(() -> chatText.setValue(newText));
    }
}
