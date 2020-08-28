package com.soapboxrace.core.dao.util;

/**
 * Base class for DAOs that deal with entities with {@link Long} keys.
 *
 * @param <TE>
 */
public abstract class LongKeyedDAO<TE> extends BaseDAO<TE, Long> {
    private final Class<TE> entityClass;

    protected LongKeyedDAO(Class<TE> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public TE find(Long key) {
        return entityManager.find(entityClass, key);
    }
}
