package com.csmtech.SJTA.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LandApplicantLoginClassRepository {

	@PersistenceContext
	@Autowired
	private EntityManager entityManager;


	@Transactional
	public List<Long> findRoleIdsByUserId(Long userId) {
	    String sql = "SELECT role_id FROM user_role WHERE user_id = :userId";
	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("userId", userId);
	    @SuppressWarnings("unchecked")
	    List<Long> roleIds = query.getResultList();
	    return roleIds;
	}
}
