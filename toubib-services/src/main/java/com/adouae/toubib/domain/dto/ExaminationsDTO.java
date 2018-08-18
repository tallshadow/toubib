package com.adouae.toubib.domain.dto;

public class ExaminationsDTO {
	
	private int id;
	private String examinations;
	
	public ExaminationsDTO() {
		super();
	}
	
	public ExaminationsDTO(int id, String examinations) {
		super();
		this.id = id;
		this.examinations = examinations;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getExaminations() {
		return examinations;
	}

	public void setExaminations(String examinations) {
		this.examinations = examinations;
	}
	
}
