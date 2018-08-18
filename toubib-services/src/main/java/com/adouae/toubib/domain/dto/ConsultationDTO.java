package com.adouae.toubib.domain.dto;

import java.util.Date;

public class ConsultationDTO {
	private int id;
	private String reason;
	private String examination;
	private String diagnostics;
	private ClientDTO client;
	private UserDTO doctor;
	private Date day;
	
	public ConsultationDTO() {
		super();
	}
	
	public ConsultationDTO(int id, String reason, String examination, String diagnostics, ClientDTO client,UserDTO doctor , Date day) {
		super();
		this.id = id;
		this.reason = reason;
		this.examination = examination;
		this.diagnostics = diagnostics;
		this.client = client;
		this.doctor = doctor;
		this.day = day;
	}

	public ConsultationDTO(String reason, String examination, String diagnostics, ClientDTO selectedClient,UserDTO doctor, Date day) {
		super();
		this.reason = reason;
		this.examination = examination;
		this.diagnostics = diagnostics;
		this.client = selectedClient;
		this.doctor = doctor;
		this.day = day;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}
	public String getDiagnostics() {
		return diagnostics;
	}
	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
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
		return "ConsultationDTO [id=" + id + ", reason=" + reason + ", examination=" + examination + ", diagnostics="
				+ diagnostics + ", client=" + client + ", doctor=" + doctor + ", day=" + day + "]";
	}
	
}
