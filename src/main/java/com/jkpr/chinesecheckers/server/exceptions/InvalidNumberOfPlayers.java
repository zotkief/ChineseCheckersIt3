package com.jkpr.chinesecheckers.server.exceptions;

public class InvalidNumberOfPlayers extends RuntimeException {
    public InvalidNumberOfPlayers(String message) {
        super(message);
    }
}
