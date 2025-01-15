package com.jkpr.chinesecheckers.server.gamelogic.rules;

import com.jkpr.chinesecheckers.server.exceptions.InvalidNumberOfPlayers;
import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Cell;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Piece;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;

import java.util.ArrayList;
import java.util.List;

public class CCRules extends AbstractRules {
    public CCRules(int count) {
        super(count,6);
    }

    @Override
    public UpdateMessage isValidMove(AbstractBoard board, Player player, Move move) {
        Position start = move.getStart(), destination = move.getEnd();
        if (!player.getState().equals(PlayerState.ACTIVE))
            return UpdateMessage.fromContent("FAIL");

        if (board.getCells().containsKey(start) && board.getCells().get(start).checkPlayer(player)) {

            if(checkWinner(start,player) && !checkWinner(destination,player))
                return UpdateMessage.fromContent("FAIL");

            List<Position> possibilities = new ArrayList<>();
            findPossibilities(board, possibilities, player, start);


            if (possibilities.contains(destination)) {
                board.makeMove(move);
                //if player won
                List<Player> winners = controlWinners(board);
                winners=board.addPlayers(winners);

                return getMessageStructure(move,board,winners,player);
            }
        }
        return UpdateMessage.fromContent("FAIL");
    }
    @Override
    public void findPossibilities(AbstractBoard board, List<Position> alreadyVisited, Player player, Position start) {
        for (Position move : board.getMovements()) {
            Position potentialMove = new Position(start, move);
            if (isMoveLegal(board, potentialMove, player)) {
                if (!alreadyVisited.contains(potentialMove)) {
                    alreadyVisited.add(new Position(start, move));
                }
            } else {
                potentialMove = new Position(potentialMove, move);
                if (!alreadyVisited.contains(potentialMove) && isMoveLegal(board, potentialMove, player)) {
                    alreadyVisited.add(potentialMove);
                    findNetwork(board, alreadyVisited, player, potentialMove);
                }
            }
        }
    }

    private void findNetwork(AbstractBoard board, List<Position> alreadyVisited, Player player, Position position) {
        for (Position move : board.getMovements()) {
            Position potentialMove = new Position(position, move);
            if (isMoveLegal(board, potentialMove, player))
                continue;
            potentialMove = new Position(potentialMove, move);

            if (!alreadyVisited.contains(potentialMove) && isMoveLegal(board, potentialMove, player)) {
                alreadyVisited.add(potentialMove);
                findNetwork(board, alreadyVisited, player, potentialMove);
            }
        }
    }

    private boolean isMoveLegal(AbstractBoard board, Position position, Player player) {
        return board.getCells().containsKey(position)
                && board.getCells().get(position).isEmpty()
                && board.getCells().get(position).getOwners().contains(player);
    }

    @Override
    public Cell configureCell(Position position) {
        Cell cell = new Cell(position, getOwners(position.getX(), position.getY()));
        cell.setPiece(getPiece(position.getX(), position.getY()));
        return cell;
    }



    private Piece getPiece(int x, int y) {
        if (y < -4) {
            Player player = playerDistribution[0];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else if (y > 4) {
            Player player = playerDistribution[3];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else if (x < -4) {
            Player player = playerDistribution[4];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else if (x > 4) {
            Player player = playerDistribution[1];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else if (x + y >= 5) {
            Player player = playerDistribution[2];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else if (x + y <= -5) {
            Player player = playerDistribution[5];
            if (player == null)
                return null;
            else
                return new Piece(player);
        } else {
            return null;
        }
    }

    private List<Player> getOwners(int x, int y) {
        List<Player> list = new ArrayList<>();
        if (y < -4 || y > 4) {
            list.add(playerDistribution[0]);
            list.add(playerDistribution[3]);
        } else if (x < -4 || x > 4) {
            list.add(playerDistribution[1]);
            list.add(playerDistribution[4]);
        } else if (x + y <= -5 || x + y >= 5) {
            list.add(playerDistribution[2]);
            list.add(playerDistribution[5]);
        } else {
            list.add(playerDistribution[0]);
            list.add(playerDistribution[1]);
            list.add(playerDistribution[2]);
            list.add(playerDistribution[3]);
            list.add(playerDistribution[4]);
            list.add(playerDistribution[5]);
        }
        return list;
    }

    private UpdateMessage getMessageStructure(Move move,AbstractBoard board,List<Player> winners,Player currentPlayer) {
        //FORMAT: "ruch" NEXT_ID "id kolejnego" WIN_ID "id wygranego" (jeżeli nikt to null)
        String output = move + " NEXT_ID " + setStates(winners,currentPlayer) + " WIN_ID";
        for(Player player:winners)
        {
            output+=" "+player.getId();
        }
        if (board.getWinnersNumber() == players.size() - 1) {
            output += " END";
        }
        return UpdateMessage.fromContent(output);
    }

    private List<Player> controlWinners(AbstractBoard board) {
        int[] winningPieces = new int[6];
        List<Player> winners=new ArrayList<>();
        for (Position pos : board.getCells().keySet()) {
            int y = pos.getY(), x = pos.getX();
            if (y < -4 && board.compareCell(pos, playerDistribution[3]))
                winningPieces[3]++;
            else if (y > 4 && board.compareCell(pos, playerDistribution[0]))
                winningPieces[0]++;
            else if (x < -4 && board.compareCell(pos, playerDistribution[1]))
                winningPieces[1]++;
            else if (x > 4 && board.compareCell(pos, playerDistribution[4]))
                winningPieces[4]++;
            else if (x + y >= 5 && board.compareCell(pos, playerDistribution[5]))
                winningPieces[5]++;
            else if (x + y <= -5 && board.compareCell(pos, playerDistribution[2]))
                winningPieces[2]++;
        }
        for(int i=0;i<6;i++)
            if(winningPieces[i]==10)
                winners.add(playerDistribution[i]);
        return winners;
    }
    private boolean checkWinner(Position pos,Player player){
        int y=pos.getY(),x= pos.getX();
        if (y < -4 && player.equals(playerDistribution[3]))
            return true;
        else if (y > 4 && player.equals(playerDistribution[0]))
            return true;
        else if (x < -4 && player.equals(playerDistribution[1]))
            return true;
        else if (x > 4 && player.equals(playerDistribution[4]))
            return true;
        else if (x + y >= 5 && player.equals(playerDistribution[5]))
            return true;
        else return x + y <= -5 && player.equals(playerDistribution[2]);
    }

    @Override
    public void configureDistribution(AbstractBoard board) {
        int count=players.size();
        switch(count)
        {
            case 2:
                playerDistribution[0]=players.get(0);
                playerDistribution[3]=players.get(1);
                break;
            case 3:
                playerDistribution[0]=players.get(0);
                playerDistribution[4]=players.get(2);
                playerDistribution[2]=players.get(1);
                break;
            case 4:
                playerDistribution[0]=players.get(0);
                playerDistribution[4]=players.get(1);
                playerDistribution[1]=players.get(2);
                playerDistribution[3]=players.get(3);
                break;
            case 6:
                playerDistribution[0]=players.get(0);
                playerDistribution[1]=players.get(1);
                playerDistribution[2]=players.get(2);
                playerDistribution[3]=players.get(3);
                playerDistribution[4]=players.get(4);
                playerDistribution[5]=players.get(5);
                break;
            default:
                throw new InvalidNumberOfPlayers("");
        }
    }

    @Override
    public String getGenMessage() {
        return "CC "+players.size()+" ";
    }
    @Override
    public Position getTarget(Player player){
        for(int i=0;i<playerDistribution.length;i++)
        {
            if(player.equals(playerDistribution[i]))
            {
                return findTarget(i);
            }
        }
        return null;
    }
    private Position findTarget(int DId){
        switch(DId){
            case 0:
                return new Position(-4,8);
            case 1:
                return new Position(-8,4);
            case 2:
                return new Position(-4,-4);
            case 3:
                return new Position(4,-8);
            case 4:
                return new Position(8,-4);
            case 5:
                return new Position(4,4);
            default:
                return null;
        }
    }
}
