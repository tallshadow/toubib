package com.adouae.toubib.impl.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//@MappedSuperclass
@Table(name = "doctors")
@Entity
//@DiscriminatorValue("D")
public class Doctor extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<Client> clients;

	// constructeurs
	public Doctor() {
		super();
		this.clients = new ArrayList<Client>();
	}

	public Doctor(Long id) {
		super(id);
	}

	public Doctor(Long id, String lastName, int version, String firstName) {
		super(id, lastName, version, firstName);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (getId() != null ? getId().hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Doctor)) {
			return false;
		}
		Doctor other = (Doctor) object;
		if ((this.getId() == null && other.getId() != null)
				|| (this.getId() != null && !this.getId().equals(other.getId()))) {
			return false;
		}
		return true;
	}

	@ManyToMany
	public Collection<Client> getClients() {
		return clients;
	}

	public void setClients(Collection<Client> clients) {
		this.clients = clients;
	}
	
//	@Override
//	public String toString() {
//		return "Doctor [clients=" + clients + ", getId()=" + getId() + ", getFirstName()=" + getFirstName()
//				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + "]";
//	}
}
