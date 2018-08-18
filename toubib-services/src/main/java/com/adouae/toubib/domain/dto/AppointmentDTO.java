package com.adouae.toubib.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class AppointmentDTO implements Serializable {

	  private static final long serialVersionUID = 1L;
	  
	  private Long id;
	  
	  private String title;
	  
	  private String reason;

	  private String place;

	  private String category;
	  
	  private Boolean isImportant;
	  
	  private Date day;
	  
	  private SlotDTO slot;
	 
	  private ClientDTO client;

	  public AppointmentDTO() {
		  super();
		  client = new ClientDTO();
		  slot = new SlotDTO();
	  }

	  public AppointmentDTO(Long id) {
	    this.id = id;
	  }

	  public AppointmentDTO(Long id, Date day) {
	    this.id = id;
	    this.day = day;
	  }

	  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public Boolean getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	

	  public SlotDTO getSlot() {
		return slot;
	}

	public void setSlot(SlotDTO slot) {
		this.slot = slot;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "AppointmentDTO [id=" + id + ", title=" + title + ", reason=" + reason + ", place=" + place
				+ ", category=" + category + ", Importance=" + isImportant + ", day=" + day + ", slot=" + slot
				+ ", client=" + client + "]";
	}

	}
