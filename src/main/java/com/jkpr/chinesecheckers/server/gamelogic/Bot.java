package com.jkpr.chinesecheckers.server.gamelogic;

import com.jkpr.chinesecheckers.server.GameAdapter;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import javafx.geometry.Pos;

import java.util.List;
import java.util.ArrayList;

public class Bot {
    private Player player;
    private Game game;
    private GameAdapter gameAdapter;
    private Position target;
    public Bot(Player player,Game game,GameAdapter adapter) {
        this.game=game;
        this.player=player;
        this.gameAdapter=adapter;

        target=game.getTarget(player);
    }
    public void broadcastMessage(UpdateMessage message) {
        System.out.println("broadcast"+message.content+" id"+ player.getId());

        String[] parts = message.content.split(" ");
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
    private void makeMove(){
        System.out.println("makeMove");
        List<Position> pieces=game.getPiecePositions(player);
        MoveMessage move=null;
        int min=100;
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

                int destLen=dxdest+dydest+dzdest-dxstart-dystart-dzstart;

                if(destLen<min)
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
            gameAdapter.brodcastBotMessage(move,player);
        }
        else
        {
            gameAdapter.brodcastBotMessage(new MoveMessage(),player);
        }
    }
}
