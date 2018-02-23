package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

@Stateless
public class CarSlotDAO extends BaseDAO<CarSlotEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public CarSlotEntity findById(Long id) {
		return entityManager.find(CarSlotEntity.class, id);
	}

	public List<CarSlotEntity> findByPersonaId(Long personaId) {
		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setPersonaId(personaId);

		TypedQuery<CarSlotEntity> query = entityManager.createNamedQuery("CarSlotEntity.findByPersonaId", CarSlotEntity.class);
		query.setParameter("persona", personaEntity);
		return query.getResultList();
	}

	public void deleteByPersona(PersonaEntity personaEntity) {
		Query query = entityManager.createNamedQuery("CarSlotEntity.deleteByPersona");
		query.setParameter("persona", personaEntity);
		query.executeUpdate();
	}

}
