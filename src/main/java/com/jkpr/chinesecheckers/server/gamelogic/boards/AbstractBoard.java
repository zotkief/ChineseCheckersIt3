package com.jkpr.chinesecheckers.server.gamelogic.boards;

import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.rules.AbstractRules;
import com.jkpr.chinesecheckers.server.gamelogic.states.PlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the abstract base class for a game board in Chinese Checkers.
 * <p>
 * The {@code AbstractBoard} class provides the foundational structure for the game board, managing the cells,
 * movements, players, and game state. Concrete board classes (like {@code CCBoard}) will extend this class
 * to define specific game board behaviors, such as board generation and movement logic.
 * </p>
 */
public abstract class AbstractBoard {

    /**
     * Constructor that initializes the board and sets the movement rules.
     * <p>
     * This constructor ensures that the movement rules are set when the board is created.
     * </p>
     */
    public AbstractBoard(){
        setMovements();
    }

    /** A map of positions to cells on the board. */
    protected Map<Position, Cell> cells = new HashMap<Position, Cell>();

    /** A list of possible movement positions. */
    protected List<Position> movements = new ArrayList<>();

    /** A list of players who have won the game. */
    private final ArrayList<Player> winners = new ArrayList<>();

    /**
     * Sets the valid movement directions for pieces on the board.
     * <p>
     * This method must be implemented by subclasses to define the movement directions that pieces can take
     * on the board, such as horizontal, vertical, or diagonal directions.
     * </p>
     */
    public abstract void setMovements();

    /**
     * Returns the map of all cells on the board.
     * <p>
     * The map is keyed by the position of the cell, with each position mapping to a specific cell.
     * </p>
     *
     * @return the map of cells on the board
     */
    public Map<Position, Cell> getCells() {
        return cells;
    }

    /**
     * Returns the list of possible movement directions for pieces on the board.
     * <p>
     * This list contains positions that represent valid directions a piece can move.
     * </p>
     *
     * @return the list of valid movement positions
     */
    public List<Position> getMovements() {
        return movements;
    }

    /**
     * Executes a move on the board.
     * <p>
     * This method is responsible for updating the board state by applying the given move.
     * The actual movement logic should be defined by concrete subclasses.
     * </p>
     *
     * @param move the move to execute
     */
    public abstract void makeMove(Move move);

    /**
     * Compares a specific cell's position to the player's piece.
     * <p>
     * This method checks if the given position contains a piece owned by the provided player.
     * </p>
     *
     * @param position the position of the cell to check
     * @param player   the player to compare with
     * @return {@code true} if the player owns the piece at the given position; {@code false} otherwise
     */
    public boolean compareCell(Position position, Player player) {
        return cells.containsKey(position) && cells.get(position).checkPlayer(player);
    }

    /**
     * Returns the number of players who have won the game.
     * <p>
     * This method provides the count of players who are currently marked as winners.
     * </p>
     *
     * @return the number of winners
     */
    public int getWinnersNumber() {
        return winners.size();
    }

    /**
     * Generates the board and sets up the cells using the provided game rules.
     * <p>
     * This method is implemented by subclasses to define how the board is generated and populated
     * based on the specific rules of the game.
     * </p>
     *
     * @param rules the rules to use for generating the board
     */
    public abstract void generate(AbstractRules rules);

    /**
     * Adds players to the game, ensuring that no duplicate players are added to the winners list.
     * <p>
     * This method adds players to the winners list only if they are not already in it.
     * </p>
     *
     * @param adder the list of players to add
     * @return a list of players who were successfully added to the winners list
     */
    public List<Player> addPlayers(List<Player> adder) {
        List<Player> result = new ArrayList<>();
        for (Player player : adder) {
            if (!winners.contains(player)) {
                winners.add(player);
                result.add(player);
            }
        }
        return result;
    }
}
