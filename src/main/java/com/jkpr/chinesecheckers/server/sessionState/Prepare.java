package com.jkpr.chinesecheckers.server.sessionState;

public class Prepare implements SessionBehavior{
    @Override
    public SessionBehavior setReady() {
        return new Ready();
    }

    @Override
    public SessionBehavior setPrepare() {
        return this;
    }

    @Override
    public SessionState getState() {
        return SessionState.PREPARE;
    }
}
