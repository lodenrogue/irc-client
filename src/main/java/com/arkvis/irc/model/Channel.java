package com.arkvis.irc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Channel {

    private final String name;
    private final List<User> users;
    private final List<Message> messages;

    private final List<Consumer<User>> otherUserJoinChannelListeners;
    private final List<Consumer<Message>> receivedMessageListeners;

    public Channel(Engine engine, String name, List<User> users) {
        this.name = name;
        this.users = new ArrayList<>(users);
        this.messages = new ArrayList<>();

        otherUserJoinChannelListeners = new ArrayList<>();
        receivedMessageListeners = new ArrayList<>();

        engine._addOtherJoinChannelListener(name, this::addUser);
        engine._addReceiveChannelMessageListener(name, this::addMessage);
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

    public void addReceiveMessageListener(Consumer<Message> listener) {
        receivedMessageListeners.add(listener);
    }

    private void addUser(User user) {
        users.add(user);
        otherUserJoinChannelListeners.forEach(listener -> listener.accept(user));
    }

    private void addMessage(Message message) {
        messages.add(message);
        receivedMessageListeners.forEach(listener -> listener.accept(message));
    }
}
