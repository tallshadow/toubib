package com.adouae.toubib.impl.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "biologies", catalog = "toubib")
public class Biology {

	private int id;
	private String biology;
	
	public Biology() {
		super();
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "biology", length = 500)
	public String getBiology() {
		return biology;
	}

	public void setBiology(String biology) {
		this.biology = biology;
	}
	
	 @Override
	  public String toString() {
	    return String.format("Biology [%s, %s]", id, biology);
	  }
	
}
