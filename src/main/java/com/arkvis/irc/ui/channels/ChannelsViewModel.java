package com.arkvis.irc.ui.channels;

import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.IRC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChannelsViewModel {
    private final ObservableList<String> servers;

    public ChannelsViewModel() {
        servers = FXCollections.observableArrayList();
        IRC.getClient().registerConnectionListener(createConnectionResultHandler());
    }

    public ObservableList<String> getServers() {
        return servers;
    }

    private ResultHandler<Connection> createConnectionResultHandler() {
        return new ResultHandler<>() {
            @Override
            public void onSuccess(Connection connection) {
                servers.add(connection.getServerName());
            }

            @Override
            public void onError() {
            }
        };
    }
}
