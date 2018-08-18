package com.adouae.toubib.domain.dto;

public class BiologyDTO {

	private int id;
	private String biology;
	
	public BiologyDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
