package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drugs", catalog = "toubib")
public class Drug {

	private int id;
	private String drug;
	
	public Drug() {
		super();
	}
	
	//getters and setters
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "drug", length = 500)
	public String getDrug() {
		return drug;
	}
	public void setDrug(String drug) {
		this.drug = drug;
	}
	
	 @Override
	  public String toString() {
	    return String.format("Drug [%s, %s]", id, drug);
	  }
}
