package com.jkpr.chinesecheckers.client.boards.factory;

import com.jkpr.chinesecheckers.client.boards.AbstractBoardClient;
import com.jkpr.chinesecheckers.client.boards.CellClient;
import com.jkpr.chinesecheckers.client.boards.PieceClient;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;

import java.util.Map;

public class YYBoardFactory implements BoardFactory{
    @Override
    public AbstractBoardClient generate(int id, int count) {
        AbstractBoardClient board=new AbstractBoardClient(id,count);

        Integer[] playerDistribution=new Integer[6];
        Map<Position, CellClient> cells=board.getCells();

        playerDistribution=new Integer[6];

        playerDistribution[0]=0;
        playerDistribution[count]=1;

        int cellNumber = 13;
        for (int y = -4; y <= 8; y++) {
            int x = -4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    CellClient cell = new CellClient(pos);
                    cell.setPiece(getPiece(x,y,playerDistribution));
                    cells.put(pos, cell);
                }
                x++;
            }
            cellNumber--;
        }

        cellNumber = 13;
        for (int y = 4; y >= -8; y--) {
            int x = 4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    CellClient cell = new CellClient(pos);
                    cell.setPiece(getPiece(x,y,playerDistribution));
                    cells.put(pos, cell);
                }
                x--;
            }
            cellNumber--;
        }
        return board;
    }
    private PieceClient getPiece(int x, int y,Integer[] playerDistribution) {
        if (y < -4) {
            Integer player=playerDistribution[0];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else if (y > 4) {
            Integer player=playerDistribution[3];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else if (x < -4) {
            Integer player=playerDistribution[4];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else if (x > 4) {
            Integer player=playerDistribution[1];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else if ( x + y >= 5) {
            Integer player=playerDistribution[2];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else if (x + y <= -5) {
            Integer player=playerDistribution[5];
            if(player==null)
                return null;
            else
                return new PieceClient(player);
        }
        else {
            return null;
        }
    }
}
