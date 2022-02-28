package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Channel {

    private final String name;
    private final List<User> users;

    private final List<Consumer<User>> otherUserJoinChannelListeners;

    public Channel(Engine engine, String name, List<User> users) {
        this.name = name;
        this.users = new ArrayList<>(users);

        otherUserJoinChannelListeners = new ArrayList<>();
        engine._addOtherJoinChannelListener(name, this::addUser);
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addOtherUserJoinChannelListener(Consumer<User> listener) {
        otherUserJoinChannelListeners.add(listener);
    }

    private void addUser(User user) {
        users.add(user);
        otherUserJoinChannelListeners.forEach(listener -> listener.accept(user));
    }
}
