package com.adouae.toubib.impl.persistence.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.adouae.toubib.exceptions.AppointmentDoctorException;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Folder;


@Repository("folderDao")
public class FolderDaoJpa implements FolderDAO {
	
	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllergys() {
		return entityManager.createQuery("select a from Allergy a").getResultList();
	}


	//getters & setters
	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getVaccines() {
		return entityManager.createQuery("select v from Vaccine v").getResultList();
	}


	@Override
	@SuppressWarnings("rawtypes")
	public void updateFolder(Folder folderDao) {
		
		try{
			System.out.println("upppp");
			
			List results =  entityManager.createQuery("select f.id from Folder f join f.client c join f.doctor d where c.id=:idClient and d.id=:idDoctor").setParameter("idClient", folderDao.getClient().getId()).setParameter("idDoctor", folderDao.getDoctor().getId()).getResultList();

			if(!results.isEmpty()){
				folderDao.setId((Long) results.get(0));
				entityManager.merge(folderDao);

			}else{

				entityManager.merge(folderDao);			}
	    }
	    catch(EntityExistsException e){
	        //Entity you are trying to insert already exist, then call merge method
	    	 System.out.println("folder already exist");
	    }
	}


	 
	@Override
	public Folder getFolder(Client client, Doctor doctor) {
		try {
			@SuppressWarnings("rawtypes")
			List results = entityManager.createQuery("select f from Folder f join f.client c join f.doctor d where c.id=:idClient and d.id=:idDoctor").setParameter("idClient", client.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
			if(!results.isEmpty()){
				return (Folder) results.get(0);
			}
			return  null;

		
		} catch (Throwable th) {
		      throw new AppointmentDoctorException(th, 3);
		    }

	}
	
	
	

}
