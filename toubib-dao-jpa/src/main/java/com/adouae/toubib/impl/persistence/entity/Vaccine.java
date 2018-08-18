package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vaccines", catalog = "toubib")
public class Vaccine {

	private int id;
	private String Vaccine;
	
	public Vaccine() {
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
	
	@Column(name = "vaccine", length = 500)
	public String getVaccine() {
		return Vaccine;
	}
	public void setVaccine(String drug) {
		this.Vaccine = drug;
	}
	
	 @Override
	  public String toString() {
	    return String.format("Vaccine [%s, %s]", id, Vaccine);
	  }
}
