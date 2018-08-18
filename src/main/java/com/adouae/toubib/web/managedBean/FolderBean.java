package com.adouae.toubib.web.managedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.FolderDTO;
import com.adouae.toubib.domain.dto.SurgicalHistoryDTO;
import com.adouae.toubib.itf.domain.service.ManageFolder;
import com.adouae.toubib.security.LoginBean;

@ManagedBean(name = "folderBean")
//@SessionScoped
@Scope("session")
@Component
public class FolderBean {

	public FolderBean() {

		this.selectedSurgical = new SurgicalHistoryDTO();
	}

	private String allergy;

	private List<String> allergyList;
	private String selectedAllergy;
	private String selectedAllergyInit;
	private List<String> addiction;
	private SurgicalHistoryDTO surgicalHistoryDto;
	private SurgicalHistoryDTO selectedSurgical;

	private String bloodGroup;

	StringBuilder builderAllergy;

	FolderDTO folderDto;

	// medication History
	private String drug;
	private String selectedDrug;
	private String selectedDrugInit;

	// vaccines
	List<String> vaccinesList;
	private String vaccine;
	private String selectedVaccine;
	private String selectedVaccineInit;

	
	private ClientDTO selectedClient;
	
	@Autowired
	private transient ManageFolder manageFolder;
	
	@Autowired
	private LoginBean loginBean ;
	
	@Autowired
	UserData userData;

	@PostConstruct
	public void init() {
		allergyList = manageFolder.getallergys();
		builderAllergy = new StringBuilder();
		addiction = new ArrayList<String>();
		//folderDto = manageFolder.getFolder(clientDto,docotrDto);
		surgicalHistoryDto = new SurgicalHistoryDTO();
		selectedSurgical = new SurgicalHistoryDTO();

		// vaccines

		vaccinesList = manageFolder.getVaccines();
	}

	public void selectedOneInit() {
		selectedAllergyInit = selectedAllergy;
		selectedDrugInit = selectedDrug;
		selectedVaccineInit = selectedVaccine;
	}

	public void selectAllergy() {

		if (selectedAllergyInit != null && !selectedAllergyInit.isEmpty()) {
			if (folderDto.getAllergy() != null && !folderDto.getAllergy().isEmpty()) {
				builderAllergy.append(", ").append(selectedAllergyInit);
			} else {
				builderAllergy.append(selectedAllergyInit);
			}
		}

		folderDto.setAllergy(builderAllergy.toString());

	}

	public void addSurgicalHistory() {
		if (!surgicalHistoryDto.getAct().isEmpty()) {
			folderDto.getSurgicalHistory().add(surgicalHistoryDto);
		}

		surgicalHistoryDto = new SurgicalHistoryDTO();
		selectedSurgical = new SurgicalHistoryDTO();

	}

	public void removeSelectedSurgical(SurgicalHistoryDTO itemToDelete) {
		folderDto.getSurgicalHistory().remove(itemToDelete);
	}

	// select drug
	public void selectDrug() {

		drug = selectedDrugInit;
	}

	// add drug
	public void addDrug() {

		if (drug != null && !drug.isEmpty()) {
			folderDto.getMedicationHistory().add(drug);
		}
		drug = null;
	}

	public void removeSelectedDrug(String itemToDelete) {

		folderDto.getMedicationHistory().remove(itemToDelete);
	}

	// Vaccines

	public void selectVaccine() {
		vaccine = selectedVaccineInit;
	}

	public void addVaccine() {

		if (vaccine != null && !vaccine.isEmpty()) {
			folderDto.getVaccins().add(vaccine);
		}
		vaccine = null;
	}

	public void removeSelectedVaccine(String itemToDelete) {

		folderDto.getVaccins().remove(itemToDelete);
	}
	
	//Folder
	
	// record Order
		public void recordFolder() {
					
			Locale locale = new Locale(userData.getLocaleCode());
			ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
			
			if (folderDto !=null) {
				
				ClientDTO c =new ClientDTO();
				BeanUtils.copyProperties(selectedClient, c);
				folderDto.setClient(c);
				folderDto.setDoctor(loginBean.getCurrentUser());
				manageFolder.updateFolder(folderDto);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						bundle.getString("updateOk"),""));
			}
			
			// Load consultation list
//			consultationList = manageClient.getConsultation(selectedClient);
//			consultationListDate = manageClient.getConsultationDate(selectedClient);
		}
		
	// getter & setters
	public List<String> getAllergyList() {
		return allergyList;
	}

	public void setAllergyList(List<String> allergyList) {
		this.allergyList = allergyList;
	}

	public ManageFolder getManageFolder() {
		return manageFolder;
	}

	public void setManageFolder(ManageFolder manageFolder) {
		this.manageFolder = manageFolder;
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

	public String getSelectedAllergy() {
		return selectedAllergy;
	}

	public void setSelectedAllergy(String selectedAllergy) {
		this.selectedAllergy = selectedAllergy;
	}

	public List<String> getAddiction() {
		return addiction;
	}

	public void setAddiction(List<String> addiction) {
		this.addiction = addiction;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public StringBuilder getBuilderAllergy() {
		return builderAllergy;
	}

	public void setBuilderAllergy(StringBuilder builderAllergy) {
		this.builderAllergy = builderAllergy;
	}

	public FolderDTO getFolderDto() {
		return folderDto;
	}

	public void setFolderDto(FolderDTO folderDto) {
		this.folderDto = folderDto;
	}

	public String getSelectedAllergyInit() {
		return selectedAllergyInit;
	}

	public void setSelectedAllergyInit(String selectedAllergyInit) {
		this.selectedAllergyInit = selectedAllergyInit;
	}

	public SurgicalHistoryDTO getSurgicalHistoryDto() {
		return surgicalHistoryDto;
	}

	public void setSurgicalHistoryDto(SurgicalHistoryDTO surgicalHistoryDto) {
		this.surgicalHistoryDto = surgicalHistoryDto;
	}

	public SurgicalHistoryDTO getSelectedSurgical() {
		return selectedSurgical;
	}

	public void setSelectedSurgical(SurgicalHistoryDTO selectedSurgical) {
		this.selectedSurgical = selectedSurgical;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getSelectedDrug() {
		return selectedDrug;
	}

	public void setSelectedDrug(String selectedDrug) {
		this.selectedDrug = selectedDrug;
	}

	public String getSelectedDrugInit() {
		return selectedDrugInit;
	}

	public void setSelectedDrugInit(String selectedDrugInit) {
		this.selectedDrugInit = selectedDrugInit;
	}

	public List<String> getVaccinesList() {
		return vaccinesList;
	}

	public void setVaccinesList(List<String> vaccinesList) {
		this.vaccinesList = vaccinesList;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public String getSelectedVaccine() {
		return selectedVaccine;
	}

	public void setSelectedVaccine(String selectedVaccine) {
		this.selectedVaccine = selectedVaccine;
	}

	public String getSelectedVaccineInit() {
		return selectedVaccineInit;
	}

	public void setSelectedVaccineInit(String selectedVaccineInit) {
		this.selectedVaccineInit = selectedVaccineInit;
	}


	public ClientDTO getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}


}
