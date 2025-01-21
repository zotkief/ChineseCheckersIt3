package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.UI.GameOptions;
import com.jkpr.chinesecheckers.server.database.DataOperator;
import com.jkpr.chinesecheckers.server.database.DatabaseManager;
import com.jkpr.chinesecheckers.server.message.GenMessage;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;

import java.util.List;

public class WatchSession implements Session{
    private PlayerHandler clients;

    public WatchSession(ClientHandler players, Server server, GameOptions options)
    {
        clients=players;

        DatabaseManager databaseManager=new DatabaseManager(DataOperator.jdbcTemplate());
        int id=options.getGameId();
        databaseManager.setGameId(id);
        List<String> list=databaseManager.getMoves();

        clients.sendMessage(new GenMessage(list.get(0)));

        for(int i=1;i<list.size();i++){
            int start=list.get(i).indexOf(' ');
            sendMessage(new UpdateMessage(list.get(i).substring(start+1)));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void broadcastMessage(MoveMessage moveMessage, PlayerHandler clientHandler) {

    }
    private void sendMessage(UpdateMessage updateMessage){
        clients.sendMessage(updateMessage);
    }
}
