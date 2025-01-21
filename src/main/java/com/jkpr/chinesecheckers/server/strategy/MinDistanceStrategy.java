package com.jkpr.chinesecheckers.server.strategy;

import com.jkpr.chinesecheckers.server.gamelogic.Game;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.message.MoveMessage;

import java.util.List;

public class MinDistanceStrategy implements BotStrategy {
    @Override
    public MoveMessage pickMove(Game game, Player player, Position target){
        List<Position> pieces = game.getPiecePositions(player);
        MoveMessage move = null;
        int min= Integer.MAX_VALUE;
        int maxDest = Integer.MIN_VALUE;

        for(Position pos : pieces){
            List<Position> destinations = game.getLegalMoves(player, pos);
            for(Position destination : destinations){
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
            }
        }
        return move;
    }
}
