package com.arkvis.irc.ui.users;

import com.arkvis.irc.model.OtherJoinEvent;
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
    private String currentChannel;
    private final ObservableList<String> users;

    public UsersViewModel() {
        channelUsersMap = new HashMap<>();
        users = FXCollections.observableArrayList();

        IRC.getClient().addUserJoinChannelListener(createUserJoinChannelResultHandler());
        IRC.getClient().addOtherUserJoinChannelListener(createOtherJoinChannelListener());
        EventEmitter.getInstance().registerSelectChannelListener(createSelectChannelListener());
    }

    public ObservableList<String> getUsers() {
        return users;
    }

    private Consumer<OtherJoinEvent> createOtherJoinChannelListener() {
        return joinEvent -> {
            String channelName = joinEvent.getChannelName();

            List<String> channelUsers = channelUsersMap.get(channelName);
            channelUsers.add(joinEvent.getNickName());
            channelUsers.sort(String::compareToIgnoreCase);

            if (currentChannel.equals(channelName)) {
                updateUsers(channelUsers);
            }
        };
    }

    private Consumer<String> createSelectChannelListener() {
        return channelName -> {
            currentChannel = channelName;
            updateUsers(channelUsersMap.getOrDefault(channelName, Collections.emptyList()));
        };
    }

    private ResultHandler<UserJoinEvent> createUserJoinChannelResultHandler() {
        return new SimpleResultHandler<>(this::onUserJoinChannelSuccess, () -> {
        });
    }

    private void onUserJoinChannelSuccess(UserJoinEvent joinEvent) {
        List<String> channelUsers = new ArrayList<>(joinEvent.getUsers());
        channelUsers.sort(String::compareToIgnoreCase);

        channelUsersMap.put(joinEvent.getChannelName(), channelUsers);
        currentChannel = joinEvent.getChannelName();
        updateUsers(channelUsers);
    }

    private void updateUsers(List<String> newUsers) {
        Platform.runLater(() -> users.setAll(newUsers));
    }
}
