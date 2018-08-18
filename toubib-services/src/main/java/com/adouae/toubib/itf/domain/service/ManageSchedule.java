package com.adouae.toubib.itf.domain.service;

import java.util.List;

import com.adouae.toubib.domain.dto.AppointmentDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.entity.Appointment;

public interface ManageSchedule {

	void addAppointment(AppointmentDTO appointment);

	List<AppointmentDTO> getAppointments(UserDTO currentUser);

	void deleteApp(AppointmentDTO appointmentDTO);

	void updateApp(AppointmentDTO appointmentDTO);

	List<AppointmentDTO> getAppointmentsClts(UserDTO currentUser, ClientDTO selectedClient);

	Boolean checkAppointment(Appointment appDao);

}
