package com.arkvis.irc.ui.channels;

import com.arkvis.irc.model.Channel;
import com.arkvis.irc.model.Connection;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ChannelsViewModel {
    private Server currentServer;
    private final List<Server> servers;
    private final List<Consumer<List<Server>>> listeners;

    public ChannelsViewModel() {
        servers = new ArrayList<>();
        listeners = new ArrayList<>();

        IRC.getClient().registerConnectionListener(createConnectionResultHandler());
        IRC.getClient().registerJoinChannelListener(createJoinChannelResultHandler());
    }

    public void addServersChangeListener(Consumer<List<Server>> listener) {
        listeners.add(listener);
    }

    private ResultHandler<Connection> createConnectionResultHandler() {
        return new SimpleResultHandler<>(this::onConnectionSuccessful, () -> {
        });
    }

    private ResultHandler<Channel> createJoinChannelResultHandler() {
        return new SimpleResultHandler<>(this::onJoinChannelSuccess, () -> {
        });
    }

    private void onConnectionSuccessful(Connection connection) {
        Server server = new Server(connection.getServerName());
        servers.add(server);
        currentServer = server;
        updateListeners();
    }

    private void onJoinChannelSuccess(Channel channel) {
        if (Objects.nonNull(currentServer)) {
            currentServer.addChannel(channel.getName());
            updateListeners();
        }
    }

    private void updateListeners() {
        listeners.forEach(listener -> listener.accept(Collections.unmodifiableList(servers)));
    }
}
