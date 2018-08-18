package com.adouae.toubib.web.managedBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.Theme;

import com.adouae.toubib.domain.dto.BiologyDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.DrugDTO;
import com.adouae.toubib.domain.dto.DrugPatientDTO;
import com.adouae.toubib.domain.dto.OrderDTO;
import com.adouae.toubib.domain.dto.PaymentDTO;
import com.adouae.toubib.domain.dto.RadiologyDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.entity.City;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.itf.domain.service.ManageOrder;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.security.LoginBean;

@ManagedBean(name = "orderBean")
//@SessionScoped
@Scope("session")
@Component
public class OrderBean {

	private DrugPatientDTO drug = new DrugPatientDTO();
	private String biology;
	private String radiology;

	private String isDrug = "drug";
	private String isBiology = "biology";
	private String isRadiology = "radiology";

	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
	private String country;
	private String city;
	private Map<String, String> countries;
	private List<City> cities;
	private List<ClientDTO> clients;
	private List<Client> filteredClients;
	private ClientDTO selectedClient;

	private List<String> rightSelected;
	private List<String> rightAvailable;
	private List<String> leftList;
	private String rightListName = "order.rightListNameDrug";

	private List<DrugDTO> drugList;
	private List<BiologyDTO> bilogyList;
	private List<RadiologyDTO> radiologyList;

	private DrugPatientDTO selectedDrug = new DrugPatientDTO();
	private List<DrugPatientDTO> selectedDrugs;
	private List<String> selectedBiology;
	private List<String> selectedRadiology;

	List<OrderDTO> orderList;
	List<Date> orderListDate;

	private String selectedOrder;

	StringBuilder builder;

	StringBuilder builderDrug;
	StringBuilder builderBiology;
	StringBuilder builderRadiology;

	ClientDTO clientDto;

	DrugDTO drugDto;
	BiologyDTO biologyDto;
	RadiologyDTO radiologyDto;

	// Editor text
	private String editorText;
	private StringBuilder editorBuilder;

	UserDTO doctorDto;

	// drug
	private String drugName;
	private int amountDrug;
	private Date startDateDrug;
	private int durationDrug;
	private int morningDrug;
	private int noonDrug;
	private int eveningDrug;
	private String dateDrug;
	private String noteDrug;

	// Payment

	private PaymentDTO payment;
	List<PaymentDTO> historicalPayment;
	private PaymentDTO selectedFee = new PaymentDTO();

	DrugPatientDTO drugPatient;

	@Autowired
	private transient ManageUsers manageClient;

	@Autowired
	private transient ManageOrder manageOrder;

//	@Autowired
//	private ClientBean clientBean;

	@Autowired
	private LoginBean loginBean;

	@Autowired
	UserData userData;

	public OrderBean() {
		this.clientDto = new ClientDTO();
		this.drug = new DrugPatientDTO();
		this.selectedDrug = new DrugPatientDTO();
		this.selectedDrugs = new ArrayList<DrugPatientDTO>();
		this.payment = new PaymentDTO();
	}

	public OrderBean(DrugPatientDTO drug) {
		this.drug = drug;
	}

	@PostConstruct
	public void init() {

		// selectedClient = clientBean.getSelectedClient();
		drug = new DrugPatientDTO();

		clientDto = new ClientDTO();

		drugDto = new DrugDTO();
		biologyDto = new BiologyDTO();
		radiologyDto = new RadiologyDTO();
		orderList = new ArrayList<OrderDTO>();
		orderListDate = new ArrayList<Date>();

		countries = new HashMap<String, String>();
		countries.put("Maroc", "Maroc");
		countries.put("Algérie", "Algérie");
		countries.put("France", "France");

		// clients = manageClient.getAllClients(loginBean.getCurrentUser());

		drugList = manageOrder.getDrugs();

		selectedDrugs = new ArrayList<DrugPatientDTO>();
		selectedDrug = new DrugPatientDTO();
		selectedBiology = new ArrayList<String>();
		selectedRadiology = new ArrayList<String>();

		leftList = new ArrayList<String>();
		rightAvailable = new ArrayList<String>();

		for (DrugDTO var : drugList) {
			rightAvailable.add(var.getDrug());
		}

		builder = new StringBuilder();
		builderDrug = new StringBuilder();
		builderBiology = new StringBuilder();
		builderRadiology = new StringBuilder();

		// Editor text
		editorBuilder = new StringBuilder();

	}

	public void onCountryChange() {
		for (DrugDTO var : drugList) {

			rightAvailable.add(var.getDrug());
		}

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
		manageClient.addClient(clientDto);

		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				bundle.getString("client.addClientOk"), "PrimeFaces Rocks."));

		// clientDto = null;
		return "client";
	}

	// Edite Client
	public void editClient() {
		manageClient.editClient(clientDto);
	}

	// update right table
	public void updateRightTable(String rightList) {
		if (rightList.equals("drug")) {

			rightListName = "order.rightListNameDrug";
			drugList = manageOrder.getDrugs();
			rightAvailable = new ArrayList<String>();
			for (DrugDTO var : drugList) {
				if (!rightAvailable.contains(drug)) {
					rightAvailable.add(var.getDrug());
				}
			}
			drug = null;

		} else if (rightList.equals("biology")) {

			rightListName = "order.rightListNameBiology";
			bilogyList = manageOrder.getBiologies();
			rightAvailable = new ArrayList<String>();
			for (BiologyDTO var : bilogyList) {
				if (!rightAvailable.contains(biology) && var != null) {
					rightAvailable.add(var.getBiology());
				}
			}
			biology = null;
		} else if (rightList.equals("radiology")) {
			rightListName = "order.rightListNameRadiology";
			radiologyList = manageOrder.getRadiology();
			rightAvailable = new ArrayList<String>();
			for (RadiologyDTO var : radiologyList) {
				if (!rightAvailable.contains(radiology) && var != null) {
					rightAvailable.add(var.getRadiology());
				}
			}
			radiology = null;
		}

	}

	// move item left to right
	public void leftToRight(String rightList) {

		if (rightList.equals("drug")) {

			rightListName = "order.rightListNameDrug";
			drugList = manageOrder.getDrugs();
			rightAvailable = new ArrayList<String>();
			for (DrugDTO var : drugList) {
				if (!rightAvailable.contains(drug)) {
					rightAvailable.add(var.getDrug());
				}
			}
			if (!rightAvailable.contains(drugName) && drug != null && !drugName.isEmpty()) {
				rightAvailable.add(drugName);
				drugDto.setDrug(drugName);
				manageOrder.updateDrug(drugDto);
			}

			drug = null;

		} else if (rightList.equals("biology")) {

			rightListName = "order.rightListNameBiology";
			bilogyList = manageOrder.getBiologies();
			rightAvailable = new ArrayList<String>();
			for (BiologyDTO var : bilogyList) {
				if (!rightAvailable.contains(biology) && var != null) {
					rightAvailable.add(var.getBiology());
				}
			}
			if (biology != null) {

				if (!biology.isEmpty() && !rightAvailable.contains(biology)) {

					rightAvailable.add(biology);
					biologyDto.setBiology(biology);
					manageOrder.updateBiologies(biologyDto);
				}
				biology = null;
			}

		} else if (rightList.equals("radiology")) {
			rightListName = "order.rightListNameRadiology";
			radiologyList = manageOrder.getRadiology();
			rightAvailable = new ArrayList<String>();
			for (RadiologyDTO var : radiologyList) {
				if (!rightAvailable.contains(radiology) && var != null) {
					rightAvailable.add(var.getRadiology());
				}
			}
			if (radiology != null) {

				if (!radiology.isEmpty() && !rightAvailable.contains(radiology)) {

					rightAvailable.add(radiology);
					radiologyDto.setRadiology(radiology);
					manageOrder.updateRadiology(radiologyDto);
				}
				radiology = null;
			}
		}

	}

	// add to selected Drug
	public void addToSelectedDrug(ActionEvent actionEvent) {

		drug = new DrugPatientDTO();
		drug.setAmount(0);
		drug.setDuration(0);
		drug.setEvening(0);
		drug.setMorning(0);
		drug.setNoon(0);
		drug.setNote("");

		drug.setDrug(drugName);
		if (drug.getDrug() != null && !drug.getDrug().isEmpty()) {
			selectedDrugs.add(drug);

		}
		if (!rightAvailable.contains(drug.getDrug())) {
			rightAvailable.add(drug.getDrug());
		}
		drug = new DrugPatientDTO();

		leftToRight("drug");
	}

	// remove item from right list
	public void remove(String itemToDelete) {
		try {
			rightAvailable.remove(itemToDelete);
			if (rightListName.equals("order.rightListNameDrug")) {
				DrugDTO d = null;
				for (DrugDTO var : drugList) {
					if (var.getDrug().equals(itemToDelete)) {
						d = var;
						manageOrder.removeDrug(d);
					}
				}
			} else if (rightListName.equals("order.rightListNameBiology")) {
				BiologyDTO b = null;
				for (BiologyDTO var : bilogyList) {
					if (var.getBiology().equals(itemToDelete)) {
						b = var;
						manageOrder.removeBiology(b);
					}
				}
			} else if (rightListName.equals("order.rightListNameRadiology")) {

				RadiologyDTO d = null;
				for (RadiologyDTO var : radiologyList) {
					if (var.getRadiology().equals(itemToDelete)) {
						d = var;
						manageOrder.removeRadiology(d);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeSelectedDrug(DrugPatientDTO itemToDelete) {

		selectedDrugs.remove(itemToDelete);
	}

	public void removeSelectedFee(PaymentDTO itemToDelete) {
		historicalPayment.remove(itemToDelete);
		manageOrder.removeFee(itemToDelete);
	}

	// move item right to left
	public void rightToLeft() {

		if (rightListName.equals("order.rightListNameDrug")) {
			for (String item : rightSelected) {
				builderDrug.append((item)).append("\n");
				DrugPatientDTO drugDto = new DrugPatientDTO();
				drugDto.setDrug(item);
				drugDto.setAmount(0);
				drugDto.setDuration(0);
				drugDto.setEvening(0);
				drugDto.setMorning(0);
				drugDto.setNoon(0);
				drugDto.setNote("");
				selectedDrugs.add(drugDto);
			}

			biology = builderBiology.toString();
			radiology = builderRadiology.toString();

		} else if (rightListName.equals("order.rightListNameBiology")) {

			for (String item : rightSelected) {
				builderBiology.append((item)).append("\n");
				selectedBiology.add(item);
			}

			biology = builderBiology.toString();
			radiology = builderRadiology.toString();

		} else if (rightListName.equals("order.rightListNameRadiology")) {
			for (String item : rightSelected) {
				builderRadiology.append((item)).append("\n");
				selectedRadiology.add(item);
			}

			biology = builderBiology.toString();
			radiology = builderRadiology.toString();
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
	public void loadOrder() {

		System.out.println("orderListDate" + orderListDate);

		if (orderList.isEmpty() || orderListDate.isEmpty()) {
			orderList = manageOrder.getOrder(loginBean.getCurrentUser(), selectedClient);
			orderListDate = manageOrder.getOrderDate(loginBean.getCurrentUser(), selectedClient);
		}

		selectedDrugs = new ArrayList<DrugPatientDTO>();
		builderBiology = new StringBuilder();
		builderRadiology = new StringBuilder();

		for (OrderDTO c : orderList) {

			if (c.getDay().toString().equals(selectedOrder)) {

				for (DrugPatientDTO drug : c.getDrug()) {

					drug.setAmount(0);
					drug.setDuration(0);
					drug.setEvening(0);
					drug.setMorning(0);
					drug.setNoon(0);
					drug.setNote("");
				}
				// drug = c.getDrug();
				selectedDrugs.addAll(c.getDrug());
				biology = c.getBiology();
				radiology = c.getRadiology();
			}
		}
	}

	public void initDrug() {
		drug = new DrugPatientDTO();
		selectedDrug = new DrugPatientDTO();
		// selectedDrugs = new ArrayList<DrugPatientDTO>();
	}

	// unload consultation
	public void unloadOrder() {
		selectedDrugs = new ArrayList<DrugPatientDTO>();
		biology = null;
		radiology = null;
		builderBiology = new StringBuilder();
		builderRadiology = new StringBuilder();
	}

	// record Order
	public void recordOrder() {

		if (!drugName.isEmpty() || !biology.isEmpty() || !radiology.isEmpty()) {
			Date date = new Date();
			ClientDTO c = new ClientDTO();
			BeanUtils.copyProperties(selectedClient, c);
			OrderDTO orderDto = new OrderDTO(selectedDrugs, biology, radiology, date, c, loginBean.getCurrentUser());

			manageOrder.updateOrder(orderDto);
		}

		orderList = manageOrder.getOrder(loginBean.getCurrentUser(), selectedClient);
		orderListDate = manageOrder.getOrderDate(loginBean.getCurrentUser(), selectedClient);
	}

	// record Drug
	public void recordDrug() {
		selectedDrug = new DrugPatientDTO(selectedDrug.getDrug(), amountDrug, morningDrug, noonDrug, eveningDrug,
				startDateDrug, durationDrug, dateDrug);
		System.out.println("dddd" + selectedDrug.getDrug());
	}

	public void onRowSelect(SelectEvent event) {
		selectedDrug = new DrugPatientDTO();
	}

	// remove Order
	public void removeOrder() {
		for (OrderDTO o : orderList) {
			if (o.getDay().toString().equals(selectedOrder)) {
				manageOrder.removeOrder(o.getId());
			}
			selectedDrugs = new ArrayList<DrugPatientDTO>();
			biology = null;
			radiology = null;
		}

	}

	// Editor text
	public void editor() {

		editorText = null;
		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		
		//get Doctor
		doctorDto = loginBean.getCurrentUser();
		String clientGender ="";
		if(selectedClient.getGender().equals("M")){
			clientGender = bundle.getString("user.titleM");
		}else if (selectedClient.getGender().equals("F")) {
			clientGender = bundle.getString("user.titleF");
		}
		
		editorBuilder.append("<center>" + dateFormat.format(today)).append("<br/><br/><br/>");
		editorBuilder.append("Dr " + doctorDto.getFirstName() + " \t " + doctorDto.getLastName()).append("<br/>");

		editorBuilder.append(((doctorDto.getAddress().getStreet() == null) ? "" : doctorDto.getAddress().getStreet())  + ", \t" + ((doctorDto.getAddress().getZip()== null) ? "" : doctorDto.getAddress().getZip()) + " \t"
				+ ((doctorDto.getAddress().getCity() == null) ? "" : doctorDto.getAddress().getCity()) ).append("<br/>");
		editorBuilder.append("</center>").append("<br/> <hr/>");

		editorBuilder
				.append("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" + clientGender
						+ "\t" + selectedClient.getFirstName() + "\t" + selectedClient.getLastName());
		editorBuilder.append("<br/><br/>");
		editorBuilder.append("<center>");

		for (DrugPatientDTO drug : selectedDrugs) {
			editorBuilder.append(drug.getAmount() + "\t" + drug.getDrug()).append("<br/>");
			editorBuilder.append(drug.getMorning() + "\t" + bundle.getString("drug.morning") + ",\t" + drug.getNoon()
					+ "\t" + bundle.getString("drug.noon") + ",\t" + drug.getEvening() + "\t"
					+ bundle.getString("drug.evening")).append("<br/><br/>");
			editorBuilder.append(drug.getNote()).append("<br/><br/>");

		}

		editorBuilder.append(biology).append("<br/><br/>");
		editorBuilder.append(radiology).append("<br/><br/>");

		editorText = editorBuilder.toString();
		editorBuilder = new StringBuilder();

	}

	// certificate
	public void certificate() {

		editorText = null;
		
		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		doctorDto = loginBean.getCurrentUser();
		
		String clientGender ="";
		
		if(selectedClient.getGender().equals("M")){
			clientGender = bundle.getString("user.titleM");
		}else if (selectedClient.getGender().equals("F")) {
			clientGender = bundle.getString("user.titleF");
		}
		
		editorBuilder.append("<center>" + dateFormat.format(today)).append("<br/><br/><br/>");
		editorBuilder.append("Dr " + doctorDto.getFirstName() + " \t " + doctorDto.getLastName()).append("<br/>");	
		editorBuilder.append(((doctorDto.getAddress().getStreet() == null) ? "" : doctorDto.getAddress().getStreet())  + ", \t" + ((doctorDto.getAddress().getZip()== null) ? "" : doctorDto.getAddress().getZip()) + " \t"
				+ ((doctorDto.getAddress().getCity() == null) ? "" : doctorDto.getAddress().getCity()) ).append("<br/>");
		editorBuilder.append("</center>").append("<br/> <hr/>");

		editorBuilder
				.append("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" + clientGender
						+ "\t" + selectedClient.getFirstName() + "\t" + selectedClient.getLastName());
		editorBuilder.append("<br/><br/>");
		editorBuilder.append("<center>");

		editorText = editorBuilder.toString();
		editorBuilder = new StringBuilder();
	}

	public void recordPayment() {
		Date date = new Date();
		payment.setDate(date);

		ClientDTO c = new ClientDTO();
		BeanUtils.copyProperties(selectedClient, c);
		payment.setClient(c);
		payment.setDoctor(loginBean.getCurrentUser());
		manageOrder.updatePayment(payment);

		historicalPayment = manageOrder.getPayments(selectedClient, loginBean.getCurrentUser());
	}

	// getters & setters

	public ClientDTO getClientDto() {
		return clientDto;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getEditorText() {
		return editorText;
	}

	public void setEditorText(String editorText) {
		this.editorText = editorText;
	}

	public StringBuilder getEditorBuilder() {
		return editorBuilder;
	}

	public void setEditorBuilder(StringBuilder editorBuilder) {
		this.editorBuilder = editorBuilder;
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

	public PaymentDTO getSelectedFee() {
		return selectedFee;
	}

	public void setSelectedFee(PaymentDTO selectedFee) {
		this.selectedFee = selectedFee;
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

	public ClientDTO getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}

	public List<Client> getFilteredClients() {
		return filteredClients;
	}

	public void setFilteredClients(List<Client> filteredClients) {
		this.filteredClients = filteredClients;
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

	public List<PaymentDTO> getHistoricalPayment() {
		return historicalPayment;
	}

	public void setHistoricalPayment(List<PaymentDTO> historicalPayment) {
		this.historicalPayment = historicalPayment;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public List<String> getLeftList() {
		return leftList;
	}

	public void setLeftList(List<String> leftList) {
		this.leftList = leftList;
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

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
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

	public UserDTO getDoctorDto() {
		return doctorDto;
	}

	public void setDoctorDto(UserDTO doctorDto) {
		this.doctorDto = doctorDto;
	}

	public DrugPatientDTO getDrug() {
		return drug;
	}

	public void setDrug(DrugPatientDTO drug) {
		this.drug = drug;
	}

	public String getBiology() {
		return biology;
	}

	public void setBiology(String biology) {
		this.biology = biology;
	}

	public String getRadiology() {
		return radiology;
	}

	public void setRadiology(String radiology) {
		this.radiology = radiology;
	}

	public String getIsDrug() {
		return isDrug;
	}

	public void setIsDrug(String isDrug) {
		this.isDrug = isDrug;
	}

	public String getIsBiology() {
		return isBiology;
	}

	public void setIsBiology(String isBiology) {
		this.isBiology = isBiology;
	}

	public String getIsRadiology() {
		return isRadiology;
	}

	public void setIsRadiology(String isRadiology) {
		this.isRadiology = isRadiology;
	}

	public List<DrugDTO> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugDTO> drugList) {
		this.drugList = drugList;
	}

	public ManageOrder getManageOrder() {
		return manageOrder;
	}

	public void setManageOrder(ManageOrder manageOrder) {
		this.manageOrder = manageOrder;
	}

	public DrugDTO getDrugDto() {
		return drugDto;
	}

	public void setDrugDto(DrugDTO drugDto) {
		this.drugDto = drugDto;
	}

	public List<BiologyDTO> getBilogyList() {
		return bilogyList;
	}

	public void setBilogyList(List<BiologyDTO> bilogyList) {
		this.bilogyList = bilogyList;
	}

	public BiologyDTO getBiologyDto() {
		return biologyDto;
	}

	public void setBiologyDto(BiologyDTO biologyDto) {
		this.biologyDto = biologyDto;
	}

	public List<RadiologyDTO> getRadiologyList() {
		return radiologyList;
	}

	public void setRadiologyList(List<RadiologyDTO> radiologyList) {
		this.radiologyList = radiologyList;
	}

	public List<OrderDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDTO> orderList) {
		this.orderList = orderList;
	}

	public StringBuilder getBuilderDrug() {
		return builderDrug;
	}

	public void setBuilderDrug(StringBuilder builderDrug) {
		this.builderDrug = builderDrug;
	}

	public StringBuilder getBuilderBiology() {
		return builderBiology;
	}

	public void setBuilderBiology(StringBuilder builderBiology) {
		this.builderBiology = builderBiology;
	}

	public StringBuilder getBuilderRadiology() {
		return builderRadiology;
	}

	public void setBuilderRadiology(StringBuilder builderRadiology) {
		this.builderRadiology = builderRadiology;
	}

	public RadiologyDTO getRadiologyDto() {
		return radiologyDto;
	}

	public void setRadiologyDto(RadiologyDTO radiologyDto) {
		this.radiologyDto = radiologyDto;
	}

	public List<Date> getOrderListDate() {
		return orderListDate;
	}

	public void setOrderListDate(List<Date> orderListDate) {
		this.orderListDate = orderListDate;
	}

//	public ClientBean getClientBean() {
//		return clientBean;
//	}
//
//	public void setClientBean(ClientBean clientBean) {
//		this.clientBean = clientBean;
//	}

	public String getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(String selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public List<String> getSelectedBiology() {
		return selectedBiology;
	}

	public void setSelectedBiology(List<String> selectedBiology) {
		this.selectedBiology = selectedBiology;
	}

	public List<String> getSelectedRadiology() {
		return selectedRadiology;
	}

	public void setSelectedRadiology(List<String> selectedRadiology) {
		this.selectedRadiology = selectedRadiology;
	}

	public DrugPatientDTO getSelectedDrug() {
		return selectedDrug;
	}

	public void setSelectedDrug(DrugPatientDTO selectedDrug) {
		this.selectedDrug = selectedDrug;
	}

	public List<DrugPatientDTO> getSelectedDrugs() {
		return selectedDrugs;
	}

	public void setSelectedDrugs(List<DrugPatientDTO> selectedDrugs) {
		this.selectedDrugs = selectedDrugs;
	}

	public int getAmountDrug() {
		return amountDrug;
	}

	public void setAmountDrug(int amountDrug) {
		this.amountDrug = amountDrug;
	}

	public Date getStartDateDrug() {
		return startDateDrug;
	}

	public void setStartDateDrug(Date startDateDrug) {
		this.startDateDrug = startDateDrug;
	}

	public int getDurationDrug() {
		return durationDrug;
	}

	public void setDurationDrug(int durationDrug) {
		this.durationDrug = durationDrug;
	}

	public int getMorningDrug() {
		return morningDrug;
	}

	public void setMorningDrug(int morningDrug) {
		this.morningDrug = morningDrug;
	}

	public int getNoonDrug() {
		return noonDrug;
	}

	public void setNoonDrug(int noonDrug) {
		this.noonDrug = noonDrug;
	}

	public int getEveningDrug() {
		return eveningDrug;
	}

	public void setEveningDrug(int eveningDrug) {
		this.eveningDrug = eveningDrug;
	}

	public String getDateDrug() {
		return dateDrug;
	}

	public void setDateDrug(String dateDrug) {
		this.dateDrug = dateDrug;
	}

	public String getNoteDrug() {
		return noteDrug;
	}

	public void setNoteDrug(String noteDrug) {
		this.noteDrug = noteDrug;
	}

	public DrugPatientDTO getDrugPatient() {
		return drugPatient;
	}

	public void setDrugPatient(DrugPatientDTO drugPatient) {
		this.drugPatient = drugPatient;
	}

}
