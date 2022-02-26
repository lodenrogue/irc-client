package com.arkvis.irc.ui.chat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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
    }

    private void bindViewModel() {
        chatText.textProperty().bindBidirectional(viewModel.getChatTextProperty());
        nickName.textProperty().bindBidirectional(viewModel.getNickNameProperty());
        userInput.textProperty().bindBidirectional(viewModel.getUserInputProperty());
    }
}
