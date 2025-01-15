package com.jkpr.chinesecheckers.server.gamelogic;

import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;

/**
 * Represents a move in the game, consisting of a start and end position.
 * <p>
 * The {@code Move} class is used to represent a movement from one position on the game board to another.
 * It stores the start and end positions as {@code Position} objects and provides methods to access these positions.
 * </p>
 */
public class Move {

    /** The starting position of the move. */
    private Position start;

    /** The ending position of the move. */
    private Position end;

    /**
     * Constructs a new {@code Move} with the specified coordinates for start and end positions.
     *
     * @param xStart the x-coordinate of the start position
     * @param yStart the y-coordinate of the start position
     * @param xEnd the x-coordinate of the end position
     * @param yEnd the y-coordinate of the end position
     */
    public Move(int xStart, int yStart, int xEnd, int yEnd) {
        start = new Position(xStart, yStart);
        end = new Position(xEnd, yEnd);
    }

    /**
     * Constructs a new {@code Move} with the specified start and end positions.
     *
     * @param start the starting position of the move
     * @param end the ending position of the move
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the ending position of the move.
     *
     * @return the ending position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Gets the starting position of the move.
     *
     * @return the starting position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Returns a string representation of the move, including the start and end positions.
     *
     * @return a string representing the move
     */
    @Override
    public String toString() {
        return start.toString() + " " + end.toString();
    }
}
