package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "radiology", catalog = "toubib")
public class Radiology {
	
	private int id;
	private String radiology;
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "radiology", length = 500)
	public String getRadiology() {
		return radiology;
	}
	public void setRadiology(String radiology) {
		this.radiology = radiology;
	}
	
	@Override
	  public String toString() {
	    return String.format("Radiology [%s, %s]", id, radiology);
	  }

}