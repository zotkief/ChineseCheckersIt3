package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.message.MoveMessage;

public interface Session {
    public void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler);
}
