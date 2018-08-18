package com.adouae.toubib.impl.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drugPatient", catalog = "toubib")
public class DrugPatient {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "Id_drugPatient", unique = true, length = 10)
	private Long id;
	private String drug;
	private Integer amount;
	private Integer morning;
	private Integer noon;
	private Integer evening;
	private Date startDate; 
	private Integer duration; 
	private String date;
	private String note;
	//private Order order;
	
	public DrugPatient() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "drug", length = 100)
	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	@Column(name = "amount",  length = 10)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "morning",  length = 10)
	public Integer getMorning() {
		return morning;
	}

	public void setMorning(Integer morning) {
		this.morning = morning;
	}

	@Column(name = "noon",  length = 10)
	public Integer getNoon() {
		return noon;
	}

	public void setNoon(Integer noon) {
		this.noon = noon;
	}

	@Column(name = "evening",  length = 10)
	public Integer getEvening() {
		return evening;
	}

	public void setEvening(Integer evening) {
		this.evening = evening;
	}

	@Column(name = "startDate")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "duration",  length = 10)
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "date", length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		
		return String.format("DrugPatient [%d, %s]", id, drug);
	}


}
