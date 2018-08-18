package com.adouae.toubib.impl.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "folder", catalog = "toubib")
public class Folder {
	
	private Long id;
	private String bloodGroup;
	private String size;
	private String weight;
	private String allergy;
	private String antecedents;
	private String riskFactors;
	private String comment;
	private Collection<SurgicalHistory> surgicalHistory;
    private Collection<String> medicationHistory ;
    private Collection<String> addiction;
    private Collection<String> vaccins ;
	private Client client;
	private Doctor doctor;
	
	
	public Folder() {
		super();
		surgicalHistory = new ArrayList<SurgicalHistory>();
		addiction = new ArrayList<String>();
		vaccins = new ArrayList<String>();
		medicationHistory = new ArrayList<String>();
		client = new Client();
		doctor = new Doctor();
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "bloodGroup")
	public String getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	@Column(name = "size", length = 4)
	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	@Column(name = "weight", length = 3)
	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	@Column(name = "allergy", length = 100)
	public String getAllergy() {
		return allergy;
	}


	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}


	@Column(name = "antecedents", length = 500)
	public String getAntecedents() {
		return antecedents;
	}


	public void setAntecedents(String antecedents) {
		this.antecedents = antecedents;
	}


	@Column(name = "riskFactors", length = 500)
	public String getRiskFactors() {
		return riskFactors;
	}


	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}


	@Column(name = "comment", length = 500)
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@OneToMany(cascade = CascadeType.ALL,targetEntity=SurgicalHistory.class)
	public Collection<SurgicalHistory> getSurgicalHistory() {
		return surgicalHistory;
	}


	public void setSurgicalHistory(Collection<SurgicalHistory> surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}


	@ElementCollection
	@Column(name="vaccines")
	public Collection<String> getVaccins() {
		return vaccins;
	}


	public void setVaccins(Collection<String> vaccins) {
		this.vaccins = vaccins;
	}


	@JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
	@OneToOne
	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	@JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	@ElementCollection
	@Column(name="medicationHistory")
	public Collection<String> getMedicationHistory() {
		return medicationHistory;
	}


	public void setMedicationHistory(Collection<String> medicationHistory) {
		this.medicationHistory = medicationHistory;
	}


	@ElementCollection
	@Column(name="addiction")
	public Collection<String> getAddiction() {
		return addiction;
	}


	public void setAddiction(Collection<String> addiction) {
		this.addiction = addiction;
	}

	@Override
	public String toString() {
		return "FolderDTO [id=" + id + ", addiction=" + addiction + ", bloodGroup=" + bloodGroup + ", size=" + size
				+ ", weight=" + weight + ", allergy=" + allergy + ", antecedents=" + antecedents + ", riskFactors="
				+ riskFactors + ", comment=" + comment + ", surgicalHistory=" + surgicalHistory + ", medicationHistory="
				+ medicationHistory + ", vaccins=" + vaccins + "]";
	}
	



}
