package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.message.Message;

public interface PlayerHandler {
    void sendMessage(Message message);
    void assignGameAdapter(GameAdapter gameAdapter);
}
