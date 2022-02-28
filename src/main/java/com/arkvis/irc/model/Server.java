package com.arkvis.irc.model;

public class Server {

    private final String name;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void connect(Engine engine, Connection connection, ResultHandler<Server> resultHandler) {
        engine.connect(connection, resultHandler);
    }
}
