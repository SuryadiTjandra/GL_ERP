package ags.goldenlionerp.application.addresses.address;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressCategories {

	@Column(name="ADCC01")
	private String categoryCode01 = "";
	
	@Column(name="ADCC02")
	private String categoryCode02 = "";
	
	@Column(name="ADCC03")
	private String categoryCode03 = "";
	
	@Column(name="ADCC04")
	private String categoryCode04 = "";
	
	@Column(name="ADCC05")
	private String categoryCode05 = "";
	
	@Column(name="ADCC06")
	private String categoryCode06 = "";
	
	@Column(name="ADCC07")
	private String categoryCode07 = "";
	
	@Column(name="ADCC08")
	private String categoryCode08 = "";
	
	@Column(name="ADCC09")
	private String categoryCode09 = "";
	
	@Column(name="ADCC10")
	private String categoryCode10 = "";
	
	AddressCategories() {}

	public String getCategoryCode01() {
		return categoryCode01;
	}

	public String getCategoryCode02() {
		return categoryCode02;
	}

	public String getCategoryCode03() {
		return categoryCode03;
	}

	public String getCategoryCode04() {
		return categoryCode04;
	}

	public String getCategoryCode05() {
		return categoryCode05;
	}

	public String getCategoryCode06() {
		return categoryCode06;
	}

	public String getCategoryCode07() {
		return categoryCode07;
	}

	public String getCategoryCode08() {
		return categoryCode08;
	}

	public String getCategoryCode09() {
		return categoryCode09;
	}

	public String getCategoryCode10() {
		return categoryCode10;
	}

	void setCategoryCode01(String categoryCode01) {
		this.categoryCode01 = categoryCode01;
	}

	void setCategoryCode02(String categoryCode02) {
		this.categoryCode02 = categoryCode02;
	}

	void setCategoryCode03(String categoryCode03) {
		this.categoryCode03 = categoryCode03;
	}

	void setCategoryCode04(String categoryCode04) {
		this.categoryCode04 = categoryCode04;
	}

	void setCategoryCode05(String categoryCode05) {
		this.categoryCode05 = categoryCode05;
	}

	void setCategoryCode06(String categoryCode06) {
		this.categoryCode06 = categoryCode06;
	}

	void setCategoryCode07(String categoryCode07) {
		this.categoryCode07 = categoryCode07;
	}

	void setCategoryCode08(String categoryCode08) {
		this.categoryCode08 = categoryCode08;
	}

	void setCategoryCode09(String categoryCode09) {
		this.categoryCode09 = categoryCode09;
	}

	void setCategoryCode10(String categoryCode10) {
		this.categoryCode10 = categoryCode10;
	}
	
	
}
