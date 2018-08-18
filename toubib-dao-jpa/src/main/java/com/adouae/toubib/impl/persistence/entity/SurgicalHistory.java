package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "surgicalHistory", catalog = "toubib")
public class SurgicalHistory {

	private Long id;
	private String act;
	private String operator;
	private String place;
	private String evolution;
	private String comment;
	
	
	public SurgicalHistory() {
		super();
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "act", length = 100)
	public String getAct() {
		return act;
	}


	public void setAct(String act) {
		this.act = act;
	}


	@Column(name = "operator", length = 100)
	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	@Column(name = "place", length = 100)
	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	@Column(name = "evolution", length = 200)
	public String getEvolution() {
		return evolution;
	}


	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}


	@Column(name = "comment", length = 500)
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "SurgicalHistoryDAO [id=" + id + ", act=" + act + ", operator=" + operator + ", place=" + place
				+ ", evolution=" + evolution + ", comment=" + comment + "]";
	}
	
	
}
