package com.adouae.toubib.impl.persistence.dao;

import java.util.List;

import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;

public interface ScheduleDAO {

	void addAppointment(Appointment appDao);

	List<Appointment> getAppointments(Doctor doctor);

	void deleteApp(Appointment appDao);

	void updateApp(Appointment appDao);

	List<Appointment> getAppointmentsClt(Doctor doctor, Client clt);

	Appointment checkAppointment(Appointment appDao);

}
