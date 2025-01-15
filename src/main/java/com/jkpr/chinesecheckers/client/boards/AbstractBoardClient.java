package com.jkpr.chinesecheckers.client.boards;

import com.jkpr.chinesecheckers.server.gamelogic.Move;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the abstract concept of a game board.
 * <p>
 * The {@code AbstractBoard} class serves as a base for defining game boards in different variants of the game.
 * It maintains a map of cells, each identified by a {@code Position}, and a list of valid movement directions.
 * Subclasses are expected to implement specific rules for validating moves and managing the board state.
 * </p>
 */
public class AbstractBoardClient {

    /** A map of all the cells on the board, where the key is the position of the cell. */
    protected Map<Position, CellClient> cells = new HashMap<Position, CellClient>();
    private Integer[] playerDistribution;

    protected int id;
    protected int count;
    protected boolean active=false;
    protected boolean won=false;
    protected int currentTurn=-1;

    public void processNext(int id){
        active=this.id==id;
        currentTurn=id;
    }
    public int getCurrentTurn(){
        return currentTurn;
    }
    public void processWin(int id){
        if(!won)
            won=this.id==id;
    }

    public AbstractBoardClient(int id,int count){
        this.id=id;
        this.count=count;
    }
    public void makeMove(Move move) {
        Position start=move.getStart(),end=move.getEnd();
        cells.get(end).setPiece(cells.get(start).getPiece());
        cells.get(start).setPiece(null);
    }
    public Map<Position, CellClient> getCells(){return cells;}
    public void setDistribution(Integer[] playerDistribution){this.playerDistribution=playerDistribution;}
    public int getId(){return id;}
    public CellClient getCell(Position pos){return cells.get(pos);}
}
