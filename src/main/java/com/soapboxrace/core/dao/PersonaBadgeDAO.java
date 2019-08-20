package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PersonaBadgeEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaBadgeDAO extends BaseDAO<PersonaBadgeEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<PersonaBadgeEntity> findAllBadgesForPersona(Long personaId) {
        return this.entityManager.createNamedQuery("PersonaBadgeEntity.findAllBadgesForPersona",
                PersonaBadgeEntity.class)
                .setParameter("personaId", personaId)
                .getResultList();
    }

    public PersonaBadgeEntity findBadgeInSlotForPersona(Long personaId, Integer slot) {
        TypedQuery<PersonaBadgeEntity> query = this.entityManager.createNamedQuery("PersonaBadgeEntity" +
                ".findBadgeInSlotForPersona", PersonaBadgeEntity.class)
                .setParameter("personaId", personaId)
                .setParameter("slot", slot);
        List<PersonaBadgeEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
