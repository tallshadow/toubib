package com.adouae.toubib;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.impl.persistence.dao.OrderDAO;
import com.adouae.toubib.impl.persistence.dao.UserDAO;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.itf.domain.service.ManageOrder;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.web.managedBean.OrderBean;

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
	
	@Inject
	private transient OrderDAO orderDao=null ;
	
	@Inject
	 transient ManageUsers manageUser = null;
	
//	@Inject
//	private transient Agenda agenda;
//	@Inject
//	public  LoginBean loginbean;
	
	@Inject
	public  OrderBean orderBean;
	
	@Autowired
	private transient ManageOrder manageOrder;
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
		
		// ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		//FacesContext context = FacesContext.getCurrentInstance();
		//Locale locale = new Locale("fr");
		//ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
		
		
	
		 //FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", bundle.getString("client.addMessageOk")));
		
		//System.out.println("uderdao"+dao.getUserDto("toto@toto.fr").getFirstName());
//		List<Diagnostics> rightList = userDao.getDiagnostics();
//		for(Diagnostics var : rightList){
//		System.out.println("uderdao      "+ var.getDiagnostics());
//		}
	
		Client c = new Client();
		c.setId(103L);
		
		Doctor d = new Doctor();
		d.setId(133L);
		
		System.out.println("user Id" +manageUser.getUserDtoByMail("a@a.aa"));
//		System.out.println("user Id" + userDao.getAllClients(d));
		
		//ExaminationsDTO examinationsDto = null;
		//examinationsDto.setExaminations("ddd");
		//System.out.println("uderdao"+dao.getAllDoctors());
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
		
