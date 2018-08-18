package com.adouae.toubib.domain.dto;

public class ReasonsDTO {
	private int id;
	private String reasons;
	
	public ReasonsDTO() {
		super();
	}
	
	
	public ReasonsDTO(int id, String reason) {
		super();
		this.id = id;
		this.reasons = reason;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getReason() {
		return reasons;
	}
	public void setReason(String reason) {
		this.reasons = reason;
	}
	
}
