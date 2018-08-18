package com.adouae.toubib.web.managedBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.Theme;

import com.adouae.toubib.domain.dto.AppointmentDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.ConsultationDTO;
import com.adouae.toubib.domain.dto.DiagnosticsDTO;
import com.adouae.toubib.domain.dto.ExaminationsDTO;
import com.adouae.toubib.domain.dto.ReasonsDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.entity.City;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Diagnostics;
import com.adouae.toubib.impl.persistence.entity.Examinations;
import com.adouae.toubib.impl.persistence.entity.Reasons;
import com.adouae.toubib.itf.domain.service.ManageFolder;
import com.adouae.toubib.itf.domain.service.ManageOrder;
import com.adouae.toubib.itf.domain.service.ManageSchedule;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.security.LoginBean;

@ManagedBean(name = "clientBean")
// @SessionScoped
// @ViewScoped
@Component
@Scope("session") // to use doctorDto in init()
public class ClientBean {

	// private String city;
	// private Map<String,String> cities = new HashMap<String, String>();

	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
	private String country;
	private String city;
	private Map<String, String> countries;
	private List<City> cities;
	private List<ClientDTO> clients;
	private List<Client> filteredClients;
	private ClientDTO selectedClient;

	private String reason;
	private String examination;
	private String diagnostic;

	private List<String> rightSelected;
	private List<String> rightAvailable;
	private List<String> leftList;
	private String rightListName = "consultation.rightListNameReasons";

	private List<Reasons> reasonsList;
	private List<Examinations> examinationsList;
	private List<Diagnostics> diagnosticList;
	private List<ConsultationDTO> consultationList;
	private List<Date> consultationListDate;

	final String isReason = "reason";
	final String isExamination = "examination";
	final String isDiagnostic = "diagnostic";

	private String selectedConsultationDate;
	ConsultationDTO selectedConsultation;
	StringBuilder builder;
	StringBuilder builderReasons;
	StringBuilder builderExaminations;
	StringBuilder builderDiagnostics;

	private boolean skip;

	ClientDTO clientDto;

	ReasonsDTO reasonsDto;
	ExaminationsDTO examinationsDto;
	DiagnosticsDTO diagnosticsDto;
	ConsultationDTO consultationDto;

	List<AppointmentDTO> listAppClt;

	int tabIndex = 0;

	@Autowired
	private transient ManageUsers manageClient;

	@Autowired
	private transient ManageSchedule manageSchedule;

	@Autowired
	private transient ManageOrder manageOrder;

	@Autowired
	private transient ManageFolder manageFolder;

	@Autowired
	private OrderBean orderBean;

	@Autowired
	private FolderBean folderBean;

	@Autowired
	private LoginBean loginBean = null;

	UserDTO doctorDto;

	@Autowired
	UserData userData;

	public ClientBean() {
		this.clientDto = new ClientDTO();
		this.reasonsDto = new ReasonsDTO();
	}

	@PostConstruct
	public void init() {

		// clients = manageClient.getAllClients(doctorDto);
		clientDto = new ClientDTO();
		examinationsDto = new ExaminationsDTO();
		diagnosticsDto = new DiagnosticsDTO();
		consultationDto = new ConsultationDTO();

		countries = new HashMap<String, String>();
		countries.put("Maroc", "Maroc");
		countries.put("Algérie", "Algérie");
		countries.put("France", "France");

		reasonsList = manageClient.getReasons();

		leftList = new ArrayList<String>();
		rightAvailable = new ArrayList<String>();

		for (Reasons var : reasonsList) {
			rightAvailable.add(var.getReason());
		}

		builder = new StringBuilder();
		builderReasons = new StringBuilder();
		builderExaminations = new StringBuilder();
		builderDiagnostics = new StringBuilder();
	}

	// init doctor's list of clients
	public void initLists() {

		if (doctorDto == null) {
			doctorDto = loginBean.getCurrentUser();

		}

		if (!manageClient.getAllClients(doctorDto).isEmpty()) {
			clients = manageClient.getAllClients(doctorDto);
		}
	}

	public void onCountryChange() {
		if (clientDto.getAddress().getCountry() != null && !clientDto.getAddress().getCountry().equals(""))
			cities = manageClient.getCities(countries.get(clientDto.getAddress().getCountry()));
	}

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		if (clientDto.getAddress().getCountry() != null && !clientDto.getAddress().getCountry().equals(""))
			cities = manageClient.getCities(countries.get(clientDto.getAddress().getCountry()));

		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).getCity().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(cities.get(i).getCity());
			}
		}
		return results;
	}

	// Add Client
	public String addClient() {

		clientDto.getDoctors().add(doctorDto);
		manageClient.addClient(clientDto);

		clientDto = new ClientDTO();

		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("client.addClientOk"), ""));

		// clientDto = null;
		clients = manageClient.getAllClients(doctorDto);
		return "clients?faces-redirect=true";
	}

	// Edite Client
	public void editClient() {
		manageClient.editClient(selectedClient);

	}

	// update right table Consultation
	public void updateTableConsultation(String rightList) {

		if (rightList.equals("reason") || reason != null) {
			rightListName = "consultation.rightListNameReasons";
			reasonsList = manageClient.getReasons();
			rightAvailable = new ArrayList<String>();
			for (Reasons var : reasonsList) {
				if (!rightAvailable.contains(reason)) {
					rightAvailable.add(var.getReason());
				}
			}
			reason = null;
		} else if (rightList.equals("examination")) {
			rightListName = "consultation.rightListNameExaminations";
			examinationsList = manageClient.getExaminations();
			rightAvailable = new ArrayList<String>();
			for (Examinations var : examinationsList) {
				if (!rightAvailable.contains(reason) && var != null) {
					rightAvailable.add(var.getExaminations());
				}
			}
			examination = null;
		} else if (rightList.equals("diagnostic")) {
			rightListName = "consultation.rightListNameDiagnostics";
			diagnosticList = manageClient.getDiagnostics();
			rightAvailable = new ArrayList<String>();
			for (Diagnostics var : diagnosticList) {
				if (!rightAvailable.contains(diagnostic) && var != null) {
					rightAvailable.add(var.getDiagnostics());
				}
			}
			diagnostic = null;
		}
	}

	// update examinations consultation
	public void updateExaminations(String rightList) {
		if (rightList.equals("examination")) {
			rightListName = "consultation.rightListNameExaminations";
			examinationsList = manageClient.getExaminations();
			rightAvailable = new ArrayList<String>();
			for (Examinations var : examinationsList) {
				if (!rightAvailable.contains(reason) && var != null) {
					rightAvailable.add(var.getExaminations());
				}
			}
			if (examination != null) {

				if (!examination.isEmpty() && !rightAvailable.contains(examination)) {

					rightAvailable.add(examination);
					examinationsDto.setExaminations(examination);
					manageClient.updateExaminations(examinationsDto);
				}
				examination = null;
			}

		}
	}

	// update diagnostic consultation
	public void updateDiagnostic(String rightList) {

		if (rightList.equals("diagnostic")) {
			rightListName = "consultation.rightListNameDiagnostics";
			diagnosticList = manageClient.getDiagnostics();
			rightAvailable = new ArrayList<String>();
			for (Diagnostics var : diagnosticList) {
				if (!rightAvailable.contains(diagnostic) && var != null) {
					rightAvailable.add(var.getDiagnostics());
				}
			}
			if (diagnostic != null) {

				if (!diagnostic.isEmpty() && !rightAvailable.contains(diagnostic)) {

					rightAvailable.add(diagnostic);
					diagnosticsDto.setDiagnostics(diagnostic);
					manageClient.updateDiagnostics(diagnosticsDto);
				}
				diagnostic = null;
			}
		}
	}

	// update table reasons Consultation
	public void updateReasons(String rightList) {

		if (rightList.equals("reason") || reason != null) {
			rightListName = "consultation.rightListNameReasons";
			reasonsList = manageClient.getReasons();
			rightAvailable = new ArrayList<String>();
			for (Reasons var : reasonsList) {
				if (!rightAvailable.contains(reason)) {
					rightAvailable.add(var.getReason());
				}
			}
			if (!rightAvailable.contains(reason) && reason != null && !reason.isEmpty()) {
				rightAvailable.add(reason);
				reasonsDto.setReason(reason);
				manageClient.updateReasons(reasonsDto);
			}
			reason = null;

		}

	}

	// remove item from right list
	public void remove(String itemToDelete) {
		try {

			rightAvailable.remove(itemToDelete);
			if (rightListName.equals("consultation.rightListNameReasons")) {
				Reasons rs = null;
				for (Reasons var : reasonsList) {
					if (var.getReason().equals(itemToDelete)) {
						rs = var;
						manageClient.removeReason(rs);
					}
				}
			} else if (rightListName.equals("consultation.rightListNameExaminations")) {
				Examinations e = null;
				for (Examinations var : examinationsList) {
					if (var.getExaminations().equals(itemToDelete)) {
						e = var;
						manageClient.removeExamination(e);
					}
				}
			} else if (rightListName.equals("consultation.rightListNameDiagnostics")) {
				Diagnostics d = null;
				for (Diagnostics var : diagnosticList) {
					if (var.getDiagnostics().equals(itemToDelete)) {
						d = var;
						manageClient.removeDiagnostic(d);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// move item right to left Consultation
	public void rightToLeft() {
		if (rightListName.equals("consultation.rightListNameReasons")) {
			for (String item : rightSelected) {
				builderReasons.append((item)).append("\n");
			}
			reason = builderReasons.toString();
			diagnostic = builderDiagnostics.toString();
			examination = builderExaminations.toString();

		} else if (rightListName.equals("consultation.rightListNameExaminations")) {

			for (String item : rightSelected) {
				builderExaminations.append((item)).append("\n");
			}
			examination = builderExaminations.toString();
			reason = builderReasons.toString();

		} else if (rightListName.equals("consultation.rightListNameDiagnostics")) {
			for (String item : rightSelected) {
				builderDiagnostics.append((item)).append("\n");
			}
			diagnostic = builderDiagnostics.toString();
			examination = builderExaminations.toString();
			reason = builderReasons.toString();
		}
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((Theme) item).getName()).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowSelect(SelectEvent event) {
		try {
			// Load consultation list
			consultationList = manageClient.getConsultation(selectedClient, doctorDto);
			consultationListDate = manageClient.getConsultationDate(doctorDto, selectedClient);

			// Load Order List
			orderBean.orderList = manageOrder.getOrder(doctorDto, selectedClient);
			orderBean.orderListDate = manageOrder.getOrderDate(doctorDto, selectedClient);

			orderBean.setSelectedClient(selectedClient);
			// Payment
			orderBean.historicalPayment = manageOrder.getPayments(selectedClient, doctorDto);

			// folder
			folderBean.setSelectedClient(selectedClient);

			// schedule
			listAppClt = manageSchedule.getAppointmentsClts(doctorDto, selectedClient);

			ClientDTO c = new ClientDTO();
			BeanUtils.copyProperties(selectedClient, c);

			folderBean.folderDto = manageFolder.getFolder(c, doctorDto);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/views/secure/client/clientDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// filter clients by name
	@SuppressWarnings("unchecked")
	public boolean filterByName(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable<String>) value).compareTo(filterText) > 0;
	}

	// Load Consultation
	public void onConsultationChange() {

		consultationList = manageClient.getConsultation(selectedClient, doctorDto);
		consultationListDate = manageClient.getConsultationDate(doctorDto, selectedClient);

		builderReasons = new StringBuilder();
		builderDiagnostics = new StringBuilder();
		builderExaminations = new StringBuilder();

		for (ConsultationDTO c : consultationList) {
			if (c.getDay().toString().equals(selectedConsultationDate)) {

				selectedConsultation = c;
				reason = c.getReason();
				diagnostic = c.getDiagnostics();
				examination = c.getExamination();

			}
		}
	}

	// unload consultation
	public void unloadConsultation() {
		builderDiagnostics = new StringBuilder();
		builderExaminations = new StringBuilder();
		builderReasons = new StringBuilder();

		reason = null;
		diagnostic = null;
		examination = null;

	}

	// record Consultation
	public void recordConsultation() {
		// Client currentClient = new Client();
		if (!reason.isEmpty() || !examination.isEmpty() || !diagnostic.isEmpty()) {
			Date date = new Date();
			consultationDto = new ConsultationDTO(reason, examination, diagnostic, selectedClient, doctorDto, date);
			manageClient.updateConsultation(consultationDto);
		}

		// Load consultation list
		consultationList = manageClient.getConsultation(selectedClient, doctorDto);
		consultationListDate = manageClient.getConsultationDate(doctorDto, selectedClient);
	}

	// remove Consultation
	public void removeConsultation() {
		for (ConsultationDTO c : consultationList) {
			if (c.getDay().toString().equals(selectedConsultationDate)) {
				manageClient.removeConsultation(c.getId());
			}
			reason = null;
			diagnostic = null;
			examination = null;
		}

		// Load consultation list
		consultationList = manageClient.getConsultation(selectedClient, doctorDto);

		consultationListDate = manageClient.getConsultationDate(doctorDto, selectedClient);
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public void onRowSelectResume(SelectEvent event) throws IOException {

		// move to consultation tab
		tabIndex = 2;

		reason = selectedConsultation.getReason();
		examination = selectedConsultation.getExamination();
		diagnostic = selectedConsultation.getDiagnostics();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/views/secure/client/clientDetails.xhtml?tab=2");
	}

	// getters & setters

	public ClientDTO getClientDto() {
		return clientDto;
	}

	public void setClientDto(ClientDTO clientDto) {
		this.clientDto = clientDto;
	}

	public ManageUsers getManageClient() {
		return manageClient;
	}

	public void setManageClient(ManageUsers manageClient) {
		this.manageClient = manageClient;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	public void setCountries(Map<String, String> countries) {
		this.countries = countries;
	}

	public List<ClientDTO> getClients() {
		return clients;
	}

	public void setClients(List<ClientDTO> clients) {
		this.clients = clients;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public List<Client> getFilteredClients() {
		return filteredClients;
	}

	public void setFilteredClients(List<Client> filteredClients) {
		this.filteredClients = filteredClients;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ReasonsDTO getConsultationDto() {
		return reasonsDto;
	}

	public void setConsultationDto(ReasonsDTO consultationDto) {
		this.reasonsDto = consultationDto;
	}

	public List<String> getReasonListSelected() {
		return rightSelected;
	}

	public void setReasonListSelected(List<String> reasonListSelected) {
		this.rightSelected = reasonListSelected;
	}

	public List<String> getReasonList() {
		return rightAvailable;
	}

	public void setReasonList(List<String> reasonList) {
		this.rightAvailable = reasonList;
	}

	public List<String> getRightSelected() {
		return rightSelected;
	}

	public void setRightSelected(List<String> rightSelected) {
		this.rightSelected = rightSelected;
	}

	public List<String> getRightAvailable() {
		return rightAvailable;
	}

	public void setRightAvailable(List<String> rightAvailable) {
		this.rightAvailable = rightAvailable;
	}

	public String getIsReason() {
		return isReason;
	}

	public String getIsExamination() {
		return isExamination;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public ReasonsDTO getReasonsDto() {
		return reasonsDto;
	}

	public void setReasonsDto(ReasonsDTO reasonsDto) {
		this.reasonsDto = reasonsDto;
	}

	public ExaminationsDTO getExaminationsDto() {
		return examinationsDto;
	}

	public void setExaminationsDto(ExaminationsDTO examinationsDto) {
		this.examinationsDto = examinationsDto;
	}

	public List<String> getLeftList() {
		return leftList;
	}

	public void setLeftList(List<String> leftList) {
		this.leftList = leftList;
	}

	public List<Reasons> getReasonsList() {
		return reasonsList;
	}

	public void setReasonsList(List<Reasons> reasonsList) {
		this.reasonsList = reasonsList;
	}

	public List<Examinations> getExaminationsList() {
		return examinationsList;
	}

	public void setExaminationsList(List<Examinations> examinationsList) {
		this.examinationsList = examinationsList;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getRightListName() {
		return rightListName;
	}

	public void setRightListName(String rightListName) {
		this.rightListName = rightListName;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}

	public String getIsDiagnostic() {
		return isDiagnostic;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getCountries() {
		return countries;
	}

	public List<City> getCities() {
		return cities;
	}

	public List<Diagnostics> getDiagnosticList() {
		return diagnosticList;
	}

	public void setDiagnosticList(List<Diagnostics> diagnosticList) {
		this.diagnosticList = diagnosticList;
	}

	public StringBuilder getBuilderReasons() {
		return builderReasons;
	}

	public void setBuilderReasons(StringBuilder builderReasons) {
		this.builderReasons = builderReasons;
	}

	public StringBuilder getBuilderExaminations() {
		return builderExaminations;
	}

	public void setBuilderExaminations(StringBuilder builderExaminations) {
		this.builderExaminations = builderExaminations;
	}

	public StringBuilder getBuilderDiagnostics() {
		return builderDiagnostics;
	}

	public void setBuilderDiagnostics(StringBuilder builderDiagnostics) {
		this.builderDiagnostics = builderDiagnostics;
	}

	public DiagnosticsDTO getDiagnosticsDto() {
		return diagnosticsDto;
	}

	public void setDiagnosticsDto(DiagnosticsDTO diagnosticsDto) {
		this.diagnosticsDto = diagnosticsDto;
	}

	public List<ConsultationDTO> getConsultationList() {
		return consultationList;
	}

	public void setConsultationList(List<ConsultationDTO> consultationList) {
		this.consultationList = consultationList;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setConsultationDto(ConsultationDTO consultationDto) {
		this.consultationDto = consultationDto;
	}

	public List<Date> getConsultationListDate() {
		return consultationListDate;
	}

	public void setConsultationListDate(List<Date> consultationListDate) {
		this.consultationListDate = consultationListDate;
	}

	public UserDTO getDoctorDto() {
		return doctorDto;
	}

	public void setDoctorDto(UserDTO doctorDto) {
		this.doctorDto = doctorDto;
	}

	public String getSelectedConsultationDate() {
		return selectedConsultationDate;
	}

	public void setSelectedConsultationDate(String selectedConsultationDate) {
		this.selectedConsultationDate = selectedConsultationDate;
	}

	public ConsultationDTO getSelectedConsultation() {
		return selectedConsultation;
	}

	public void setSelectedConsultation(ConsultationDTO selectedConsultation) {
		this.selectedConsultation = selectedConsultation;
	}

	public ManageOrder getManageOrder() {
		return manageOrder;
	}

	public void setManageOrder(ManageOrder manageOrder) {
		this.manageOrder = manageOrder;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public FolderBean getFolderBean() {
		return folderBean;
	}

	public void setFolderBean(FolderBean folderBean) {
		this.folderBean = folderBean;
	}

	public ManageFolder getManageFolder() {
		return manageFolder;
	}

	public void setManageFolder(ManageFolder manageFolder) {
		this.manageFolder = manageFolder;
	}

	public ClientDTO getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public List<AppointmentDTO> getListAppClt() {
		return listAppClt;
	}

	public void setListAppClt(List<AppointmentDTO> listAppClt) {
		this.listAppClt = listAppClt;
	}

	public ManageSchedule getManageSchedule() {
		return manageSchedule;
	}

	public void setManageSchedule(ManageSchedule manageSchedule) {
		this.manageSchedule = manageSchedule;
	}

}
