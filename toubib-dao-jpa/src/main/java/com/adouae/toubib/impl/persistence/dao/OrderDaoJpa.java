package com.adouae.toubib.impl.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.adouae.toubib.exceptions.AppointmentDoctorException;
import com.adouae.toubib.impl.persistence.entity.Biology;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Drug;
import com.adouae.toubib.impl.persistence.entity.Order;
import com.adouae.toubib.impl.persistence.entity.Payment;
import com.adouae.toubib.impl.persistence.entity.Radiology;

@Repository("orderDao")
public class OrderDaoJpa implements OrderDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Drug> getDrugs() {
		return entityManager.createQuery("select d from Drug d").getResultList();
	}

	@Override
	public void removeDrug(Drug d) {
		try {
			entityManager.remove(entityManager.merge(d));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	@Override
	public void updateDrug(Drug draugDao) {
		try {
			entityManager.merge(entityManager.merge(draugDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Biology>  getBiologies() {
		return entityManager.createQuery("select b from Biology b").getResultList();
	}
	
	@Override
	public void updateBiologies(Biology biologyDao) {
		try {
			entityManager.merge(entityManager.merge(biologyDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	@Override
	public void removeBiology(Biology biologygDao) {
		try {
			entityManager.remove(entityManager.merge(biologygDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	//getters and setters
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Radiology>  getRadiology() {
		return entityManager.createQuery("select b from Radiology b").getResultList();
	}
	
	@Override
	public void updateRadiology(Radiology radiologyDao) {
		try {
			entityManager.merge(entityManager.merge(radiologyDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
	
	@Override
	public void removeRadiology(Radiology radiologygDao) {
		try {
			entityManager.remove(entityManager.merge(radiologygDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}

	// get Order/ client
	@Override
	@SuppressWarnings("unchecked")
	public List<Order> getOrder(Doctor doctor, Client client) {
		try {
		      return entityManager.createQuery("select o from Order o join o.client c join o.doctor d where c.id=:idClient and d.id=:idDoctor").setParameter("idClient", client.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
		    } catch (Throwable th) {
		      throw new AppointmentDoctorException(th, 3);
		    }
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Date> getOrderDate(Doctor doctor, Client client) {
		try {
		      return entityManager.createQuery("select o.day from Order o join o.client c join o.doctor d where c.id=:idClient and d.id=:idDoctor").setParameter("idClient", client.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
		    } catch (Throwable th) {
		      throw new AppointmentDoctorException(th, 3);
		    }
	}

	@Override
	public void removeOrder(int id) {
		try {
			Order o = entityManager.find(Order.class, id);
			entityManager.remove(o);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}

	@Override
	public void updateOrder(Order orderDao) {

		entityManager.merge(orderDao);
		
	}

	@Override
	public void updatePayment(Payment paymentDao) {
		entityManager.merge(paymentDao);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Payment> getPayments(Client client, Doctor doctor) {
		
		try {
		      return entityManager.createQuery("select p from Payment p join p.client c join p.doctor d where c.id=:idClient and d.id=:idDoctor").setParameter("idClient", client.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
		    } catch (Throwable th) {
		      throw new AppointmentDoctorException(th, 3);
		    }
	}

	@Override
	public void removeFee(Payment pDao) {
		try {
			entityManager.remove(entityManager.merge(pDao));
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	}
}
