package com.adouae.toubib.impl.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Basic(optional = false)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Basic(optional = false)
	  @Column(name = "title")
	  private String title;
	  
	  @Basic(optional = false)
	  @Column(name = "reason")
	  private String reason;
	  
	  @Basic(optional = false)
	  @Column(name = "place")
	  private String place;
	  
	  @Basic(optional = false)
	  @Column(name = "category")
	  private String category;
	  
	  @Basic(optional = false)
	  @Column(name = "isImportant")
	  private Boolean isImportant;
	  
	  @Basic(optional = false)
	  @Column(name = "DAY")
	  @Temporal(TemporalType.DATE)
	  private Date day;
	  
	  @JoinColumn(name = "ID_SLOT", referencedColumnName = "ID")
	  @ManyToOne(optional = false,cascade=CascadeType.ALL)
	  private Slot slot;
	  
	  @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
	  @ManyToOne(optional = false)
	  private Client client;

	  public Appointment() {
		  this.client = new Client();
		  this.slot = new Slot();
	  }

	  public Appointment(Long id) {
	    this.id = id;
	  }

	  public Appointment(Long id, Date day) {
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

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	  public boolean equals(Object object) {
	    // TODO: Warning - this method won't work in the case the id fields are not set
	    if (!(object instanceof Appointment)) {
	      return false;
	    }
	    Appointment other = (Appointment) object;
	    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	      return false;
	    }
	    return true;
	  }

	  @Override
	  public String toString() {
	    return String.format("Appointment[%s, %s, %s]", id, slot, client);
	  }
	}
