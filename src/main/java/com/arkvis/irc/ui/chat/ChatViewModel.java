package com.arkvis.irc.ui.chat;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.IRCClient;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.EventEmitter;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChatViewModel {
    private final Map<String, String> chatViews;
    private String currentChatView;

    private final StringProperty chatText;
    private final StringProperty nickName;
    private final StringProperty userInput;

    private final IRCClient client;
    private final EventEmitter eventEmitter;

    public ChatViewModel() {
        chatViews = new HashMap<>();

        chatText = new SimpleStringProperty("");
        nickName = new SimpleStringProperty("");
        userInput = new SimpleStringProperty("");

        client = IRC.getClient();
        eventEmitter = EventEmitter.getInstance();
    }

    public void init() {
        client.registerConnectionListener(createConnectionResultHandler());
        client.registerJoinChannelListener(createJoinChannelResultHandler());

        eventEmitter.registerSelectViewListener(this::changeToView);
        connectToServer();
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

    private void connectToServer() {
        String serverName = "irc.libera.chat";
        chatViews.put(serverName, "");
        currentChatView = serverName;

        updateChatText("Connecting to server...");
        client.connect(serverName, Arrays.asList("nick", "altNick1", "altNick2"));
    }

    private ResultHandler<Channel> createJoinChannelResultHandler() {
        return new SimpleResultHandler<>(
                this::onJoinChannelSuccess,
                () -> updateChatText("Error joining channel"));
    }

    private ResultHandler<Connection> createConnectionResultHandler() {
        return new SimpleResultHandler<>(
                this::onConnectionSuccess,
                () -> updateChatText("Error connecting to server"));
    }

    private void onJoinChannelSuccess(Channel channel) {
        String channelName = channel.getName();
        chatViews.putIfAbsent(channelName, "");
        currentChatView = channelName;

        String message = String.format("Successfully joined channel %s", channelName);
        updateChatText(message);
    }

    private void onConnectionSuccess(Connection connection) {
        String serverName = connection.getServerName();
        chatViews.putIfAbsent(serverName, "");
        currentChatView = serverName;

        String message = String.format("Successfully connected to %s", serverName);
        updateChatText(message);
        updateNickName(connection.getNickName());
    }

    private void changeToView(String viewName) {
        currentChatView = viewName;
        String text = chatViews.get(currentChatView);
        Platform.runLater(() -> chatText.setValue(text));
    }

    private void updateNickName(String newName) {
        Platform.runLater(() -> nickName.setValue(newName));
    }

    private void updateChatText(String message) {
        String currentText = chatViews.get(currentChatView);
        String newText = String.format("%s%s\n", currentText, message);
        chatViews.put(currentChatView, newText);
        Platform.runLater(() -> chatText.setValue(newText));
    }

    private void appendToChatText(String message) {
        String currentText = chatText.getValue();
        String newText = String.format("%s%s\n", currentText, message);
        Platform.runLater(() -> chatText.setValue(newText));
    }
}
