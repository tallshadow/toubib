package com.adouae.toubib.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class FolderDTO {
	
	private Long id;
	private List<String> addiction;
	private String bloodGroup;
	private String size;
	private String weight;
	private String allergy;
	private String antecedents;
	private String riskFactors;
	private String comment;
	private List<SurgicalHistoryDTO> surgicalHistory;
	private List<String> medicationHistory;
	private List<String> vaccins;
	private ClientDTO client;
	private UserDTO doctor;
	
	
	public FolderDTO() {
		super();
		surgicalHistory = new ArrayList<SurgicalHistoryDTO>();
		medicationHistory =  new ArrayList<String>();
		addiction = new ArrayList<String>();
		vaccins =  new ArrayList<String>();
		client = new ClientDTO();
		doctor = new UserDTO();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	public String getAllergy() {
		return allergy;
	}


	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}


	public String getAntecedents() {
		return antecedents;
	}


	public void setAntecedents(String antecedents) {
		this.antecedents = antecedents;
	}


	public String getRiskFactors() {
		return riskFactors;
	}


	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<SurgicalHistoryDTO> getSurgicalHistory() {
		return surgicalHistory;
	}


	public void setSurgicalHistory(List<SurgicalHistoryDTO> surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}


	public List<String> getMedicationHistory() {
		return medicationHistory;
	}


	public void setMedicationHistory(List<String> medicationHistory) {
		this.medicationHistory = medicationHistory;
	}


	public List<String> getVaccins() {
		return vaccins;
	}


	public void setVaccins(List<String> vaccins) {
		this.vaccins = vaccins;
	}

	public ClientDTO getClient() {
		return client;
	}


	public void setClient(ClientDTO client) {
		this.client = client;
	}


	public UserDTO getDoctor() {
		return doctor;
	}


	public void setDoctor(UserDTO doctor) {
		this.doctor = doctor;
	}


	@Override
	public String toString() {
		return "FolderDTO [id=" + id + ", addiction=" + addiction + ", bloodGroup=" + bloodGroup + ", size=" + size
				+ ", weight=" + weight + ", allergy=" + allergy + ", antecedents=" + antecedents + ", riskFactors="
				+ riskFactors + ", comment=" + comment + ", surgicalHistory=" + surgicalHistory + ", medicationHistory="
				+ medicationHistory + ", vaccins=" + vaccins + "]";
	}
	
	

}
