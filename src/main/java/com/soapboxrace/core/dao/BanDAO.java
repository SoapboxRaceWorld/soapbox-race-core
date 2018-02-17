package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;

@Stateless
public class BanDAO extends BaseDAO<BanEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    private BanEntity findByTypeAndData(BanEntity.BanType type, String data)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj " +
                "WHERE obj.type = :type AND obj.data = :data AND (" +
                "   obj.endsAt is null OR CURRENT_TIMESTAMP < obj.endsAt" +
                ") ORDER BY obj.id DESC", BanEntity.class);
        query.setParameter("type", type);
        query.setParameter("data", data);
        
        List<BanEntity> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(results.size() - 1);
    }
    
    public BanEntity findByIp(String ip)
    {
        return findByTypeAndData(BanEntity.BanType.IP_BAN, ip);
    }

    public BanEntity findByEmail(String email)
    {
        return findByTypeAndData(BanEntity.BanType.EMAIL_BAN, email);
    }

    public BanEntity findByHWID(String hwid)
    {
        return findByTypeAndData(BanEntity.BanType.HWID_BAN, hwid);
    }
    
//    public BanEntity findByIp(String ip)
//    {
//        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :ip", BanEntity.class);
//        query.setParameter("type", BanEntity.BanType.IP_BAN);
//        query.setParameter("ip", ip);
//
//        List<BanEntity> results = query.getResultList();
//        
//        return results.isEmpty() ? null : results.get(0);
//    }
//
//    public BanEntity findByHWID(String hwid)
//    {
//        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :hwid", BanEntity.class);
//        query.setParameter("type", BanEntity.BanType.HWID_BAN);
//        query.setParameter("hwid", hwid);
//
//        List<BanEntity> results = query.getResultList();
//
//        return results.isEmpty() ? null : results.get(0);
//    }
//
//    public BanEntity findByEmail(String email)
//    {
//        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.data = :email", BanEntity.class);
//        query.setParameter("type", BanEntity.BanType.EMAIL_BAN);
//        query.setParameter("email", email);
//
//        List<BanEntity> results = query.getResultList();
//
//        return results.isEmpty() ? null : results.get(0);
//    }
//
    public BanEntity findByUser(UserEntity userEntity)
    {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.type = :type AND obj.userEntity = :user", BanEntity.class);
        query.setParameter("type", BanEntity.BanType.USER_BAN);
        query.setParameter("user", userEntity);

        List<BanEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
