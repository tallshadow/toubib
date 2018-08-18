package com.adouae.toubib.impl.persistence.dao;

import java.util.Date;
import java.util.List;

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


public interface UserDAO {
	public Long addUser(User user);
	public Long editClient( Client client);
	public void remove( Client client);
	public User getUser(String email, String password);
	public User getUser(String email);
	public List<Doctor> getAllDoctors(Doctor doctorDao);
	 public List<Slot> getAllSlot(Doctor doctor);
	// liste des Rv d'un médecin, un jour donné
	  public List<Appointment> getAppointmentDoctorDay(Doctor doctor, Date day);
	public void delete(Appointment rv);
	public Appointment addAppointment(Date day, Slot slot, Client client);
	public List<Client> getAllClients(Doctor doctor);
	public Long addClient(Client clientDao);
	public List<City> getCities(String idCountry);
	public void addDoctor();
	public void updateConsultation(Consultation consultation);
	public List<Reasons> getReasons();
	public List<Examinations> getExaminations();
	public List<Diagnostics> getDiagnostics();
	public void updateReasons(Reasons reasonDao);
	public void updateExaminations(Examinations examinationsDao);
	void removeReason(Reasons rs);
	public void removeExamination(Examinations e);
	public void removeDiagnostic(Diagnostics d);
	public void updateDiagnostics(Diagnostics diagnosticsDao);
	public List<Consultation> getConsultation(Client clientDTO, User userDao);
	void removeConsultation(int id);
	public List<Date> getConsultationDate(User doctorDao, Client clientDao);
	public void changeUserPassword(User userDao);
}
