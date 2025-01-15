package com.jkpr.chinesecheckers.server.gamelogic.boards;

import java.util.Objects;

/**
 * Represents a position on the board in a two-dimensional space (x, y).
 * <p>
 * The {@code Position} class is used to store and manipulate coordinates
 * in a Chinese checkers game. It provides constructors for creating positions
 * and methods for comparing and generating hash codes for them.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Position pos1 = new Position(2, 3);
 * Position pos2 = new Position(pos1, new Position(1, 1));
 * </pre>
 */
public class Position {

    /** The x-coordinate of the position. */
    private int x;

    /** The y-coordinate of the position. */
    private int y;

    /**
     * Constructs a {@code Position} with specified x and y coordinates.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a {@code Position} by adding two other {@code Position} objects.
     * This will create a new position whose coordinates are the sum of the
     * coordinates of {@code pos1} and {@code pos2}.
     *
     * @param pos1 the first {@code Position} object
     * @param pos2 the second {@code Position} object
     */
    public Position(Position pos1, Position pos2) {
        this.x = pos1.x + pos2.x;
        this.y = pos1.y + pos2.y;
    }

    /**
     * Returns the hash code for this {@code Position}.
     * The hash code is based on the x and y coordinates.
     *
     * @return the hash code of this {@code Position}
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compares this {@code Position} with another object for equality.
     * Two {@code Position} objects are considered equal if their x and y
     * coordinates are the same.
     *
     * @param obj the object to be compared for equality with this {@code Position}
     * @return {@code true} if the specified object is equal to this {@code Position},
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    /**
     * na potrzeby wiadomosci
     */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    @Override
    public String toString()
    {
        return x+" "+y;
    }
}
