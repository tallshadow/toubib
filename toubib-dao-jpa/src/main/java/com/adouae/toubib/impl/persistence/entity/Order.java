package com.adouae.toubib.impl.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "order", catalog = "toubib")
public class Order {
	
	private Integer id;
	private Collection<DrugPatient> drug;
	private String biology;
	private String radiology;
	private Client client;
	private Doctor doctor;
	private Date day;
	
	public Order() {
		super();
		drug = new ArrayList<DrugPatient>();
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

	@OneToMany(cascade = CascadeType.ALL,targetEntity=DrugPatient.class)
	public Collection<DrugPatient> getDrug() {
		return drug;
	}

	public void setDrug(ArrayList<DrugPatient> drug) {
		this.drug = drug;
	}
	
	@Column(name = "biology", length = 200)
	public String getBiology() {
		return biology;
	}

	public void setDrug(Collection<DrugPatient> drug) {
		this.drug = drug;
	}

	public void setBiology(String biology) {
		this.biology = biology;
	}

	@Column(name = "radiology", length = 200)
	public String getRadiology() {
		return radiology;
	}

	public void setRadiology(String radiology) {
		this.radiology = radiology;
	}

	@Basic(optional = false)
	@Column(name = "day")
	@Temporal(TemporalType.DATE)
	public Date getDay() {
		return day;
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

	public void setDay(Date day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", drug=" + drug + ", biology=" + biology + ", radiology=" + radiology + ", client="
				+ client + ", doctor=" + doctor + ", day=" + day + "]";
	}

	
}

