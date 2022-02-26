package com.arkvis.irc.ui.channels;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChannelsView implements Initializable {

    @FXML
    private ListView<String> serversList;

    private final ChannelsViewModel viewModel;

    public ChannelsView() {
        viewModel = new ChannelsViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.addServersChangeListener(this::updateServers);
    }

    private void updateServers(List<Server> servers) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Server server : servers) {
            items.add(server.getName());
            items.addAll(server.getChannels());
        }
        Platform.runLater(() -> serversList.setItems(items));
    }
}
