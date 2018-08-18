package com.adouae.toubib.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.adouae.toubib.impl.persistence.entity.Address;
import com.adouae.toubib.impl.persistence.entity.Role;

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String password;
	private String statut;
	private Address address;
	private String phone;
	private String title;
	private String job;
	private int version;
	private Set<Role> roles = new HashSet<Role>(0);
	
	
	public UserDTO() {
		super();
		this.address = new Address();
		this.roles = new  HashSet<Role>(0);
	}

	public UserDTO(String email, String password, String statut) {
		super();
		this.email = email;
		this.password = password;
		this.statut = statut;
		this.address = new Address();
	}

	public UserDTO(Long id, String firstName, String lastName, String email, String password, String statut,
			Address address, String phone, String title, String job, int version) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.statut = statut;
		this.address = address;
		this.phone = phone;
		this.title = title;
		this.job = job;
		this.version = version;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", password=" + password + ", statut=" + statut + ", address=" + address
				+ ", phone=" + phone + ", title=" + title + ", job=" + job + ", version=" + version + ", roles=" + roles
				+ "]";
	}

	
	
}
