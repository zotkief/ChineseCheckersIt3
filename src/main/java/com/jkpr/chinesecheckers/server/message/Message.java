package com.jkpr.chinesecheckers.server.message;

/**
 * The {@code Message} class serves as the base class for all message types
 * exchanged between the server and the client in the game.
 *
 * <p>This class defines the common structure and behavior for all messages,
 * including a type and serialization methods. Concrete message types, such as
 * {@link MoveMessage} and {@link UpdateMessage}, extend this class and
 * implement specific content and serialization logic.</p>
 */
public abstract class Message {
    private final MessageType type;

    /**
     * Constructs a new {@code Message} with the specified type.
     *
     * @param type the type of the message
     */
    public Message(MessageType type) {
        this.type = type;
    }

    /**
     * Gets the type of this message.
     *
     * @return the type of this message
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Serializes the message into a string format.
     * The format follows the convention: "TYPE <content>".
     *
     * @return the string representation of the message
     */
    public abstract String serialize();

    /**
     * Parses a string into the corresponding message object.
     * The input string must follow the format: "TYPE <content>".
     *
     * @param line the string representation of the message
     * @return the corresponding {@code Message} object
     * @throws IllegalArgumentException if the string format is invalid
     */
    public static Message fromString(String line) {
        int spaceIndex = line.indexOf(' ');
        if(spaceIndex == -1) {
            throw new IllegalArgumentException("Invalid message format");
        }
        String type = line.substring(0, spaceIndex).trim();
        String content = line.substring(spaceIndex + 1).trim();

        MessageType messageType;
        try {
            messageType = MessageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid message format", e);
        }

        switch (messageType) {
            case MOVE:
                return MoveMessage.fromContent(content);
            case UPDATE:
                return UpdateMessage.fromContent(content);
            default:
                throw new IllegalArgumentException("Invalid message format");
        }
    }
}
