package com.adouae.toubib.impl.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "slot")
@Component
@Transactional
public class Slot implements Serializable {

	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Basic(optional = false)
	  @Column(name = "id")
	  private Long id;
	  
	  @Basic(optional = false)
	  @Column(name = "startDate")
	  private Date startDate;
	  
	  @Basic(optional = false)
	  @Column(name = "endDate")
	  private Date endDate;
	  
	  @Basic(optional = false)
	  @Column(name = "isAllDay")
	  private Boolean isAllDay;
	  
	  @Basic(optional = false)
	  @Column(name = "VERSION")
	  @Version
	  private int version;
	  
	  @JoinColumn(name = "ID_DOCTOR", referencedColumnName = "ID")
	  @ManyToOne(optional = false)
	  private Doctor doctor;

	  // constructeurs
	  public Slot() {
		  super();
		  this.doctor = new Doctor();
	  }

	  public Slot(Long id) {
	    this.id = id;
	  }

	public Slot(Long id, Date startDate, Date endDate, int version, Doctor doctor) {
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

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Override
	  public int hashCode() {
	    int hash = 0;
	    hash += (id != null ? id.hashCode() : 0);
	    return hash;
	  }

	
	
	  public Boolean getIsAllDay() {
		return isAllDay;
	}

	public void setIsAllDay(Boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	@Override
	  public boolean equals(Object object) {
	    // TODO: Warning - this method won't work in the case the id fields are not set
	    if (!(object instanceof Slot)) {
	      return false;
	    }
	    Slot other = (Slot) object;
	    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	      return false;
	    }
	    return true;
	  }

	@Override
	public String toString() {
		return "Slot [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", version=" + version
				+ ", doctor=" + doctor + "]";
	}

	
	  
	}
