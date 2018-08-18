package com.adouae.toubib;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.impl.persistence.dao.UserDAO;
import com.adouae.toubib.impl.persistence.entity.Appointment;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Slot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)


public class Test {
	@Autowired
	//private ManageUsers dao ;
	
	//@Inject
	//private transient UserDTO user=null ;
	@Inject
	private transient UserDAO userDao=null ;
	
//	@Inject
//	private transient OrderDAO orderDao=null ;
//	
//	@Inject
//	private transient FolderDAO folderDao=null ;
//	
//	@Inject
//	private transient ScheduleDAO scheduleDao=null ;
	
//	@Inject
//	private transient Agenda agenda;
//	@Inject
//	private transient LoginBean loginbean;
//	@Inject
//	private transient SlotDoctorDay slotDactorDay;
//	@Inject
//	private transient Appointment appointment;
//	@Inject
//	private transient Slot slot;
//	@Inject
//	private transient Doctor doctor;
//	
	

	
	
//	@Inject
//	CustomUserDetailsService serv = null;
//	@Inject
//	CustomUserDetailsService login = null;
	//@Inject
	//Parametres p =null;
	
	@org.junit.Test
	@Transactional(readOnly=true)
	  public void testGetNameStartsWith() throws IOException {
		//System.out.println("uderdao"+dao.getUserDto("toto@toto.fr").getFirstName());
		//userDao.addDoctor();
//		Client user = new Client();
//		user.setId(103L);
		Client c = new Client();
		Doctor d = new Doctor();
		d.setId(1L);
		c.setId(2L);
		//Folder folder =  new Folder();
		//folderDao.updateFolder(folder);
		Appointment app = new Appointment();
		Slot slot = new Slot();
		slot.setId(8L);
		slot.setDoctor(d);
		app.setClient(c);
		app.setSlot(slot);
		//scheduleDao.addAppointment(app);
		c.setTreatingDoctor("fff");
		System.out.println("user Id" +userDao.getAllClients(d));
		System.out.println("user Id" +d);
		//System.out.println("user Id" +c);
		
		//System.out.println("user Id" +userDao.editClient(c));
		//System.out.println("user Id" +userDao.getConsultationDate(d, c));
		//System.out.println("uderdao"+userDao.getConsultationDate(user));
		//System.out.println("uderdao"+userDao.getAllDoctors(d));
		//System.out.println("uderdao"+scheduleDao.getAppointmentsClt(d,c));
		//System.out.println("uderdao"+agenda.getDoctors());
		//System.out.println("slot"+slot.getdStart());
		//System.out.println("slot"+doctor.getEmail());
		//slotDactorDay.setAppointment(appointment);
		//slotDactorDay.setSlot(slot);
		//System.out.println("uderdao"+slotDactorDay.getAppointment());
		
		//System.out.println("uderdao"+agenda.getDoctors().toString());
//		List<Doctor> medecins = agenda.getDoctors();
//		for (Doctor m : medecins) {
//		      System.out.println(m.getFirstName() + "ggggggggg");
//		    }
	}
	
//	
}
		
