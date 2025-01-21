package com.jkpr.chinesecheckers.server.sessionState;

public interface SessionBehavior {
    SessionBehavior setReady();
    SessionBehavior setPrepare();
    SessionState getState();
}
