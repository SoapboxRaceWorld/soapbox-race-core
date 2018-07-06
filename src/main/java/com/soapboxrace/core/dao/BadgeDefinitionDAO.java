package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BadgeDefinitionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BadgeDefinitionDAO extends BaseDAO<BadgeDefinitionEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public BadgeDefinitionEntity findById(Long id)
    {
        return entityManager.find(BadgeDefinitionEntity.class, id);
    }
    
    public List<BadgeDefinitionEntity> getAll()
    {
        return entityManager.createQuery(
                "SELECT obj FROM BadgeDefinitionEntity obj",
                BadgeDefinitionEntity.class
        ).getResultList();
    }
}
