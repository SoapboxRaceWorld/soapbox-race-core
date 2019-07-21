package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.VinylEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class VinylDAO extends BaseDAO<VinylEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteByCustomCar(CustomCarEntity customCarEntity) {
        Query query = entityManager.createNamedQuery("VinylEntity.deleteByCustomCar");
        query.setParameter("customCar", customCarEntity);
        query.executeUpdate();
    }

}
