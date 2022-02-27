package com.arkvis.irc.ui.users;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.EventEmitter;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.function.Consumer;

public class UsersViewModel {
    private final Map<String, List<String>> channelUsersMap;
    private final ObservableList<String> users;

    public UsersViewModel() {
        channelUsersMap = new HashMap<>();
        users = FXCollections.observableArrayList();

        IRC.getClient().addJoinChannelListener(createJoinChannelResultHandler());
        EventEmitter.getInstance().registerSelectChannelListener(createSelectChannelListener());
    }

    public ObservableList<String> getUsers() {
        return users;
    }

    private Consumer<String> createSelectChannelListener() {
        return channelName -> updateUsers(channelUsersMap.getOrDefault(channelName, Collections.emptyList()));
    }

    private void updateUsers(List<String> newUsers) {
        Platform.runLater(() -> users.setAll(newUsers));
    }

    private ResultHandler<ChannelEvent> createJoinChannelResultHandler() {
        return new SimpleResultHandler<>(this::onJoinChannelSuccess, () -> {
        });
    }

    private void onJoinChannelSuccess(ChannelEvent channel) {
        List<String> channelUsers = new ArrayList<>(channel.getUsers());
        channelUsers.sort(String::compareToIgnoreCase);

        channelUsersMap.put(channel.getName(), channelUsers);
        updateUsers(channelUsers);
    }
}
