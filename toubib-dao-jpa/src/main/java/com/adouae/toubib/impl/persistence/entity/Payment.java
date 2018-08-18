package com.adouae.toubib.impl.persistence.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "payment", catalog = "toubib")
public class Payment {
	
	private Integer id;
	private String fee;
	private String motto;
	private String methodPayment;
	private Date date;
	private Client client;
	private Doctor doctor;
	
	
	public Payment() {
		super();
		client = new Client();
		doctor = new Doctor();
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "fee", length = 50)
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	@Column(name = "motto", length = 50)
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	@Column(name = "methodPayment", length = 50)
	public String getMethodPayment() {
		return methodPayment;
	}
	public void setMethodPayment(String methodPayment) {
		this.methodPayment = methodPayment;
	}
	
	@Basic(optional = false)
	@Column(name = "day")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	@JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
	@ManyToOne(optional = false)
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
	@Override
	public String toString() {
		return "PaymentDTO [id=" + id + ", fee=" + fee + ", motto=" + motto + ", methodPayment=" + methodPayment
				+ ", date=" + date + "]";
	}
	
	
	

}
