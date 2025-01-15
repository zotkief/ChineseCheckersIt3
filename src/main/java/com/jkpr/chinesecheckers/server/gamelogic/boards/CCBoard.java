package com.jkpr.chinesecheckers.server.gamelogic.boards;

import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.rules.AbstractRules;

/**
 * Represents the board for the Chinese Checkers game.
 * <p>
 * The {@code CCBoard} class extends {@code AbstractBoard} and provides a specific implementation of the game board for Chinese Checkers.
 * It handles the generation of the board, setting the possible movements for pieces, and making moves on the board.
 * </p>
 */
public class CCBoard extends AbstractBoard {

    /**
     * Generates the Chinese Checkers board by setting up the cells and distributing them according to the provided rules.
     * <p>
     * This method creates the grid structure of the game board, with cells arranged in a hexagonal pattern.
     * The cells are configured using the rules passed in the argument.
     * </p>
     *
     * @param rules the game rules to use for configuring the cells
     */
    @Override
    public void generate(AbstractRules rules)
    {
        rules.configureDistribution(this);

        // First half of the board (top part)
        int cellNumber = 13;
        for (int y = -4; y <= 8; y++) {
            int x = -4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    Cell cell = rules.configureCell(pos);
                    cells.put(pos, cell);
                }
                x++;
            }
            cellNumber--;
        }

        // Second half of the board (bottom part)
        cellNumber = 13;
        for (int y = 4; y >= -8; y--) {
            int x = 4;
            for (int k = 0; k < cellNumber; k++) {
                Position pos = new Position(x, y);
                if (!cells.containsKey(pos)) {
                    Cell cell = rules.configureCell(pos);
                    cells.put(pos, cell);
                }
                x--;
            }
            cellNumber--;
        }
    }

    /**
     * Sets the possible movements for pieces on the board.
     * <p>
     * This method defines the directions in which pieces can move.
     * The movement directions include horizontal, vertical, and diagonal directions.
     * </p>
     */
    @Override
    public void setMovements() {
        movements.clear();

        // Movement possibilities
        movements.add(new Position(-1, 0));  // Left
        movements.add(new Position(1, 0));   // Right
        movements.add(new Position(0, -1));  // Up
        movements.add(new Position(0, 1));   // Down
        movements.add(new Position(1, -1));  // Bottom-left diagonal
        movements.add(new Position(-1, 1));  // Top-right diagonal
    }

    /**
     * Makes a move by transferring a piece from the starting position to the ending position.
     * <p>
     * This method updates the board by moving a piece from its start position to its end position,
     * and clears the start position once the piece has moved.
     * </p>
     *
     * @param move the move to execute, containing the start and end positions
     */
    @Override
    public void makeMove(Move move) {
        Position start = move.getStart(), end = move.getEnd();
        cells.get(end).setPiece(cells.get(start).getPiece());  // Move piece to the new position
        cells.get(start).setPiece(null);  // Clear the piece from the start position
    }
}
