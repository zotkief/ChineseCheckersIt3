package com.jkpr.chinesecheckers.server.sessionState;

public class Ready implements SessionBehavior{
    @Override
    public SessionBehavior setReady() {
        return this;
    }

    @Override
    public SessionBehavior setPrepare() {
        return this;
    }

    @Override
    public SessionState getState() {
        return SessionState.READY;
    }
}
