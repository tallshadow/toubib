package com.adouae.toubib.domain.dto;

public class SurgicalHistoryDTO {

	private Long id;
	private String act;
	private String operator;
	private String place;
	private String evolution;
	private String comment;
	
	
	public SurgicalHistoryDTO() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAct() {
		return act;
	}


	public void setAct(String act) {
		this.act = act;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getEvolution() {
		return evolution;
	}


	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "SurgicalHistoryDTO [id=" + id + ", act=" + act + ", operator=" + operator + ", place=" + place
				+ ", evolution=" + evolution + ", comment=" + comment + "]";
	}
	
	
}
