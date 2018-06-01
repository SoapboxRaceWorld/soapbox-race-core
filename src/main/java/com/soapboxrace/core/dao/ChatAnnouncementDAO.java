package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ChatAnnouncementEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ChatAnnouncementDAO extends BaseDAO<ChatAnnouncementEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public List<ChatAnnouncementEntity> findAll()
    {
        return entityManager.createNamedQuery("ChatAnnouncementEntity.findAll", ChatAnnouncementEntity.class)
                .getResultList();
    }

    public ChatAnnouncementEntity findById(int id)
    {
        return entityManager.find(ChatAnnouncementEntity.class, id);
    }
}
