package com.jkpr.chinesecheckers.server;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.Message;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;

import java.util.List;

public class Bot implements PlayerHandler{
    private Player player;
    private Game game;
    private Session gameSession;
    private Position target;
    public Bot(Player player, Game game, Session adapter) {
        this.game=game;
        this.player=player;
        this.gameSession =adapter;

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
        List<Position> pieces=game.getPiecePositions(player);
        MoveMessage move=null;
        int min=100,maxDest=0;
        for(Position pos:pieces){
            List<Position> destinations=game.getLegalMoves(player,pos);
            for(Position destination:destinations)
            {
                //decision algorithm
                int dxdest=Math.abs(destination.getX()-target.getX());
                int dydest=Math.abs(destination.getY()-target.getY());
                int dzdest=Math.abs(destination.getX()+destination.getY()-target.getX()-target.getY());
                int dxstart=Math.abs(pos.getX()-target.getX());
                int dystart=Math.abs(pos.getY()-target.getY());
                int dzstart=Math.abs(pos.getX()+pos.getY()-target.getX()-target.getY());

                int dStart=dxstart+dystart+dzstart;
                int dDest=dxdest+dydest+dzdest;

                int destLen=dDest-dStart;

                if(destLen<min)
                {
                    move=new MoveMessage(pos.getX(),pos.getY(),
                            destination.getX(),destination.getY());
                    min=destLen;
                    maxDest=dStart;
                }
                else if(destLen==min && dStart>maxDest)
                {
                    move=new MoveMessage(pos.getX(),pos.getY(),
                            destination.getX(),destination.getY());
                    min=destLen;
                }
                //decision algorithm
            }
        }
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
