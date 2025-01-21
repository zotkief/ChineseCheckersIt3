package com.jkpr.chinesecheckers.server.gamelogic.rules;

import com.jkpr.chinesecheckers.server.gamelogic.Move;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Cell;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractRules {

    protected List<Player> players=new ArrayList<Player>();

    protected Player[] playerDistribution;

    public AbstractRules(int count,int maxPlayers){
        //setting players
        for(int i=0;i<count;i++)
            players.add(new Player(i));
        playerDistribution=new Player[maxPlayers];
        players.get(0).setActive();
        System.out.println(players.get(0).getId()+"abc");
    }
    public abstract void findPossibilities(AbstractBoard board, List<Position> alreadyVisited, Player player, Position start);
    public abstract UpdateMessage isValidMove(AbstractBoard board, Player player, Move move);
    public abstract Cell configureCell(Position position);
    public abstract void configureDistribution(AbstractBoard board);
    public abstract String getGenMessage();
    public abstract Position getTarget(Player player);
    public List<Player> getPlayers(){return players;}
    public Player getPlayer(int id){return players.get(id);}

    public int setStates(List<Player> winners,Player player){
        for(Player player1:winners)
            player1.setWin();

        player.setWait();

        for(int i=0;i< players.size();i++)
            System.out.println(players.get(i).getState());

        //choosing next player
        Player tempRef=players.get((player.getId()+1)%players.size());
        while(!tempRef.getState().equals(PlayerState.WAIT))
        {
            tempRef=players.get((player.getId()+1)%players.size());
            //System.out.println(tempRef.getState());
        }
        tempRef.setActive();

        return tempRef.getId();
    }
}
