package com.adouae.toubib.domain.dto;

public class RadiologyDTO {
	
	private int id;
	private String radiology;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
