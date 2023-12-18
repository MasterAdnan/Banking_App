package com.Bank.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
//	@NotEmpty(message = "The Name should not be empty")
//	@Size(min = 4, max = 15)
	private String Fullname;
	
//	@NotEmpty(message = "The Mobile number should not be empty")
	private String Mobile_Number;
	
//	@Pattern(regexp = "([a-zA-Z0-9] + (?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA_Z]{2,})", message = "Please Enter a Valid Email" )
	private String email;
	
	private String dob;
	
	private String address;
	
	private String gender;
	
	private String aadhar_number;
	
	private String pan_number;
	
	private String city;
	
	private String state;
	
	private String user_id;
	
//	@OneToOne(mappedBy = "User", cascade = CascadeType.ALL)
//	private Account account;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setFullname(String fullname) {
		Fullname = fullname;
	}

	public String getMobile_Number() {
		return Mobile_Number;
	}

	public void setMobile_Number(String mobile_Number) {
		Mobile_Number = mobile_Number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAadhar_number() {
		return aadhar_number;
	}

	public void setAadhar_number(String aadhar_number) {
		this.aadhar_number = aadhar_number;
	}

	public String getPan_number() {
		return pan_number;
	}

	public void setPan_number(String pan_number) {
		this.pan_number = pan_number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Fullname=" + Fullname + ", Mobile_Number=" + Mobile_Number + ", email=" + email
				+ ", dob=" + dob + ", address=" + address + ", gender=" + gender + ", aadhar_number=" + aadhar_number
				+ ", pan_number=" + pan_number + ", city=" + city + ", state=" + state + ", user_id=" + user_id + "]";
	}
	
}
