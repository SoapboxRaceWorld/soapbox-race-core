package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class BanDAO extends BaseDAO<BanEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    public BanEntity findByIp(String ip)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :ip", BanEntity.class);
        query.setParameter("type", BanEntity.BanType.IP_BAN);
        query.setParameter("ip", ip);

        List<BanEntity> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }

    public BanEntity findByHWID(String hwid)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :hwid", BanEntity.class);
        query.setParameter("type", BanEntity.BanType.HWID_BAN);
        query.setParameter("hwid", hwid);

        List<BanEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public BanEntity findByEmail(String email)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :email", BanEntity.class);
        query.setParameter("type", BanEntity.BanType.EMAIL_BAN);
        query.setParameter("email", email);

        List<BanEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public BanEntity findByUser(UserEntity userEntity)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.userEntity = :user", BanEntity.class);
        query.setParameter("type", BanEntity.BanType.USER_BAN);
        query.setParameter("user", userEntity);

        List<BanEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
