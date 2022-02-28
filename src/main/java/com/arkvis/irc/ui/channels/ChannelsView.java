package com.arkvis.irc.ui.channels;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        serversList.getSelectionModel().selectedItemProperty().addListener(createSelectItemListener());
    }

    private ChangeListener<String> createSelectItemListener() {
        return (observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                viewModel.selectChannel(newValue);
            }
        };
    }

    private void updateServers(List<Server> servers) {
        Server server = servers.get(0);
        List<String> channels = new ArrayList<>(server.getChannels());

        String selection = selectLastItemOrDefault(channels, server.getName());
        channels.sort(String::compareToIgnoreCase);

        ObservableList<String> items = createItems(server, channels);
        Platform.runLater(() -> setServerItems(items, selection));
    }

    private String selectLastItemOrDefault(List<String> channels, String defaultValue) {
        return channels.size() > 0 ? channels.get(channels.size() - 1) : defaultValue;
    }

    private ObservableList<String> createItems(Server server, List<String> channels) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add(server.getName());
        items.addAll(channels);
        return items;
    }

    private void setServerItems(ObservableList<String> items, String selection) {
        serversList.setItems(items);
        serversList.getSelectionModel().select(selection);
    }
}
