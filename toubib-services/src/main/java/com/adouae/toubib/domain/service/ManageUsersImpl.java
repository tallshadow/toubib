package com.adouae.toubib.domain.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.ConsultationDTO;
import com.adouae.toubib.domain.dto.DiagnosticsDTO;
import com.adouae.toubib.domain.dto.ExaminationsDTO;
import com.adouae.toubib.domain.dto.ReasonsDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.dao.PasswordResetTokenRepository;
import com.adouae.toubib.impl.persistence.dao.RoleDAO;
import com.adouae.toubib.impl.persistence.dao.UserDAO;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.City;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Consultation;
import com.adouae.toubib.impl.persistence.entity.Diagnostics;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Examinations;
import com.adouae.toubib.impl.persistence.entity.PasswordResetToken;
import com.adouae.toubib.impl.persistence.entity.Reasons;
import com.adouae.toubib.impl.persistence.entity.Slot;
import com.adouae.toubib.impl.persistence.entity.User;
import com.adouae.toubib.impl.persistence.entity.VerificationToken;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.tools.Error;

@Service("manageUser")
@Transactional
@EnableJpaRepositories("com.adouae.*")
@ComponentScan( basePackages = "com.adouae.toubib" )
@EnableWebMvcSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ManageUsersImpl implements ManageUsers {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	/**
	 * Properties for activation mail.
	 */
	private Configuration config;

	// @Autowired
	// private VerificationTokenRepository tokenRepository;w

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// errors
	private List<Error> errors = new ArrayList<Error>();
	private Boolean error = false;

	public ManageUsersImpl() {
		super();
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setClientDAO(UserDAO clientDAO) {
		this.userDAO = clientDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Long addUser(UserDTO userDto, int flag) {
		//String pass = springEncoderMd5(userDto.getPassword(), flag);
		String pass = passwordEncoder.encode(userDto.getPassword());
		User userDao = new User();

		userDto.setPassword(pass);

		if (flag != -1) {
			userDto.setStatut("ACTIVE");
		} else {
			userDto.setStatut("NON_ACTIVE");

		}

		BeanUtils.copyProperties(userDto, userDao);

		//Role role = new Role(userDao, "ROLE_USER");
	
		userDAO.addUser(userDao);
		//TO DO ** prb duplicate user!
		//roleDAO.addRole(role);
		return userDao.getId();
	}

	@Override
	public UserDTO getUserDto(String email, String password) {

		UserDTO cltDto = new UserDTO();
		BeanUtils.copyProperties(userDAO.getUser(email, password), cltDto);
		return cltDto;
	}

	// get user by mail
	public UserDTO getUserDtoByMail(String email) {

		UserDTO cltDto = new UserDTO();
		if(userDAO.getUser(email) != null){
		BeanUtils.copyProperties(userDAO.getUser(email), cltDto);
		return cltDto;
		}
		
		return null;
	}

	public String springEncoderMd5(String mot, int flag) {

		if (flag != -1) {
			MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			messageDigest.update(mot.getBytes(), 0, mot.length());
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return hashedPass;
		} else
			return null;
	}

	@Override
	public UserDTO getUserDto(String email) {
		UserDTO cltDto = new UserDTO();
		if(userDAO.getUser(email) != null){
		BeanUtils.copyProperties(userDAO.getUser(email), cltDto);
		}
		return cltDto;
	}

	@Override
	public boolean login(String email, String password) {
		if (userDAO.getUser(email, password) != null) {
			return true;
		}
		return false;
	}

	public boolean checkUser(String email) {

		if (userDAO.getUser(email) == null) {
			return false;
		} else {
			return true;
		}
	}

	// list of doctors
	@Override
	public List<Doctor> getAllDoctors(UserDTO docorDto) {

		Doctor doctorDao = new Doctor();

		BeanUtils.copyProperties(docorDto, doctorDao);

		return userDAO.getAllDoctors(doctorDao);

	}

	@Override
	public void delete(Appointment rv) {
		userDAO.delete(rv);
	}

	@Override
	public Appointment addAppointment(Date day, Slot slot, Client client) {
		return userDAO.addAppointment(day, slot, client);

	}

	@Override
	public List<ClientDTO> getAllClients(UserDTO doctor) {
		Doctor doctorDao = new Doctor();
		BeanUtils.copyProperties(doctor, doctorDao);

		List<ClientDTO> cltsDto = new ArrayList<ClientDTO>();

		for (Client c : userDAO.getAllClients(doctorDao)) {

			ClientDTO cltDto = new ClientDTO();
			
			for (Doctor d : c.getDoctor()) {
				UserDTO doctorDto = new UserDTO();
				BeanUtils.copyProperties(d, doctorDto);
				cltDto.getDoctors().add(doctorDto);
			}
			
			BeanUtils.copyProperties(c, cltDto);
			cltsDto.add(cltDto);
		}

		return cltsDto;
	}

	@Override
	public boolean getError() {
		return error;
	}

	@Override
	public List<Error> getErrors() {
		return errors;
	}

	@Override
	public Long addClient(ClientDTO clientDto) {

		Client clientDao = new Client();
		BeanUtils.copyProperties(clientDto, clientDao);


		for (UserDTO cltDto : clientDto.getDoctors()) {
			Doctor doctor = new Doctor();
			BeanUtils.copyProperties(cltDto, doctor);
			clientDao.getDoctor().add(doctor);
		}

		System.out.println("clientDto***" + clientDto);
		System.out.println("clientDao***" + clientDao.getDoctor());
		
		userDAO.addClient(clientDao);

		return clientDao.getId();
	}

	@Override
	public List<City> getCities(String idCountry) {
		return userDAO.getCities(idCountry);
	}

	@Override
	public Long editClient(ClientDTO clientDto) {
		System.out.println("clientDto" +clientDto);
		Client clientDao = new Client();
		BeanUtils.copyProperties(clientDto, clientDao);
		BeanUtils.copyProperties(clientDto.getAddress(), clientDao.getAddress());
		
		for (UserDTO doctorDto : clientDto.getDoctors()) {

			Doctor doctorDao = new Doctor();
			BeanUtils.copyProperties(doctorDto, doctorDao);
			clientDao.getDoctor().add(doctorDao);
		}

		System.out.println("clientDto" + clientDto);
		System.out.println("clientDao" + clientDao);

		userDAO.editClient(clientDao);

		return clientDao.getId();

	}

	// Consultation
	@Override
	public void updateConsultation(ConsultationDTO consultationDto) {
		Consultation consutlationDao = new Consultation();
		BeanUtils.copyProperties(consultationDto, consutlationDao);
		BeanUtils.copyProperties(consultationDto.getClient(), consutlationDao.getClient());
		BeanUtils.copyProperties(consultationDto.getDoctor(), consutlationDao.getDoctor());
		userDAO.updateConsultation(consutlationDao);

	}

	@Override
	public List<Reasons> getReasons() {
		return userDAO.getReasons();
	}

	@Override
	public List<Examinations> getExaminations() {
		return userDAO.getExaminations();
	}

	@Override
	public List<Diagnostics> getDiagnostics() {
		return userDAO.getDiagnostics();
	}

	@Override
	public void updateReasons(ReasonsDTO reasonsDto) {
		Reasons reasonDao = new Reasons();
		BeanUtils.copyProperties(reasonsDto, reasonDao);
		userDAO.updateReasons(reasonDao);

	}

	@Override
	public void updateExaminations(ExaminationsDTO examinationsDto) {
		Examinations examinationsDao = new Examinations();
		BeanUtils.copyProperties(examinationsDto, examinationsDao);

		userDAO.updateExaminations(examinationsDao);
	}

	@Override
	public void removeReason(Reasons rs) {
		userDAO.removeReason(rs);
	}

	@Override
	public void removeExamination(Examinations e) {
		userDAO.removeExamination(e);
	}

	@Override
	public void removeDiagnostic(Diagnostics d) {
		userDAO.removeDiagnostic(d);
	}

	@Override
	public void updateDiagnostics(DiagnosticsDTO diagnosticsDto) {
		Diagnostics diagnosticsDao = new Diagnostics();
		BeanUtils.copyProperties(diagnosticsDto, diagnosticsDao);
		userDAO.updateDiagnostics(diagnosticsDao);
	}

	@Override
	public List<ConsultationDTO> getConsultation(ClientDTO clientDTO, UserDTO userDTO) {

		Client clientDao = new Client();
		User userDao = new User();
		try {
			BeanUtils.copyProperties(clientDTO, clientDao);
			BeanUtils.copyProperties(userDTO, userDao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ConsultationDTO> consDto = new ArrayList<ConsultationDTO>();

		for (Consultation source : userDAO.getConsultation(clientDao, userDao)) {
			ConsultationDTO target = new ConsultationDTO();
			BeanUtils.copyProperties(source, target);

			consDto.add(target);
		}

		return consDto;
	}

	@Override
	public List<Date> getConsultationDate(UserDTO doctorDTO, ClientDTO clientDTO) {

		Client clientDao = new Client();
		User doctorDao = new User();
		try {
			BeanUtils.copyProperties(clientDTO, clientDao);
			BeanUtils.copyProperties(doctorDTO, doctorDao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userDAO.getConsultationDate(doctorDao, clientDao);
	}

	@Override
	public void removeConsultation(int id) {
		userDAO.removeConsultation(id);
	}

	@Override
	public void createVerificationTokenForUser(final UserDTO user, final String token) {
		User userDao = new User();
		BeanUtils.copyProperties(user, userDao);
		@SuppressWarnings("unused")
		final VerificationToken myToken = new VerificationToken(token, userDao);
		// tokenRepository.save(myToken);
	}

	@Override
	public void createPasswordResetTokenForUser(final UserDTO user, final String token) {

		User userDao = new User();
		BeanUtils.copyProperties(user, userDao);

		final PasswordResetToken myToken = new PasswordResetToken(token, userDao);
		passwordTokenRepository.save(myToken);

	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordTokenRepository.findByToken(token);
	}

	@Override
	public boolean checkIfValidOldPassword(final UserDTO user, final String oldPassword) {
		User userDao = new User();
		BeanUtils.copyProperties(user, userDao);
		return passwordEncoder.matches(oldPassword, userDao.getPassword());
	}
	
	 @Override
	    public void changeUserPassword(final UserDTO user, final String newPassword) {
	
		 	User userDao = new User();
		 	BeanUtils.copyProperties(user, userDao);
		 
		 	userDao.setPassword(passwordEncoder.encode(newPassword));
		 
	        userDAO.changeUserPassword(userDao);
	    }
	 
	 @Override
		public void savePassword(UserDTO user, String newPassword) {
		 
		 User userDao = new User();
		 BeanUtils.copyProperties(user, userDao);
			
		 userDao.setPassword(passwordEncoder.encode(newPassword));
		 userDAO.changeUserPassword(userDao);
		}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public PasswordResetTokenRepository getPasswordTokenRepository() {
		return passwordTokenRepository;
	}

	public void setPasswordTokenRepository(PasswordResetTokenRepository passwordTokenRepository) {
		this.passwordTokenRepository = passwordTokenRepository;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
