package com.adouae.toubib.impl.persistence.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.adouae.toubib.exceptions.AppointmentDoctorException;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;

@Repository("scheduleDao")
public class ScheduleDaoJpa implements ScheduleDAO {

	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public void addAppointment(Appointment appDao) {
		try {
			
			System.out.println("appDao" +appDao);
			System.out.println("check ****" +checkAppointment(appDao)); 

			if(checkAppointment(appDao) == null){
				
				System.out.println("app not exist" +appDao); 
 				entityManager.persist(appDao);
			}else{
				System.out.println("app exist" +appDao);
				appDao.setId(checkAppointment(appDao).getId());
				entityManager.merge(appDao);
			}

		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 4);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointments(Doctor doctor) {
		try {
	        return entityManager.createQuery("select rv from Appointment rv join rv.slot c join c.doctor m where m.id=:idDoctor").setParameter("idDoctor", doctor.getId()).getResultList();
	      } catch (Throwable th) {
	        throw new AppointmentDoctorException(th, 3);
	      }
	}
	
	@Override
	public Appointment checkAppointment(Appointment appDao) {
		try {
			@SuppressWarnings("unchecked")
			List<Appointment> results = entityManager.createQuery("select app from Appointment app join app.client c join app.slot s join s.doctor d where c.id=:idClient and s.startDate =:startDate and d.id=:idDoctor").setParameter("startDate", appDao.getSlot().getStartDate()).setParameter("idClient", appDao.getClient().getId()).setParameter("idDoctor", appDao.getSlot().getDoctor().getId()).getResultList();
	   
	        if (!results.isEmpty()) {
	        	Appointment app = new Appointment();
				app = results.get(0);
				return app;
			}
	        return null; 
	      } catch (Throwable th) {
	        throw new AppointmentDoctorException(th, 3);
	      }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsClt(Doctor doctor, Client clt) {
		try {
			
			Date day = Calendar.getInstance().getTime();
	        return entityManager.createQuery("select rv from Appointment rv join rv.client c join rv.slot s join s.doctor d where s.startDate >=:day and c.id=:idClient and d.id=:idDoctor").setParameter("idClient", clt.getId()).setParameter("idDoctor", doctor.getId()).setParameter("day", day).getResultList();
	        
	      } catch (Throwable th) {
	        throw new AppointmentDoctorException(th, 3);
	      }
	}
	
	@Override
	public void deleteApp(Appointment appDao) {
		try {
			
			entityManager.remove(entityManager.merge(appDao));
		    } catch (Throwable th) {
		      throw new AppointmentDoctorException(th,5);
		    }
		
	}

	@Override
	public void updateApp(Appointment appDao) {
		entityManager.merge(appDao);
	}

}
