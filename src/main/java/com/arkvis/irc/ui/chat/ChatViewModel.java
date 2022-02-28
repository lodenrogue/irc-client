package com.arkvis.irc.ui.chat;

import com.arkvis.irc.model.*;
import com.arkvis.irc.ui.EventEmitter;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;
import java.util.function.Consumer;

public class ChatViewModel {
    private static final String SERVER_SENDER = "*";

    private final Map<String, String> chatViews;
    private String currentChatView;

    private final List<Consumer<String>> chatTextListeners;
    private final StringProperty nickName;
    private final StringProperty userInput;

    private final IRCClient client;
    private final EventEmitter eventEmitter;

    public ChatViewModel() {
        chatViews = new HashMap<>();
        chatTextListeners = new ArrayList<>();

        nickName = new SimpleStringProperty("");
        userInput = new SimpleStringProperty("");

        client = IRC.getClient();
        eventEmitter = EventEmitter.getInstance();
    }

    public void init() {
        client.addConnectionListener(createConnectionResultHandler());
        client.addUserJoinChannelListener(createUserJoinChannelResultHandler());
        client.addOtherJoinChannelListener(createOtherJoinChannelListener());
        client.addChannelMessageListener(createChannelMessageListener());
        client.addSendMessageListener(createSendMessageResultHandler());


        eventEmitter.registerSelectChannelListener(this::changeToView);
        connectToServer();
    }

    public void processUserInput() {
        String input = userInput.getValue();
        userInput.setValue("");

        if (input.startsWith("/join ")) {
            String[] inputSplit = input.split(" ");
            client.joinChannel(inputSplit[1]);
        } else {
            client.sendMessage(currentChatView, input);
        }
    }

    public StringProperty getUserInputProperty() {
        return userInput;
    }

    public StringProperty getNickNameProperty() {
        return nickName;
    }

    public void addUserInputChangeListener(Consumer<String> listener) {
        chatTextListeners.add(listener);
    }

    private void connectToServer() {
        String serverName = "irc.libera.chat";
        chatViews.put(serverName, "");
        currentChatView = serverName;

        updateChatText(currentChatView, SERVER_SENDER, "Connecting to server...");
        client.connect(serverName, Arrays.asList("nick", "altNick1", "altNick2"));
    }

    private ResultHandler<MessageEvent> createSendMessageResultHandler() {
        return new SimpleResultHandler<>(
                this::onSendMessageSuccess,
                () -> updateChatText(currentChatView, SERVER_SENDER, "Error sending message"));
    }

    private ResultHandler<UserJoinEvent> createUserJoinChannelResultHandler() {
        return new SimpleResultHandler<>(
                this::onUserJoinChannelSuccess,
                () -> updateChatText(currentChatView, SERVER_SENDER, "Error joining channel"));
    }

    private Consumer<OtherJoinEvent> createOtherJoinChannelListener() {
        return joinEvent -> updateChatText(
                joinEvent.getChannelName(),
                SERVER_SENDER,
                String.format("%s has joined", joinEvent.getNickName()));
    }

    private ResultHandler<ConnectionEvent> createConnectionResultHandler() {
        return new SimpleResultHandler<>(
                this::onConnectionSuccess,
                () -> updateChatText(currentChatView, SERVER_SENDER, "Error connecting to server"));
    }

    private Consumer<MessageEvent> createChannelMessageListener() {
        return messageEvent -> updateChatText(
                messageEvent.getChannelName(),
                messageEvent.getSenderNick(),
                messageEvent.getMessage());
    }

    private void onSendMessageSuccess(MessageEvent event) {
        updateChatText(event.getChannelName(), nickName.getValue(), event.getMessage());
    }

    private void onUserJoinChannelSuccess(UserJoinEvent joinEvent) {
        String channelName = joinEvent.getChannelName();
        chatViews.putIfAbsent(channelName, "");

        String message = String.format("Successfully joined channel %s", channelName);
        updateChatText(channelName, SERVER_SENDER, message);
        changeToView(channelName);
    }

    private void onConnectionSuccess(ConnectionEvent connectionEvent) {
        String serverName = connectionEvent.getServerName();
        chatViews.putIfAbsent(serverName, "");
        currentChatView = serverName;

        String message = String.format("Successfully connected to %s", serverName);
        updateChatText(currentChatView, SERVER_SENDER, message);
        updateNickName(connectionEvent.getNickName());
    }

    private void changeToView(String viewName) {
        currentChatView = viewName;
        String text = chatViews.get(currentChatView);
        chatTextListeners.forEach(listener -> listener.accept(text));
    }

    private void updateNickName(String newName) {
        Platform.runLater(() -> nickName.setValue(newName));
    }

    private void updateChatText(String viewName, String sender, String message) {
        String newText = String.format("%s%s : %s\n",
                getCurrentText(viewName),
                sender,
                message);

        chatViews.put(viewName, newText);
    }

    private String getCurrentText(String viewName) {
        String text = chatViews.get(viewName);
        return Objects.isNull(text) ? "" : text;
    }
}
