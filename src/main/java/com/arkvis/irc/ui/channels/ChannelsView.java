package com.arkvis.irc.ui.channels;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChannelsView implements Initializable {

    @FXML
    private ListView<String> servers;

    private final ChannelsViewModel viewModel;

    public ChannelsView() {
        viewModel = new ChannelsViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindViewModel();
    }

    private void bindViewModel() {
        servers.setItems(viewModel.getServers());
    }
}
