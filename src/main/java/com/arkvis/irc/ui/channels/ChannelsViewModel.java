package com.arkvis.irc.ui.channels;

import com.arkvis.irc.model.ConnectionEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.model.UserJoinEvent;
import com.arkvis.irc.ui.EventEmitter;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.model.SimpleResultHandler;

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

        IRC.getClient().addConnectionListener(createConnectionResultHandler());
        IRC.getClient().addUserJoinChannelListener(createUserJoinChannelResultHandler());
    }

    public void addServersChangeListener(Consumer<List<Server>> listener) {
        listeners.add(listener);
    }

    public void selectChannel(String channelName) {
        EventEmitter.getInstance().emitSelectChannelEvent(channelName);
    }

    private ResultHandler<ConnectionEvent> createConnectionResultHandler() {
        return new SimpleResultHandler<>(this::onConnectionSuccessful, () -> {
        });
    }

    private ResultHandler<UserJoinEvent> createUserJoinChannelResultHandler() {
        return new SimpleResultHandler<>(this::onUserJoinChannelSuccess, () -> {
        });
    }

    private void onConnectionSuccessful(ConnectionEvent connectionEvent) {
        Server server = new Server(connectionEvent.getServerName());
        servers.add(server);
        currentServer = server;
        updateListeners();
    }

    private void onUserJoinChannelSuccess(UserJoinEvent joinEvent) {
        if (Objects.nonNull(currentServer)) {
            currentServer.addChannel(joinEvent.getChannelName());
            updateListeners();
        }
    }

    private void updateListeners() {
        listeners.forEach(listener -> listener.accept(Collections.unmodifiableList(servers)));
    }
}
