package com.jkpr.chinesecheckers.server.message;

/**
 * The {@code UpdateMessage} class represents a message used to send updates during the game.
 * This class extends the {@code Message} class and includes additional content related to game updates.
 */
public class UpdateMessage extends Message {
    /**
     * The content of the update message.
     */
    public final String content;

    /**
     * Constructs an {@code UpdateMessage} with the specified content.
     *
     * @param content The content of the update message.
     */
    public UpdateMessage(String content) {
        super(MessageType.UPDATE);
        this.content = content;
    }

    /**
     * Returns the content of the update message.
     *
     * @return The content of the update message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Serializes the {@code UpdateMessage} into a string format for transmission.
     * The serialized format includes the message type followed by the content.
     *
     * @return A serialized string representation of the update message.
     */
    @Override
    public String serialize() {
        return getType().name() + " " + content;
    }

    /**
     * Creates an {@code UpdateMessage} from the provided content string.
     *
     * @param content The content to be included in the update message.
     * @return A new {@code UpdateMessage} instance containing the specified content.
     */
    public static UpdateMessage fromContent(String content) {
        return new UpdateMessage(content);
    }
}
