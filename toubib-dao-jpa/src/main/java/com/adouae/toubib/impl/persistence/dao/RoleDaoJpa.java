package com.adouae.toubib.impl.persistence.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adouae.toubib.impl.persistence.entity.Role;

@Repository("roleDao")
public class RoleDaoJpa implements RoleDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserDAO userDao;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Integer addRole(Role role) {
		
		try{
			if (userDao.getUser(role.getUser().getEmail()) == null) {
				entityManager.persist(role);
			}else{
				entityManager.merge(role);
			}
	    }
	    catch(EntityExistsException e){
	    	System.out.println("user already exist");
	    }
		return role.getId();
	}

	//getters & setters
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	
}
