package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

@Stateless
public class InventoryItemDAO extends BaseDAO<InventoryItemEntity> {
	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public InventoryItemEntity findByEntitlementTagAndPersona(Long personaId, String entitlementTag) {
		TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity.findByPersonaEntitlEmentId", InventoryItemEntity.class);
		query.setParameter("personaId", personaId);
		query.setParameter("entitlementTag", entitlementTag);
		List<InventoryItemEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

	public List<InventoryItemEntity> findListByEntitlementTagAndPersona(String entitlementTag, PersonaEntity personaEntity) {
		TypedQuery<InventoryItemEntity> query = entityManager
				.createQuery("SELECT obj FROM InventoryItemEntity obj WHERE obj.entitlementTag = :tag AND obj.persona = :persona", InventoryItemEntity.class);
		query.setParameter("tag", entitlementTag);
		query.setParameter("persona", personaEntity);

		List<InventoryItemEntity> list = query.getResultList();

		return list;
	}

	public void deleteByPersona(Long personaId) {
		Query query = entityManager.createNamedQuery("InventoryItemEntity.deleteByPersona");
		query.setParameter("personaId", personaId);
		query.executeUpdate();
	}

	public InventoryItemEntity findByHashAndPersona(Long personaId, Integer hash) {
		TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity.findByPersonaHash", InventoryItemEntity.class);
		query.setParameter("personaId", personaId);
		query.setParameter("hash", hash);
		List<InventoryItemEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
}
