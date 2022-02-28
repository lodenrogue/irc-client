package com.arkvis.irc.model;

public class Server {
    private final String name;
    private final User primaryUser;

    public Server(String name, User primaryUser) {
        this.name = name;
        this.primaryUser = primaryUser;
    }

    public String getName() {
        return name;
    }

    public static void connect(Engine engine, Connection connection, ResultHandler<Server> resultHandler) {
        engine.connect(connection, resultHandler);
    }

    public User getPrimaryUser() {
        return primaryUser;
    }
}
