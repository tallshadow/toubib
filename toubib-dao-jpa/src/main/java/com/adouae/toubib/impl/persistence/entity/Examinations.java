package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "examinations", catalog = "toubib")
public class Examinations {
	private int id;
	private String examinations;
	
	public Examinations() {
		super();
	}
	
	public Examinations(int id, String examinations) {
		super();
		this.id = id;
		this.examinations = examinations;
	}



	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "examinations", length = 500)
	public String getExaminations() {
		return examinations;
	}

	public void setExaminations(String examinations) {
		this.examinations = examinations;
	}
	
}
