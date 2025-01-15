package com.jkpr.chinesecheckers.server.gamelogic.rules;

import com.jkpr.chinesecheckers.server.gamelogic.Move;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Piece;
import com.jkpr.chinesecheckers.server.gamelogic.Player;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Cell;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YYRules extends AbstractRules{
    private final int enemy=new Random().nextInt(5)+1;

    public YYRules() {
        super(2, 6);
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
    public Cell configureCell(Position position) {
        Cell cell=new Cell(position,getOwners(position.getX(),position.getY()));
        cell.setPiece(getPiece(position));
        return cell;
    }

    @Override
    public void configureDistribution(AbstractBoard board) {
        playerDistribution[0]=players.get(0);
        playerDistribution[enemy]=players.get(1);
    }

    @Override
    public String getGenMessage() {
        return "YY "+enemy+" ";
    }

    private Piece getPiece(Position position){
        if(position.getY()<-4)
            return new Piece(playerDistribution[0]);
        else if(position.getX()>4 && playerDistribution[1]!=null)
            return new Piece(playerDistribution[1]);
        else if(position.getY()+position.getX()>4 && playerDistribution[2]!=null)
            return new Piece(playerDistribution[2]);
        else if(position.getY()>4 && playerDistribution[3]!=null)
            return new Piece(playerDistribution[3]);
        else if(position.getX()<-4 && playerDistribution[4]!=null)
            return new Piece(playerDistribution[4]);
        else if(position.getY()+ position.getX()<-4 && playerDistribution[5]!=null)
            return new Piece(playerDistribution[5]);
        else
            return null;
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
    private void findPossibilities(AbstractBoard board, List<Position> alreadyVisited, Player player, Position start) {
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
}
