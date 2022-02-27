package com.arkvis.irc.ui.users;

import com.arkvis.irc.model.ChannelEvent;
import com.arkvis.irc.model.ResultHandler;
import com.arkvis.irc.ui.IRC;
import com.arkvis.irc.ui.SimpleResultHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersViewModel {
    private final ObservableList<String> users;

    public UsersViewModel() {
        users = FXCollections.observableArrayList();
        IRC.getClient().addJoinChannelListener(createJoinChannelResultHandler());
    }

    private ResultHandler<ChannelEvent> createJoinChannelResultHandler() {
        return new SimpleResultHandler<>(
                channel -> users.setAll(channel.getUsers()),
                () -> {
                });
    }

    public ObservableList<String> getUsers() {
        return users;
    }
}
