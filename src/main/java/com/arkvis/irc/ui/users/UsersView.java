package com.arkvis.irc.ui.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class UsersView implements Initializable {

    @FXML
    private ListView<String> users;

    private final UsersViewModel viewModel;

    public UsersView() {
        viewModel = new UsersViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindViewModel();
    }

    private void bindViewModel() {
        users.setItems(viewModel.getUsers());
    }
}
