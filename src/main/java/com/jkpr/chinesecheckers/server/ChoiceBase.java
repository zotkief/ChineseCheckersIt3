package com.jkpr.chinesecheckers.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code ChoiceBase} class represents a selection model for different game types and associated player count options.
 * It stores a list of game types and corresponding valid player counts for each type.
 */
public class ChoiceBase {

    /** A map containing game types as keys and arrays of valid player counts as values. */
    private HashMap<String, String[]> base = new HashMap<>();

    /** A list of game types available for selection. */
    private ArrayList<String> types = new ArrayList<>();

    /**
     * Constructs a new {@code ChoiceBase} object, initializing it with predefined game types and their valid player counts.
     * The game types include "Standard", "Fast Paced", and "Yin and Yang", with varying allowed player counts.
     */
    public ChoiceBase() {
        base.put("Standard", new String[]{"2", "3", "4", "6"});
        types.add("Standard");

        base.put("Fast Paced", new String[]{"2", "3", "4", "6"});
        types.add("Fast Paced");

        base.put("Yin and Yang", new String[]{"2"});
        types.add("Yin and Yang");
    }

    /**
     * Returns an array of available game types.
     *
     * @return A string array containing the available game types.
     */
    public String[] getKeys() {
        return types.toArray(new String[0]);
    }

    /**
     * Returns an array of valid player counts for the specified game type.
     *
     * @param key The game type whose valid player counts are to be retrieved.
     * @return A string array containing the valid player counts for the specified game type.
     */
    public String[] getArray(String key) {
        return base.get(key);
    }
}
