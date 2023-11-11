package com.projeto.estoque.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	private String phone;

	@Column(name = "name")
	private String name;

	@Column(name = "zipCode")
	private String zipCode;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "number")
	private String number;

	@Column(name = "complement")
	private String complement;

	public Pessoa() {
	}

	public Pessoa(String phone, String name, String zipCode, String street, String city, String state, String number,
			String complement) {
		super();
		this.phone = phone;
		this.name = name;
		this.zipCode = zipCode;
		this.street = street;
		this.city = city;
		this.state = state;
		this.number = number;
		this.complement = complement;

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Override
	public String toString() {
		return "Pessoa [phone=" + phone + ", name=" + name + ", zipCode=" + zipCode + ", street=" + street + ", city="
				+ city + ", state=" + state + ", number=" + number + ", complement=" + complement + "]";

	}
}


