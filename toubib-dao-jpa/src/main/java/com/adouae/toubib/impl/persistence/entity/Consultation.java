package com.adouae.toubib.impl.persistence.entity;

import java.io.Serializable;
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
@Table(name = "consultation", catalog = "toubib")
public class Consultation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String reason;
	private String examination;
	private String diagnostics;
	private Client client;
	private Doctor doctor;
	private Date day;

	public Consultation() {
		super();
		client = new Client();
		doctor = new Doctor();
	}	

	public Consultation(int id, String reason, String examination, String diagnostics, Client client, Date day, Doctor doctor) {
		super();
		this.id = id;
		this.reason = reason;
		this.examination = examination;
		this.diagnostics = diagnostics;
		this.client = client;
		this.doctor = doctor;
		this.day = day;
	}

	public Consultation( String reason, String examination, String diagnostics, Client client, Date day) {
		super();
		this.reason = reason;
		this.examination = examination;
		this.diagnostics = diagnostics;
		this.client = client;
		this.day = day;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "reason", length = 1000)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "examination", length = 1000)
	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	@Column(name = "diagnostics", length = 1000)
	public String getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
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

	@Basic(optional = false)
	@Column(name = "DAY")
	@Temporal(TemporalType.DATE)
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Consultation [id=" + id + ", reason=" + reason + ", examination=" + examination + ", diagnostics="
				+ diagnostics + ", client=" + client + ", doctor=" + doctor + ", day=" + day + "]";
	}
	
	

}
