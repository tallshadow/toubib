package com.adouae.toubib.domain.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.adouae.toubib.impl.persistence.entity.Address;

public class ClientDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Address address;
	private String city;
	private String socialSituation;
	private String familySituation;
	private String job;
	private Integer numberOfChildren;
	private String medicalSituation;
	private String treatingDoctor;
	private String sentBy;
	private String mutual;
	private String gender;
	private String title;
	private Collection<UserDTO> doctors;
	
	
	public ClientDTO() {
		super();
		this.address = new Address();
		this.doctors = new ArrayList<UserDTO>();
		
	}
	
	public ClientDTO(Long id, String firstName, String lastName, Date dateOfBirth, Address address, String city,
			 String socialSituation, String socialFamiliale, String job,
			Integer numberOfChildren, String medicalSituation, String doctor, String sentBy, String mutual) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.socialSituation = socialSituation;
		this.familySituation = socialFamiliale;
		this.job = job;
		this.numberOfChildren = numberOfChildren;
		this.medicalSituation = medicalSituation;
		this.treatingDoctor = doctor;
		this.sentBy = sentBy;
		this.mutual = mutual;
	}


	// getters & setters
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSocialSituation() {
		return socialSituation;
	}
	public void setSocialSituation(String socialSituation) {
		this.socialSituation = socialSituation;
	}
	
	public String getFamilySituation() {
		return familySituation;
	}

	public void setFamilySituation(String familySituation) {
		this.familySituation = familySituation;
	}

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public String getMedicalSituation() {
		return medicalSituation;
	}
	public void setMedicalSituation(String medicalSituation) {
		this.medicalSituation = medicalSituation;
	}
	
	public String getTreatingDoctor() {
		return treatingDoctor;
	}

	public void setTreatingDoctor(String treatingDoctor) {
		this.treatingDoctor = treatingDoctor;
	}

	public String getMutual() {
		return mutual;
	}
	public void setMutual(String mutual) {
		this.mutual = mutual;
	}
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Collection<UserDTO> getDoctors() {
		return doctors;
	}

	public void setDoctors(Collection<UserDTO> doctors) {
		this.doctors = doctors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + ", city=" + city + ", socialSituation=" + socialSituation
				+ ", familySituation=" + familySituation + ", job=" + job + ", numberOfChildren=" + numberOfChildren
				+ ", medicalSituation=" + medicalSituation + ", treatingDoctor=" + treatingDoctor + ", sentBy=" + sentBy
				+ ", mutual=" + mutual + ", gender=" + gender + ", title=" + title + ", doctors=" + doctors + "]";
	}

	
	
}
