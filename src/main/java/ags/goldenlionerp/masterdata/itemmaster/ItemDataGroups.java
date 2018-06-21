package ags.goldenlionerp.masterdata.itemmaster;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemDataGroups {
	@Column(name="IMITC01")
	private String categoryCode = "";
	
	@Column(name="IMITC02")
	private String brandCode = "";
	
	@Column(name="IMITC03")
	private String typeCode = "";
	
	@Column(name="IMITC04")
	private String categoryCode4 = "";
	
	@Column(name="IMITC05")
	private String landedCostRule = "";
	
	@Column(name="IMITC06")
	private String categoryCode6 = "";
	
	@Column(name="IMITC07")
	private String categoryCode7 = "";
	
	@Column(name="IMITC08")
	private String categoryCode8 = "";
	
	@Column(name="IMITC09")
	private String categoryCode9 = "";
	
	@Column(name="IMITC10")
	private String categoryCode10 = "";
	
	@Column(name="IMSLRC1")
	private String salesReportingCode1 = "";
	
	@Column(name="IMSLRC2")
	private String salesReportingCode2 = "";
	
	@Column(name="IMSLRC3")
	private String salesReportingCode3 = "";
	
	@Column(name="IMSLRC4")
	private String salesReportingCode4 = "";
	
	@Column(name="IMSLRC5")
	private String salesReportingCode5 = "";
	
	@Column(name="IMSLRC6")
	private String salesReportingCode6 = "";
	
	@Column(name="IMSLRC7")
	private String salesReportingCode7 = "";
	
	@Column(name="IMSLRC8")
	private String salesReportingCode8 = "";
	
	@Column(name="IMSLRC9")
	private String salesReportingCode9 = "";
	
	@Column(name="IMSLRC0")
	private String salesReportingCode0 = "";

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getCategoryCode4() {
		return categoryCode4;
	}

	public String getLandedCostRule() {
		return landedCostRule;
	}

	public String getCategoryCode6() {
		return categoryCode6;
	}

	public String getCategoryCode7() {
		return categoryCode7;
	}

	public String getCategoryCode8() {
		return categoryCode8;
	}

	public String getCategoryCode9() {
		return categoryCode9;
	}

	public String getCategoryCode10() {
		return categoryCode10;
	}

	public String getSalesReportingCode1() {
		return salesReportingCode1;
	}

	public String getSalesReportingCode2() {
		return salesReportingCode2;
	}

	public String getSalesReportingCode3() {
		return salesReportingCode3;
	}

	public String getSalesReportingCode4() {
		return salesReportingCode4;
	}

	public String getSalesReportingCode5() {
		return salesReportingCode5;
	}

	public String getSalesReportingCode6() {
		return salesReportingCode6;
	}

	public String getSalesReportingCode7() {
		return salesReportingCode7;
	}

	public String getSalesReportingCode8() {
		return salesReportingCode8;
	}

	public String getSalesReportingCode9() {
		return salesReportingCode9;
	}

	public String getSalesReportingCode0() {
		return salesReportingCode0;
	}

	void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	void setCategoryCode4(String categoryCode4) {
		this.categoryCode4 = categoryCode4;
	}

	void setLandedCostRule(String landedCostRule) {
		this.landedCostRule = landedCostRule;
	}

	void setCategoryCode6(String categoryCode6) {
		this.categoryCode6 = categoryCode6;
	}

	void setCategoryCode7(String categoryCode7) {
		this.categoryCode7 = categoryCode7;
	}

	void setCategoryCode8(String categoryCode8) {
		this.categoryCode8 = categoryCode8;
	}

	void setCategoryCode9(String categoryCode9) {
		this.categoryCode9 = categoryCode9;
	}

	void setCategoryCode10(String categoryCode10) {
		this.categoryCode10 = categoryCode10;
	}

	void setSalesReportingCode1(String salesReportingCode1) {
		this.salesReportingCode1 = salesReportingCode1;
	}

	void setSalesReportingCode2(String salesReportingCode2) {
		this.salesReportingCode2 = salesReportingCode2;
	}

	void setSalesReportingCode3(String salesReportingCode3) {
		this.salesReportingCode3 = salesReportingCode3;
	}

	void setSalesReportingCode4(String salesReportingCode4) {
		this.salesReportingCode4 = salesReportingCode4;
	}

	void setSalesReportingCode5(String salesReportingCode5) {
		this.salesReportingCode5 = salesReportingCode5;
	}

	void setSalesReportingCode6(String salesReportingCode6) {
		this.salesReportingCode6 = salesReportingCode6;
	}

	void setSalesReportingCode7(String salesReportingCode7) {
		this.salesReportingCode7 = salesReportingCode7;
	}

	void setSalesReportingCode8(String salesReportingCode8) {
		this.salesReportingCode8 = salesReportingCode8;
	}

	void setSalesReportingCode9(String salesReportingCode9) {
		this.salesReportingCode9 = salesReportingCode9;
	}

	void setSalesReportingCode0(String salesReportingCode0) {
		this.salesReportingCode0 = salesReportingCode0;
	}
	
	//for JPA
	ItemDataGroups() {}

	//for Jackson
	ItemDataGroups(String categoryCode, String brandCode, String typeCode, String categoryCode4,
			String landedCostRule, String categoryCode6, String categoryCode7, String categoryCode8,
			String categoryCode9, String categoryCode10, String salesReportingCode1, String salesReportingCode2,
			String salesReportingCode3, String salesReportingCode4, String salesReportingCode5,
			String salesReportingCode6, String salesReportingCode7, String salesReportingCode8,
			String salesReportingCode9, String salesReportingCode0) {
		super();
		this.categoryCode = categoryCode;
		this.brandCode = brandCode;
		this.typeCode = typeCode;
		this.categoryCode4 = categoryCode4;
		this.landedCostRule = landedCostRule;
		this.categoryCode6 = categoryCode6;
		this.categoryCode7 = categoryCode7;
		this.categoryCode8 = categoryCode8;
		this.categoryCode9 = categoryCode9;
		this.categoryCode10 = categoryCode10;
		this.salesReportingCode1 = salesReportingCode1;
		this.salesReportingCode2 = salesReportingCode2;
		this.salesReportingCode3 = salesReportingCode3;
		this.salesReportingCode4 = salesReportingCode4;
		this.salesReportingCode5 = salesReportingCode5;
		this.salesReportingCode6 = salesReportingCode6;
		this.salesReportingCode7 = salesReportingCode7;
		this.salesReportingCode8 = salesReportingCode8;
		this.salesReportingCode9 = salesReportingCode9;
		this.salesReportingCode0 = salesReportingCode0;
	}
	
	
}
