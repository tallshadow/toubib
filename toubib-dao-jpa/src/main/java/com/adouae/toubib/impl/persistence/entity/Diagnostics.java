package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diagnostics", catalog = "toubib")
public class Diagnostics {
	private int id;
	private String diagnostics;
	
	public Diagnostics() {
		super();
	}
	
	
	public Diagnostics(int id, String diagnostics) {
		super();
		this.id = id;
		this.diagnostics = diagnostics;
	}


	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "diagnostics", length = 500)
	public String getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
	}	
	
}
