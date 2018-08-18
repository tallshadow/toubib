package com.adouae.toubib.impl.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "clients")
@Entity
public class Client extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String familySituation;
	private Integer numberOfChildren;
	private String treatingDoctor;
	private String sentBy;
	private String mutual;
	private Collection<Doctor> doctor;

	public Client() {
		super();
		this.doctor = new ArrayList<Doctor>();
	}

	public Client(String familySituation, String job, Integer numberOfChildren, String doctor, String sentBy,
			String mutual) {
		super();
		this.familySituation = familySituation;
		this.numberOfChildren = numberOfChildren;
		this.treatingDoctor = doctor;
		this.sentBy = sentBy;
		this.mutual = mutual;
	}

	public Client(Long id) {
		super(id);
	}

	public Client(Long id, String lastName, int version, String firstName) {
		super(id, lastName, version, firstName);
	}

	@Override
	public int hashCode() {
		Long id = getId();
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Client)) {
			return false;
		}
		Client other = (Client) object;
		if ((this.getId() == null && other.getId() != null)
				|| (this.getId() != null && !this.getLastName().equals(other.getId()))) {
			return false;
		}
		return true;
	}


	@Column(name = "familySituation", length = 20)
	public String getFamilySituation() {
		return familySituation;
	}

	public void setFamilySituation(String familySituation) {
		this.familySituation = familySituation;
	}

	@Column(name = "numberOfChildren", length = 10)
	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	
	@Column(name = "treatingDoctor", length = 60)
	public String getTreatingDoctor() {
		return treatingDoctor;
	}

	public void setTreatingDoctor(String treatingDoctor) {
		this.treatingDoctor = treatingDoctor;
	}

	@Column(name = "sentBy", length = 60)
	public String getSentBy() {
		return sentBy;
	}

	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	@Column(name = "mutual", length = 20)
	public String getMutual() {
		return mutual;
	}

	public void setMutual(String mutual) {
		this.mutual = mutual;
	}

	@ManyToMany
	public Collection<Doctor> getDoctor() {
		return doctor;
	}

	public void setDoctor(Collection<Doctor> doctor) {
		this.doctor = doctor;
	}

//	@Override
//	public String toString() {
//		return "Client ["+"Id=" + getId() +  "familySituation=" + familySituation + ", numberOfChildren=" + numberOfChildren
//				+ ", treatingDoctor=" + treatingDoctor + ", sentBy=" + sentBy + ", mutual=" + mutual + ", doctor="
//				+ doctor + "]";
//	}

	
}
