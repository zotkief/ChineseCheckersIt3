package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.Message;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import com.jkpr.chinesecheckers.server.strategy.BotStrategy;

import java.util.List;

public class Bot implements PlayerHandler{
    private Player player;
    private Game game;
    private Session gameSession;
    private Position target;
    private final BotStrategy strategy;
    public Bot(Player player, Game game, Session adapter, BotStrategy strategy){
        this.game=game;
        this.player=player;
        this.gameSession =adapter;
        this.strategy=strategy;

        target=game.getTarget(player);
    }
    @Override
    public void sendMessage(Message message) {
        UpdateMessage update=(UpdateMessage)message;
        System.out.println("broadcast"+update.content+" id"+ player.getId());

        String[] parts = update.content.split(" ");
        int i=0;
        while(i< parts.length-1)
        {
            if(parts[i].equals("NEXT_ID")
                    && parts[i+1].equals(String.valueOf(player.getId())))
            {
                makeMove();
            }
            i++;
        }
    }
    @Override
    public void assignGameAdapter(Session gameSession) {
        this.gameSession = gameSession;
    }

    private void makeMove(){
        System.out.println("makeMove");
        MoveMessage move = strategy.pickMove(game,player,target);
        if(!(move==null))
        {
            gameSession.broadcastMessage(move,this);
        }
        else
        {
            gameSession.broadcastMessage(new MoveMessage(),this);
        }
    }
}
