package com.adouae.toubib.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.domain.dto.AppointmentDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.dao.ScheduleDAO;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.itf.domain.service.ManageSchedule;

@Service("manageSchedule")
@Transactional
public class ManageScheduleImp implements ManageSchedule {

	@Autowired
	private ScheduleDAO scheduleDAO;

	@Override
	public void addAppointment(AppointmentDTO appointment) {

		Appointment appDao = new Appointment();

		BeanUtils.copyProperties(appointment, appDao);
		BeanUtils.copyProperties(appointment.getSlot(), appDao.getSlot());
		BeanUtils.copyProperties(appointment.getSlot().getDoctor(), appDao.getSlot().getDoctor());
		BeanUtils.copyProperties(appointment.getClient(), appDao.getClient());

		
		scheduleDAO.addAppointment(appDao);

	}

	@Override
	public Boolean checkAppointment(Appointment appDao){
		
		if(scheduleDAO.checkAppointment(appDao) != null){
			return true;
		}
		
		return false;
	}
	@Override
	public List<AppointmentDTO> getAppointments(UserDTO currentUser) {
		Doctor doctor = new Doctor();
		BeanUtils.copyProperties(currentUser, doctor);

		List<Appointment> listAppDao = scheduleDAO.getAppointments(doctor);
		List<AppointmentDTO> listAppDto = new ArrayList<AppointmentDTO>();

		for (Appointment appDao : listAppDao) {

			AppointmentDTO appDto = new AppointmentDTO();

			BeanUtils.copyProperties(appDao, appDto);
			BeanUtils.copyProperties(appDao.getSlot(), appDto.getSlot());
			BeanUtils.copyProperties(appDao.getSlot().getDoctor(), appDto.getSlot().getDoctor());
			BeanUtils.copyProperties(appDao.getClient(), appDto.getClient());

			listAppDto.add(appDto);

		}

		return listAppDto;
	}

	@Override
	public List<AppointmentDTO> getAppointmentsClts(UserDTO currentUser, ClientDTO selectedClient) {
		
		Doctor doctor = new Doctor();
		Client clt = new Client();
		
		if(currentUser != null && selectedClient != null){
		BeanUtils.copyProperties(currentUser, doctor);
		BeanUtils.copyProperties(selectedClient, clt);
		}
		
		List<Appointment> listAppDao = scheduleDAO.getAppointmentsClt(doctor, clt);
		List<AppointmentDTO> listAppDto = new ArrayList<AppointmentDTO>();

		for (Appointment appDao : listAppDao) {

			AppointmentDTO appDto = new AppointmentDTO();

			BeanUtils.copyProperties(appDao, appDto);
			BeanUtils.copyProperties(appDao.getSlot(), appDto.getSlot());
			BeanUtils.copyProperties(appDao.getSlot().getDoctor(), appDto.getSlot().getDoctor());
			BeanUtils.copyProperties(appDao.getClient(), appDto.getClient());

			listAppDto.add(appDto);

		}

		return listAppDto;
	}
	
	@Override
	public void deleteApp(AppointmentDTO appointmentDTO) {

		Appointment appDao = new Appointment();

		BeanUtils.copyProperties(appointmentDTO, appDao);
		BeanUtils.copyProperties(appointmentDTO.getSlot(), appDao.getSlot());
		BeanUtils.copyProperties(appointmentDTO.getSlot().getDoctor(), appDao.getSlot().getDoctor());
		BeanUtils.copyProperties(appointmentDTO.getClient(), appDao.getClient());

		scheduleDAO.deleteApp(appDao);
	}

	@Override
	public void updateApp(AppointmentDTO appointmentDTO) {
		Appointment appDao = new Appointment();

		BeanUtils.copyProperties(appointmentDTO, appDao);
		BeanUtils.copyProperties(appointmentDTO.getSlot(), appDao.getSlot());
		BeanUtils.copyProperties(appointmentDTO.getSlot().getDoctor(), appDao.getSlot().getDoctor());
		BeanUtils.copyProperties(appointmentDTO.getClient(), appDao.getClient());
		
		scheduleDAO.updateApp(appDao);
	}

	

}
