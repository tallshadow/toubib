package com.adouae.toubib.itf.domain.service;

import java.util.Date;
import java.util.List;

import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.ConsultationDTO;
import com.adouae.toubib.domain.dto.DiagnosticsDTO;
import com.adouae.toubib.domain.dto.ExaminationsDTO;
import com.adouae.toubib.domain.dto.ReasonsDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.City;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Diagnostics;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Examinations;
import com.adouae.toubib.impl.persistence.entity.PasswordResetToken;
import com.adouae.toubib.impl.persistence.entity.Reasons;
import com.adouae.toubib.impl.persistence.entity.Slot;

public interface ManageUsers {
	
	public Long addUser(UserDTO clt, int flag);
	public Long addClient(ClientDTO clt);
	public boolean login(String email, String password);
	public UserDTO getUserDto(String email, String password);
	public UserDTO getUserDto(String email);
	public UserDTO getUserDtoByMail(String email);
	public boolean checkUser(String email);
	// liste des Médecins
	public List<Doctor> getAllDoctors(UserDTO userDTO);
	public void delete(Appointment rv);
	public Appointment addAppointment(Date day, Slot slot, Client client);
	public List<ClientDTO> getAllClients(UserDTO userDTO);
	public boolean getError();
	public List<com.adouae.toubib.tools.Error> getErrors();
	public List<City> getCities(String string);
	public Long editClient(ClientDTO selectedClient);
	public void updateConsultation(ConsultationDTO consultationDto);
	public List<Reasons> getReasons();
	public List<Examinations> getExaminations();
	public List<Diagnostics> getDiagnostics();
	public void updateReasons(ReasonsDTO reasonsDto);
	public void updateExaminations(ExaminationsDTO examinationsDto);
	public void removeReason(Reasons rs);
	public void removeExamination(Examinations e);
	public void removeDiagnostic(Diagnostics d);
	public void updateDiagnostics(DiagnosticsDTO diagnosticsDto);
	public List<ConsultationDTO> getConsultation(ClientDTO selectedClient, UserDTO userDTO);
	public List<Date> getConsultationDate(UserDTO doctorDTO, ClientDTO selectedClient);
	public void removeConsultation(int id);
	//reset password
	void createVerificationTokenForUser(UserDTO user, String token);
	void createPasswordResetTokenForUser(UserDTO user, String token);
	PasswordResetToken getPasswordResetToken(String token);
//	public void sendPasswordResetMail(UserDTO user, String serverString);
//	public void initiateResetPassword(UserDTO user);
	public boolean checkIfValidOldPassword(UserDTO user, String passwordDto);
	public void changeUserPassword(UserDTO user, String newPassword);
	public void savePassword(UserDTO user, String newPassword);
}
