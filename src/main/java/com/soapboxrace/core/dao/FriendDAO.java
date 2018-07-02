package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.FriendEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class FriendDAO extends BaseDAO<FriendEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public List<FriendEntity> findByUserId(Long userId)
    {
        TypedQuery<FriendEntity> query = entityManager.createNamedQuery("FriendEntity.findByUser", FriendEntity.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public FriendEntity findBySenderAndRecipient(Long recipientUserId, Long senderId)
    {
        TypedQuery<FriendEntity> query = entityManager.createNamedQuery("FriendEntity.findBySenderAndRecipient", FriendEntity.class);
        query.setParameter("recipientUserId", recipientUserId);
        query.setParameter("senderId", senderId);
        List<FriendEntity> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }
}