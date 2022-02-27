package com.arkvis.irc.ui.users;

import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.model.UserJoinEvent;
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

        IRC.getClient().addUserJoinChannelListener(createUserJoinChannelResultHandler());
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

    private ResultHandler<UserJoinEvent> createUserJoinChannelResultHandler() {
        return new SimpleResultHandler<>(this::onUserJoinChannelSuccess, () -> {
        });
    }

    private void onUserJoinChannelSuccess(UserJoinEvent joinEvent) {
        List<String> channelUsers = new ArrayList<>(joinEvent.getUsers());
        channelUsers.sort(String::compareToIgnoreCase);

        channelUsersMap.put(joinEvent.getChannelName(), channelUsers);
        updateUsers(channelUsers);
    }
}
