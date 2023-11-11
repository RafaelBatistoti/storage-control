package com.projeto.estoque.dto;

import com.projeto.estoque.encrypt.Encrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Item {

	@Id
	private String code;

	private String name;

	@Convert(converter = Encrypt.class)
	@Column(name = "description")
	private String desc;

	@Column(name = "quantity")
	private String qnt;

	@Convert(converter = Encrypt.class)
	private String price;

	@Column(name = "supply")
	private String suplly;

	public Item() {
	}

	public Item(String code, String name, String desc, String qnt, String price, String suplly) {
		super();
		this.code = code;
		this.name = name;
		this.desc = desc;
		this.qnt = qnt;
		this.price = price;
		this.suplly = suplly;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getQnt() {
		return qnt;
	}

	public void setQnt(String qnt) {
		this.qnt = qnt;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSuplly() {
		return suplly;
	}

	public void setSuplly(String suplly) {
		this.suplly = suplly;
	}

	@Override
	public String toString() {
		return "Item [code=" + code + ", name=" + name + ", desc=" + desc + ", qnt=" + qnt + ", price=" + price
				+ ", suplly=" + suplly + "]";
	}
}
