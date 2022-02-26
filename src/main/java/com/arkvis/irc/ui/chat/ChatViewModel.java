package com.arkvis.irc.ui.chat;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.IRC;
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
        appendToChatText("Connecting to server...");
        ResultHandler<Connection> resultHandler = createConnectionResultHandler();

        client.registerConnectionListener(resultHandler);
        client.connect("irc.libera.chat", Arrays.asList("nick", "altNick1", "altNick2"));
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

    private ResultHandler<Connection> createConnectionResultHandler() {
        return new ResultHandler<>() {
            @Override
            public void onSuccess(Connection connection) {
                onConnectionSuccess(connection);
            }

            @Override
            public void onError() {
                appendToChatText("Error connecting to server");
            }
        };
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
