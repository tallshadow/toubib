package com.adouae.toubib.domain.dto;

import java.util.Date;

import com.adouae.toubib.impl.persistence.entity.DrugPatient;
import com.adouae.toubib.impl.persistence.entity.Order;

public class DrugPatientDTO  {
	
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
	private Order order;
	
	public DrugPatientDTO() {
		super();
	}
	
	

	public DrugPatientDTO(String drug, Integer amount, Integer morning, Integer noon, Integer evening, Date startDate, Integer duration,
			String date, Order order) {
		super();
		this.drug = drug;
		this.amount = amount;
		this.morning = morning;
		this.noon = noon;
		this.evening = evening;
		this.startDate = startDate;
		this.duration = duration;
		this.date = date;
		this.order = order;
	}

	public DrugPatientDTO(String drug, Integer amount, Integer morning, Integer noon, Integer evening, Date startDate, Integer duration,
			String date) {
		super();
		this.drug = drug;
		this.amount = amount;
		this.morning = morning;
		this.noon = noon;
		this.evening = evening;
		this.startDate = startDate;
		this.duration = duration;
		this.date = date;
	}



	public DrugPatientDTO(DrugPatient drug) {
		super();
		this.id = drug.getId();
		this.drug = drug.getDrug();
		this.amount = drug.getAmount();
		this.morning = drug.getMorning();
		this.noon = drug.getNoon();
		this.evening = drug.getEvening();
		this.startDate = drug.getStartDate();
		this.duration = drug.getDuration();
		this.date = drug.getDate();
		this.note = drug.getNote();
		//this.order = drug.getOrder();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getMorning() {
		return morning;
	}

	public void setMorning(Integer morning) {
		this.morning = morning;
	}

	public Integer getNoon() {
		return noon;
	}

	public void setNoon(Integer noon) {
		this.noon = noon;
	}

	public Integer getEvening() {
		return evening;
	}

	public void setEvening(Integer evening) {
		this.evening = evening;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	@Override
	public String toString() {
		return "DrugPatientDTO [id=" + id + ", drug=" + drug + ", amount=" + amount + ", morning=" + morning + ", noon="
				+ noon + ", evening=" + evening + ", startDate=" + startDate + ", duration=" + duration + ", date="
				+ date + ", order=" + order + "]";
	}

	
}
