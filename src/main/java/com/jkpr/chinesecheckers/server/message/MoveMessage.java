package com.jkpr.chinesecheckers.server.message;

import com.jkpr.chinesecheckers.server.gamelogic.Move;

/**
 * The {@code MoveMessage} class represents a message for transmitting game moves between the client and server.
 * It can either contain the details of a player's move or a skip action indicating that the player wants to skip their turn.
 * This class extends the {@code Message} class with a {@code MessageType.MOVE}.
 */
public class MoveMessage extends Message {
    /**
     * The move object containing details about the move.
     */
    private Move move;

    /**
     * Coordinates for the start and end positions of the move (for client-side use).
     */
    private Integer q1, r1, q2, r2;

    /**
     * A flag indicating whether the player is skipping their turn.
     */
    private boolean skip;

    /**
     * Constructs a {@code MoveMessage} with a {@code Move} object.
     * This constructor is used for sending move details from the server side.
     *
     * @param move The {@code Move} object containing start and end coordinates of the move.
     */
    public MoveMessage(Move move) {
        super(MessageType.MOVE);
        this.move = move;
        this.q1 = move.getStart().getX();
        this.r1 = move.getStart().getY();
        this.q2 = move.getEnd().getX();
        this.r2 = move.getEnd().getY();
    }

    /**
     * Constructs a {@code MoveMessage} representing a skip action.
     * This is used when a player decides to skip their turn.
     */
    public MoveMessage() {
        super(MessageType.MOVE);
        this.skip = true;
    }

    /**
     * Constructs a {@code MoveMessage} with specific start and end coordinates.
     * This constructor is used for client-side communication to specify the move's coordinates.
     *
     * @param q1 The starting x-coordinate (q1).
     * @param r1 The starting y-coordinate (r1).
     * @param q2 The ending x-coordinate (q2).
     * @param r2 The ending y-coordinate (r2).
     */
    public MoveMessage(int q1, int r1, int q2, int r2) {
        super(MessageType.MOVE);
        this.q1 = q1;
        this.r1 = r1;
        this.q2 = q2;
        this.r2 = r2;
        this.move = new Move(q1, r1, q2, r2);
    }

    /**
     * Serializes the {@code MoveMessage} to a string format for transmission.
     * The serialized format includes the message type and the move coordinates.
     *
     * @return A serialized string representing the move message.
     */
    @Override
    public String serialize() {
        return getType().name() + " " + q1 + "," + r1 + " " + q2 + "," + r2;
    }

    /**
     * Deserializes the string content to create a {@code MoveMessage}.
     * This method interprets the content string to extract the move details or a skip action.
     *
     * @param content The string content representing the move or skip action.
     * @return A new {@code MoveMessage} created from the provided content.
     */
    public static MoveMessage fromContent(String content) {
        if (content.equals("SKIP"))
            return new MoveMessage();
        String[] parts = content.split(" ");
        String[] start = parts[0].split(",");
        String[] end = parts[1].split(",");
        return new MoveMessage(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
    }

    /**
     * Retrieves the {@code Move} object representing the player's move.
     *
     * @return The {@code Move} object for this move message.
     */
    public Move getMove() {
        return move;
    }

    /**
     * Returns whether the player is skipping their turn.
     *
     * @return {@code true} if the player is skipping their turn, {@code false} otherwise.
     */
    public boolean getSkip() {
        return skip;
    }
}
