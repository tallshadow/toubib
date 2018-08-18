package com.adouae.toubib;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)


public class Test {
	@Autowired
	//private ManageUsers dao ;
	
	//@Inject
	//private transient UserDTO user=null ;
//	@Inject
//	private transient UserDAO userDao=null ;
//	
//	@Inject
//	private  ManageOrder orderDao ;
//	
//	@Inject
//	private transient ManageUsers manageUser =null;
	
	
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
		Client user = new Client();
		user.setId(103L);
		//System.out.println("user Id" + orderDao.getOrder(user));
		//System.out.println("uderdao"+userDao.getConsultation(user));
		//System.out.println("uderdao"+dao.getAllDoctors());
		Doctor d = new Doctor();
		d.setId(133L);
		//System.out.println("uderdao"+manageUser.getUserDtoByMail("a@a.aa"));
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
		
