package com.arkvis.irc.ui.chat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ChatView implements Initializable {

    @FXML
    private TextArea chatText;

    @FXML
    private Label nickName;

    @FXML
    private TextField userInput;

    private final ChatViewModel viewModel;

    public ChatView() {
        viewModel = new ChatViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindViewModel();
        viewModel.init();
        userInput.setOnKeyReleased(createInputKeyHandler());
        chatText.textProperty().addListener(createTextChangeListener());
    }

    private void bindViewModel() {
        viewModel.addUserInputChangeListener(createUserInputChangeListener());
        nickName.textProperty().bindBidirectional(viewModel.getNickNameProperty());
        userInput.textProperty().bindBidirectional(viewModel.getUserInputProperty());
    }

    private Consumer<String> createUserInputChangeListener() {
        return text -> Platform.runLater(() -> {
            chatText.setText(text);
            chatText.appendText("");
            chatText.setScrollTop(Double.MAX_VALUE);
        });
    }

    private ChangeListener<String> createTextChangeListener() {
        return (observable, oldValue, newValue) -> {
            chatText.appendText("");
            chatText.setScrollTop(Double.MAX_VALUE);
        };
    }

    private EventHandler<KeyEvent> createInputKeyHandler() {
        return event -> {
            if (event.getCode() == KeyCode.ENTER) {
                viewModel.processUserInput();
            }
        };
    }
}
