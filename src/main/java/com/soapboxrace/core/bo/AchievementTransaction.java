package com.soapboxrace.core.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * The transaction entries; key is achievement category, value is a list of maps of global values for scripts
     */
    private final Map<String, List<Map<String, Object>>> entries = new HashMap<>();

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

        List<Map<String, Object>> categoryEntries = this.entries.computeIfAbsent(category, k -> new ArrayList<>());

        categoryEntries.add(parameters);
    }

    public Long getPersonaId() {
        return personaId;
    }

    public Map<String, List<Map<String, Object>>> getEntries() {
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

    public void clear() {
        if (!this.committed) {
            throw new RuntimeException("Non-committed transactions cannot be cleared");
        }

        this.entries.clear();
    }
}
