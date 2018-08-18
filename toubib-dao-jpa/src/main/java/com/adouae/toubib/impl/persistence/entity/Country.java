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
@Table(name = "country", catalog = "toubib")
public class Country implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	String country;
	private Collection<City> cities;

	public Country(Integer id, String country) {
		super();
		this.id = id;
		this.country = country;
	}

	public Country() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "country", length = 50)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@OneToMany(mappedBy="country")
	public Collection<City> getCities() {
		return cities;
	}

	public void setCities(Collection<City> cities) {
		this.cities = cities;
	}

	
	

}
