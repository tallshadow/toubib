package com.adouae.toubib.impl.persistence.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user", catalog = "toubib")
@DiscriminatorColumn(name="role")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String email;
	private String password;
	private Address address;
	private String statut;
	private String job;
	private int version;
	//private Set<Role> roles = new HashSet<Role>(0);
	//private Client client;
	//private Doctor doctor;
	private String gender;

	
	
	public User() {
		super();
	}
	
	public User(String email, String password,
			String statut) {
		this.email = email;
		this.password = password;
		this.statut = statut;
	}
	
	public User(Long id,String firstName, String lastName,Date dateOfBirth, String email, String password,Address address, Set<Role> roles,
			Client client, Doctor doctor, String statut) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.address = address;
		//this.roles = roles;
		//this.client = client;
		//this.doctor = doctor;
		this.statut = statut;
	}


	public User(Long id) {
	    this.id = id;
	  }

	public User(Long id, String lastName, int version, String firstName) {
		this.id =id;
		this.lastName=lastName;
		this.version=version;
		this.lastName=lastName;
		
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "firstName", length = 22)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastName", length = 22)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email", length = 254, unique=true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 254)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "statut", length = 254)
	public String getStatut() {
		return this.statut;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//	public Set<Role> getRoles() {
//		return this.roles;
//	}

//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}

//	@OneToOne(fetch = FetchType.LAZY)
//	public Client getClient() {
//		return this.client;
//	}
//
//	public void setClient(Client client) {
//		this.client = client;
//	}
	
//	@OneToOne(fetch = FetchType.LAZY)
//	public Doctor getDoctor() {
//		return doctor;
//	}

//	public void setDoctor(Doctor doctor) {
//		this.doctor = doctor;
//	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "dateOfBirth")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "job", length = 100)
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", password=" + password + ", address=" + address + ", statut=" + statut
				 + ", job=" + job + ", version=" + version + ", client="
				+  ", gender=" + gender + "]";
	}
	
	
	
}

