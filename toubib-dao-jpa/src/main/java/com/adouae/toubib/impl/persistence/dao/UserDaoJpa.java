package com.adouae.toubib.impl.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.adouae.toubib.exceptions.AppointmentDoctorException;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.City;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Consultation;
import com.adouae.toubib.impl.persistence.entity.Diagnostics;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Examinations;
import com.adouae.toubib.impl.persistence.entity.Reasons;
import com.adouae.toubib.impl.persistence.entity.Slot;
import com.adouae.toubib.impl.persistence.entity.User;

@Repository("clientDao")
public class UserDaoJpa implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Long addUser(User user) {
		Doctor doctor = new Doctor();
		BeanUtils.copyProperties(user, doctor);
		try{
			entityManager.merge(doctor);
//		if (getUser(doctor.getEmail()) == null) {
//			entityManager.persist(doctor);
//		}else{
//			entityManager.merge(doctor);
//		}
		}catch(Throwable th){
			throw new AppointmentDoctorException(th, 4);
        }
		return doctor.getId();
	}

	@Override
	public Long editClient(Client client) {
		entityManager.merge(client);
		return client.getId();
	}

	@Override
	public void remove(Client client) {
		// TODO Auto-generated method stub

	}

	@Override
	@SuppressWarnings("unchecked")
	public User getUser(String email, String password) {
		List<User> results = entityManager
				.createQuery("Select user from User as user where email=:email and password=:password")
				.setParameter("email", email).setParameter("password", password).getResultList();
		if (!results.isEmpty()) {
			User user = new User();
			user = results.get(0);
			return user;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public User getUser(String email) {
		List<User> results = entityManager.createQuery("Select user from User as user where email=:email")
				.setParameter("email", email).getResultList();
		if (!results.isEmpty()) {
			User user = new User();
			user = results.get(0);
			return user;
		}
		return null;
	}

	// Doctors
	@SuppressWarnings("unchecked")
	@Override
	public List<Doctor> getAllDoctors(Doctor doctor) {
		return entityManager.createQuery("select rm from Doctor rm  where rm.id=:idDoctor")
				.setParameter("idDoctor", doctor.getId()).getResultList();
		// return entityManager.createQuery("select rc from Slot rc join
		// rc.doctor m where m.id=:idDoctor").setParameter("idDoctor",
		// 70L).getResultList();
		// return entityManager.createQuery("select rc from Slot rc join
		// rc.doctor m where m.id=:idDoctor").setParameter("idDoctor",
		// 70L).getResultList();
	}

	public void addDoctor() {
		Doctor d = new Doctor();
		entityManager.persist(d);
	}

	// liste des créneaux horaires d'un médecin donné
	// medecin : le médecin
	@SuppressWarnings("unchecked")
	public List<Slot> getAllSlot(Doctor doctor) {
		try {
			return entityManager.createQuery("select rc from Slot rc join rc.doctor m where m.id=:idDoctor")
					.setParameter("idDoctor", doctor.getId()).getResultList();
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 3);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentDoctorDay(Doctor doctor, Date day) {
		try {
			return entityManager
					.createQuery(
							"select rv from Appointment rv join rv.slot c join c.doctor m where m.id=:idDoctor and rv.day=:day")
					.setParameter("idDoctor", doctor.getId()).setParameter("day", day).getResultList();
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 3);
		}
	}

	@Override
	public void delete(Appointment rv) {
		try {

			entityManager.remove(entityManager.merge(rv));
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 5);
		}
	}

	@Override
	public Appointment addAppointment(Date day, Slot slot, Client client) {
		try {
			Appointment rv = new Appointment(null, day);
			rv.setClient(client);
			rv.setSlot(slot);
			System.out.println(String.format("avant persist : %s", rv));
			entityManager.persist(rv);
			System.out.println(String.format("après persist : %s", rv));
			return rv;
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 4);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAllClients(Doctor doctor) {
		try {
			List<Client> clients = entityManager
					.createQuery("select c from Client c join c.doctor d where d.id=:idDoctor")
					.setParameter("idDoctor", doctor.getId()).getResultList();

			for (Client clt : clients) {
				Hibernate.initialize(clt.getAddress());
			}

			return clients;
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 1);
		}
	}

	@Override
	public Long addClient(Client client) {
		// entityManager.persist(client);
		entityManager.merge(client);
		// if (!entityManager.contains(client)) {
		// entityManager.persist(client);
		// } else {
		// entityManager.merge(client);
		// }
		return client.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCities(String country) {
		return entityManager.createQuery("select rm.cities from Country rm where rm.country =:country")
				.setParameter("country", country).getResultList();
	}

	// Consultation
	@Override
	public void updateConsultation(Consultation consultation) {
		// TODO Auto-generated method stub
		entityManager.merge(consultation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reasons> getReasons() {
		return entityManager.createQuery("select rs from Reasons rs").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Examinations> getExaminations() {
		return entityManager.createQuery("select e from Examinations e").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Diagnostics> getDiagnostics() {
		return entityManager.createQuery("select d from Diagnostics d").getResultList();
	}

	@Override
	public void removeReason(Reasons rs) {
		try {

			entityManager.remove(entityManager.merge(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateReasons(Reasons reasonDao) {
		entityManager.merge(reasonDao);
	}

	@Override
	public void updateExaminations(Examinations examinationsDao) {
		entityManager.merge(examinationsDao);
	}

	@Override
	public void removeExamination(Examinations e) {
		try {
			entityManager.remove(entityManager.merge(e));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void removeDiagnostic(Diagnostics d) {
		try {
			entityManager.remove(entityManager.merge(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateDiagnostics(Diagnostics diagnosticsDao) {
		entityManager.merge(diagnosticsDao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consultation> getConsultation(Client client, User doctor) {
		try {
			return entityManager
					.createQuery(
							"select cs from Consultation cs join cs.client c join c.doctor d where c.id=:idClient and d.id=:idDoctor")
					.setParameter("idClient", client.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 3);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getConsultationDate(User doctor, Client clt) {
		try {
			return entityManager
					.createQuery(
							"select cs.day from Consultation cs join cs.client c join cs.doctor d where c.id=:idClient and d.id=:idDoctor")
					.setParameter("idClient", clt.getId()).setParameter("idDoctor", doctor.getId()).getResultList();
		} catch (Throwable th) {
			throw new AppointmentDoctorException(th, 3);
		}
	}

	@Override
	public void removeConsultation(int id) {
		try {
			Consultation c = entityManager.find(Consultation.class, id);
			entityManager.remove(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeUserPassword(User userDao) {
		Doctor doctor = new Doctor();
		BeanUtils.copyProperties(userDao, doctor);
		entityManager.merge(doctor);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
