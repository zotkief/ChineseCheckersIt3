package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.message.MoveMessage;

public interface Session {
    void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler);
    boolean isReady();
    void setPrepare();
    void setReady();

}
