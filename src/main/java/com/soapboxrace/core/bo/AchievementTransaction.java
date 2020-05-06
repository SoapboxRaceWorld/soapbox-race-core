package com.soapboxrace.core.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a "transaction" for persona achievement updates.
 *
 * @author coder
 */
public class AchievementTransaction {
    /**
     * The ID of the persona
     */
    private final Long personaId;

    /**
     * The transaction entries; key is achievement category, value is the map of global values for scripts
     */
    private final Map<String, Map<String, Object>> entries = new HashMap<>();

    /**
     * Whether the transaction has been committed
     */
    private boolean committed = false;

    /**
     * Initializes a new instance of the {@link AchievementTransaction} class.
     *
     * @param personaId the persona ID
     */
    public AchievementTransaction(Long personaId) {
        this.personaId = personaId;
    }

    /**
     * Adds a new entry to the transaction
     *
     * @param category   the achievement category
     * @param parameters the global variable map
     */
    public synchronized void add(String category, Map<String, Object> parameters) {
        if (this.committed) {
            throw new RuntimeException("add() is not permitted for committed transactions.");
        }

        if (this.entries.containsKey(category)) {
            throw new RuntimeException("Called add() with a category that has already been added: " + category);
        }

        this.entries.put(category, parameters);
    }

    public Long getPersonaId() {
        return personaId;
    }

    public Map<String, Map<String, Object>> getEntries() {
        return entries;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void markCommitted() {
        if (this.committed) {
            throw new RuntimeException("Attempted to commit the same transaction more than once!");
        }

        this.committed = true;
    }
}
