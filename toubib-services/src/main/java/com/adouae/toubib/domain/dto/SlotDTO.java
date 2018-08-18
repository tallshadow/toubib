package com.adouae.toubib.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class SlotDTO implements Serializable {

	  private static final long serialVersionUID = 1L;
	 
	  private Long id;
	  
	  
	  private Date startDate;
	  
	 
	  private Date endDate;
	  private Boolean allDay;
	  
	  private Boolean isAllDay;
	  
	  
	  private int version;
	  
	 
	  private UserDTO doctor;

	  // constructeurs
	  public SlotDTO() {
		  super();
		  this.doctor = new UserDTO();
	  }

	  public SlotDTO(Long id) {
	    this.id = id;
	  }

	public SlotDTO(Long id, Date startDate, Date endDate, int version, UserDTO doctor) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.version = version;
		this.doctor = doctor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(UserDTO doctor) {
		this.doctor = doctor;
	}
	

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}
	
	public Boolean getIsAllDay() {
		return isAllDay;
	}

	public void setIsAllDay(Boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	@Override
	  public int hashCode() {
	    int hash = 0;
	    hash += (id != null ? id.hashCode() : 0);
	    return hash;
	  }

	
	@Override
	public String toString() {
		return "Slot [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", version=" + version
				+ ", doctor=" + doctor + "]";
	}

	
	  
	}
