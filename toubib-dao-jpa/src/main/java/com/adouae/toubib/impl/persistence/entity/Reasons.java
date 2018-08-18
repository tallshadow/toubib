package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reasons", catalog = "toubib")
public class Reasons {
	private int id;
	private String reasons;
	
	public Reasons() {
		super();
	}
	
	
	public Reasons(int id, String reason) {
		super();
		this.id = id;
		this.reasons = reason;
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "reason", length = 500)
	public String getReason() {
		return reasons;
	}
	public void setReason(String reason) {
		this.reasons = reason;
	}
	
}
