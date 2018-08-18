package com.adouae.toubib.domain.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;



public class OrderDTO {
	
	private int id;
	private List<DrugPatientDTO> drug;
	private String biology;
	private String radiology;
	private Date day;
	private ClientDTO client;
	private UserDTO doctor;
	
	public OrderDTO() {
		super();
		drug = new ArrayList<DrugPatientDTO>();
		client = new ClientDTO();
		doctor = new UserDTO();
 	}

	
	
	public OrderDTO(List<DrugPatientDTO> drug, String biology, String radiology, Date day) {
		super();
		this.drug = drug;
		this.biology = biology;
		this.radiology = radiology;
		this.day = day;
	}



	public OrderDTO(List<DrugPatientDTO> drug, String biology, String radiology, Date day, ClientDTO client,
			UserDTO doctor) {
		super();
		this.drug = drug;
		this.biology = biology;
		this.radiology = radiology;
		this.day = day;
		this.client = client;
		this.doctor = doctor;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<DrugPatientDTO> getDrug() {
		return drug;
	}

	public void setDrug(ArrayList<DrugPatientDTO> drug) {
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

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
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
		return "OrderDTO [id=" + id + ", drug=" + drug + ", biology=" + biology + ", radiology=" + radiology + ", day="
				+ day + "]";
	}

	public void setDrug(List<DrugPatientDTO> drug) {
		this.drug = drug;
	}

	
	
}
