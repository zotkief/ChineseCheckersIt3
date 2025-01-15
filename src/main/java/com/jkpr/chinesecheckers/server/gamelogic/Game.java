package com.jkpr.chinesecheckers.server.gamelogic;

import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.gamelogic.rules.AbstractRules;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of Chinese Checkers.
 * <p>
 * The {@code Game} class handles the flow of the game, including player actions, validating moves, and updating the game state.
 * It interacts with the game board and the rules to determine whether moves are valid and to manage the game state.
 * </p>
 */
public class Game {

    /** The count of players in the game. */
    private int playersCount = 0;

    /** The game board. */
    private AbstractBoard board;

    /** The game rules. */
    private AbstractRules rules;

    /**
     * Processes the next move made by a player.
     * <p>
     * This method checks whether the player is in the active state and whether the move is valid.
     * If the player skips their turn, the state of the game is updated to reflect the skip.
     * Otherwise, it validates the move and applies it.
     * </p>
     *
     * @param message the move message containing the move details
     * @param player the player making the move
     * @return an {@code UpdateMessage} indicating the outcome of the move
     */
    public UpdateMessage nextMove(MoveMessage message, Player player) {
        System.out.println(message.getSkip()+" "+message.getMove());
        // If the player is not active, the move is invalid.
        if (player.getState() != PlayerState.ACTIVE) {
            return UpdateMessage.fromContent("FAIL");
        }
        System.out.println(message.getSkip());
        // If the player chose to skip, update the player states and return a skip message.
        if (message.getSkip()) {
            return UpdateMessage.fromContent("SKIP NEXT_ID " + rules.setStates(new ArrayList<>(), player));
        }
        //System.out.println(message.serialize());
        // Otherwise, validate and process the move.
        return rules.isValidMove(board, player, message.getMove());
    }

    /**
     * Sets the game board.
     *
     * @param board the board to set for the game
     */
    public void setBoard(AbstractBoard board) {
        this.board = board;
    }

    /**
     * Sets the game rules.
     *
     * @param rules the rules to set for the game
     */
    public void setRules(AbstractRules rules) {
        this.rules = rules;
    }

    /**
     * Adds a player to the game.
     *
     * @return the player who joins the game
     */
    public Player join() {
        return rules.getPlayers().get(playersCount++);
    }

    /**
     * Generates the game setup, including the game board.
     */
    public void generate() {
        board.generate(rules);
    }

    /**
     * Returns a message describing the game generation.
     *
     * @return a message containing the game generation information
     */
    public String getGenMessage() {
        return rules.getGenMessage();
    }
    public Position getTarget(Player player){
        return rules.getTarget(player);
    }
    public List<Position> getLegalMoves(Player player,Position start){
        List<Position> positions=new ArrayList<>();
        rules.findPossibilities(board,positions,player,start);
        return positions;
    }
    public List<Position> getPiecePositions(Player player){
        List<Position> positions=new ArrayList<>();
        for(Position pos:board.getCells().keySet()){
            if(board.getCells().get(pos).checkPlayer(player))
                positions.add(pos);
        }
        return positions;
    }
}
