package com.adouae.toubib.domain.dto;

import java.util.Date;

public class PaymentDTO {
	
	private Integer id;
	private String fee;
	private String motto;
	private String methodPayment;
	private Date date;
	private ClientDTO client;
	private UserDTO doctor;
	
	
	public PaymentDTO() {
		super();
		client = new ClientDTO();
		doctor = new UserDTO();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public String getMethodPayment() {
		return methodPayment;
	}
	public void setMethodPayment(String methodPayment) {
		this.methodPayment = methodPayment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
		return "PaymentDTO [id=" + id + ", fee=" + fee + ", motto=" + motto + ", methodPayment=" + methodPayment
				+ ", date=" + date + "]";
	}
	
	
	

}
