package com.adouae.toubib.domain.dto;

public class DrugDTO {

	private int id;
	private String drug;
	
	public DrugDTO() {
		super();
	}
	
	//getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
