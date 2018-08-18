package com.adouae.toubib.impl.persistence.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "address", catalog = "toubib")
public class Address implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	Long id;
	String street;
	String city;
	String zip;
	String country;
	String phone;
	String email;
	private Collection<User> users; 	

	public Address( String street, String city, String zip,String phone,
			String country) {
		super();
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
		this.phone = phone;
		
	}


	public Address(){}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "street", length = 100)
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(name = "city", length = 100)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "zip", length = 22)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name = "country", length = 100)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@OneToMany(mappedBy="address") 
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
