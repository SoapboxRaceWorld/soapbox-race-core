package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soapboxrace.core.jpa.UserEntity;

@Stateless
public class UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void selectAll() {
		System.out.println(entityManager);
		// TypedQuery<UserEntity> createNamedQuery = entityManager.createNamedQuery("", UserEntity.class);
		// List<UserEntity> resultList = createNamedQuery.getResultList();
	}

	public void insert(UserEntity userEntity) {
		entityManager.persist(userEntity);
	}

}
