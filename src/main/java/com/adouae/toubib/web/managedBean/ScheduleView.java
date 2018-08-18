package com.adouae.toubib.web.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adouae.toubib.domain.dto.AppointmentDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.itf.domain.service.ManageSchedule;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.security.LoginBean;

@ManagedBean(name = "scheduleView")
// @SessionScoped
@Component
@Scope("session")
public class ScheduleView implements Serializable {

	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;

	//private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private List<ClientDTO> clients;
	private String identityClient;
	public ClientDTO selectedClient;

	private Map<ScheduleEvent, AppointmentDTO> hIdentitesClients = new HashMap<ScheduleEvent, AppointmentDTO>();

	// selected Client id
	private Long selectedClientId;

	// is importance
	private String buttonStyle = "";

	// add pin
	private String addPin = "";

	// app
	private AppointmentDTO appointment;
	private AppointmentDTO selectedappointment;
	public List<AppointmentDTO> listApp;
	public List<AppointmentDTO> listAppClt;

	private UserDTO doctorDto;

	@Autowired
	private ClientBean clientBean;

	@Autowired
	private transient ManageUsers manageClient;

	@Autowired
	private transient ManageSchedule manageSchedule;

	@Autowired
	private LoginBean loginBean;

	public ScheduleView() {
		this.selectedappointment = new AppointmentDTO();
	}

	@PostConstruct
	public void init() {
		// clients = new ArrayList<ClientDTO>();
		// selectedClient = new ClientDTO();

		appointment = new AppointmentDTO();

		eventModel = new DefaultScheduleModel();

		clients = manageClient.getAllClients(loginBean.getCurrentUser());

		listApp = manageSchedule.getAppointments(loginBean.getCurrentUser());

		listAppClt = manageSchedule.getAppointmentsClts(loginBean.getCurrentUser(), clientBean.getSelectedClient());

		for (AppointmentDTO appDto : listApp) {

			DefaultScheduleEvent getEvent = new DefaultScheduleEvent(appDto.getTitle(), appDto.getSlot().getStartDate(),
					appDto.getSlot().getEndDate(), appDto.getSlot().getIsAllDay());
			if (appDto.getIsImportant())
				getEvent.setStyleClass(appDto.getCategory() + " " + "addPin");
			else
				getEvent.setStyleClass(appDto.getCategory());

			eventModel.addEvent(getEvent);

			hIdentitesClients.put(getEvent, appDto);

		}

//		lazyEventModel = new LazyScheduleModel() {
//
//			@Override
//			public void loadEvents(Date start, Date end) {
//				Date random = getRandomDate(start);
//				addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
//
//				random = getRandomDate(start);
//				addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
//			}
//		};

	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

//	public ScheduleModel getLazyEventModel() {
//		return lazyEventModel;
//	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void selectedOneInit() {
		appointment.setClient(selectedClient);
	}

	public void addEvent(ActionEvent actionEvent) {
				
		for (ClientDTO c : clients) {
			if (c.getId() == selectedClientId) {
				appointment.setClient(c);
			}
		}

		((DefaultScheduleEvent) event).setStyleClass(appointment.getCategory() + " " + addPin);

		Calendar calendar = Calendar.getInstance();
		Date d = calendar.getTime();

		// update appointment if exist
		if (hIdentitesClients.get(event) != null) {

			hIdentitesClients.get(event).setTitle(event.getTitle());
			hIdentitesClients.get(event).setReason(event.getDescription());
			hIdentitesClients.get(event).getSlot().setAllDay(event.isAllDay());
			hIdentitesClients.get(event).getSlot().setStartDate(event.getStartDate());
			hIdentitesClients.get(event).getSlot().setEndDate(event.getEndDate());
			hIdentitesClients.get(event).getSlot().setDoctor(loginBean.getCurrentUser());
			hIdentitesClients.get(event).getSlot().setIsAllDay(event.isAllDay());
			hIdentitesClients.get(event).setDay(d);
			hIdentitesClients.get(event).setCategory(appointment.getCategory());;

			if (buttonStyle.equals("buttonPined"))
				hIdentitesClients.get(event).setIsImportant(true);
			else
				hIdentitesClients.get(event).setIsImportant(false);

			// add appointement
			manageSchedule.addAppointment(hIdentitesClients.get(event));
		} else {
			// insert new appointment 
			appointment.setTitle(event.getTitle());
			appointment.setReason(event.getDescription());
			appointment.getSlot().setAllDay(event.isAllDay());
			appointment.getSlot().setStartDate(event.getStartDate());
			appointment.getSlot().setEndDate(event.getEndDate());
			appointment.getSlot().setDoctor(loginBean.getCurrentUser());
			appointment.setDay(d);
			appointment.getSlot().setIsAllDay(event.isAllDay());

			if (buttonStyle.equals("buttonPined"))
				appointment.setIsImportant(true);
			else
				appointment.setIsImportant(false);

			// add appointement
			manageSchedule.addAppointment(appointment);
		}

		if (event.getId() == null)
			eventModel.addEvent(event);

		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();

		// re load list appointment
		listApp = manageSchedule.getAppointments(loginBean.getCurrentUser());

		appointment = new AppointmentDTO();

	}

	public void onEventSelect(SelectEvent selectEvent) {

		for (AppointmentDTO appDto : listApp) {

			DefaultScheduleEvent getEvent = new DefaultScheduleEvent(appDto.getTitle(), appDto.getSlot().getStartDate(),
					appDto.getSlot().getEndDate(), appDto.getSlot().getIsAllDay());
			if (appDto.getIsImportant())
				getEvent.setStyleClass(appDto.getCategory() + " " + "addPin");
			else
				getEvent.setStyleClass(appDto.getCategory());

			hIdentitesClients.put(getEvent, appDto);

		}

		event = (ScheduleEvent) selectEvent.getObject();

		if (hIdentitesClients.get(event) != null) {

			appointment.setCategory(hIdentitesClients.get(event).getCategory());

			if (hIdentitesClients.get(event).getIsImportant()) {

				buttonStyle = "buttonPined";
				addPin = "addPin";
			} else {
				initParams();
			}

			selectedClientId = hIdentitesClients.get(event).getClient().getId();

			appointment.setPlace(hIdentitesClients.get(event).getPlace());
		}

	}

	public void onDateSelect(SelectEvent selectEvent) {
		initParams();
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {

		AppointmentDTO appToMove = new AppointmentDTO();

		for (Entry<ScheduleEvent, AppointmentDTO> entry : hIdentitesClients.entrySet()) {
			if (entry.getKey() == event.getScheduleEvent()) {

				appToMove = entry.getValue();
			}
		}

		manageSchedule.updateApp(appToMove);

	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
				"Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// delete app

	public void deleteApp() {

		manageSchedule.deleteApp(hIdentitesClients.get(event));
		init();

		initParams();

	}

	public void initParams() {
		buttonStyle = "";
		addPin = "";
	}

	// la méthode du texte autocomplete

	private String identity(ClientDTO c) {
		return c.getLastName() + " " + c.getFirstName();
	}

	public List<String> completeClients(String query) {
		List<String> identitys = new ArrayList<String>();
		// on recherche les clients qui correspondent
		for (ClientDTO c : clients) {
			String identity = identity(c);
			if (identity.toLowerCase().startsWith(query.toLowerCase())) {
				identitys.add(identity);
			}
		}
		return identitys;
	}

	// change color button pin

	public void buttonAction() {
		if (buttonStyle.isEmpty()) {
			buttonStyle = "buttonPined";
			addPin = "addPin";
		} else {
			buttonStyle = "";
			addPin = "";
		}
	}

	// move to cons
	public void onRowSelectResumeApp(SelectEvent event) throws IOException {

		clientBean.unloadConsultation();
		// move to consultation tab
		clientBean.setTabIndex(2);

		if (selectedappointment != null)
			clientBean.setReason(selectedappointment.getReason());

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/views/secure/client/clientDetails.xhtml?tab=2");
	}

	// getters & setters
	public List<ClientDTO> getClients() {
		return clients;
	}

	public void setClients(List<ClientDTO> clients) {
		this.clients = clients;
	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

	public String getIdentityClient() {
		return identityClient;
	}

	public void setIdentityClient(String identityClient) {
		this.identityClient = identityClient;
	}

	public ManageUsers getManageClient() {
		return manageClient;
	}

	public void setManageClient(ManageUsers manageClient) {
		this.manageClient = manageClient;
	}

	public ClientDTO getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}

	public String getButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public AppointmentDTO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentDTO appointment) {
		this.appointment = appointment;
	}

	public UserDTO getDoctorDto() {
		return doctorDto;
	}

	public void setDoctorDto(UserDTO doctorDto) {
		this.doctorDto = doctorDto;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Long getSelectedClientId() {
		return selectedClientId;
	}

	public void setSelectedClientId(Long selectedClientId) {
		this.selectedClientId = selectedClientId;
	}

	public String getAddPin() {
		return addPin;
	}

	public void setAddPin(String addPin) {
		this.addPin = addPin;
	}

	public List<AppointmentDTO> getListApp() {
		return listApp;
	}

	public void setListApp(List<AppointmentDTO> listApp) {
		this.listApp = listApp;
	}

	public ManageSchedule getManageSchedule() {
		return manageSchedule;
	}

	public void setManageSchedule(ManageSchedule manageSchedule) {
		this.manageSchedule = manageSchedule;
	}

	public Map<ScheduleEvent, AppointmentDTO> gethIdentitesClients() {
		return hIdentitesClients;
	}

	public void sethIdentitesClients(Map<ScheduleEvent, AppointmentDTO> hIdentitesClients) {
		this.hIdentitesClients = hIdentitesClients;
	}

	public List<AppointmentDTO> getListAppClt() {
		return listAppClt;
	}

	public void setListAppClt(List<AppointmentDTO> listAppClient) {
		this.listAppClt = listAppClient;
	}

	public AppointmentDTO getSelectedappointment() {
		return selectedappointment;
	}

	public void setSelectedappointment(AppointmentDTO selectedappointment) {
		this.selectedappointment = selectedappointment;
	}

}
