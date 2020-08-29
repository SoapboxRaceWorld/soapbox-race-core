package com.soapboxrace.core.dao.util;

/**
 * Base class for DAOs that deal with entities with {@link String} keys.
 *
 * @param <TE> The entity type
 */
public abstract class StringKeyedDAO<TE> extends BaseDAO<TE, String> {
    private final Class<TE> entityClass;

    protected StringKeyedDAO(Class<TE> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public TE find(String key) {
        return entityManager.find(entityClass, key);
    }
}
