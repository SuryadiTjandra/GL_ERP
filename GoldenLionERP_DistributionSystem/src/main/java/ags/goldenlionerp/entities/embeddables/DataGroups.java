package ags.goldenlionerp.entities.embeddables;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataGroups {
	@Column(name="ITC01")
	private String categoryCode = "";
	
	@Column(name="ITC02")
	private String brandCode = "";
	
	@Column(name="ITC03")
	private String typeCode = "";
	
	@Column(name="ITC04")
	private String categoryCode4 = "";
	
	@Column(name="ITC05")
	private String landedCostRule = "";
	
	@Column(name="ITC06")
	private String categoryCode6 = "";
	
	@Column(name="ITC07")
	private String categoryCode7 = "";
	
	@Column(name="ITC08")
	private String categoryCode8 = "";
	
	@Column(name="ITC09")
	private String categoryCode9 = "";
	
	@Column(name="ITC10")
	private String categoryCode10 = "";
	
	@Column(name="SLRC1")
	private String salesReportingCode1 = "";
	
	@Column(name="SLRC2")
	private String salesReportingCode2 = "";
	
	@Column(name="SLRC3")
	private String salesReportingCode3 = "";
	
	@Column(name="SLRC4")
	private String salesReportingCode4 = "";
	
	@Column(name="SLRC5")
	private String salesReportingCode5 = "";
	
	@Column(name="SLRC6")
	private String salesReportingCode6 = "";
	
	@Column(name="SLRC7")
	private String salesReportingCode7 = "";
	
	@Column(name="SLRC8")
	private String salesReportingCode8 = "";
	
	@Column(name="SLRC9")
	private String salesReportingCode9 = "";
	
	@Column(name="SLRC0")
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

	protected void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	protected void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	protected void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	protected void setCategoryCode4(String categoryCode4) {
		this.categoryCode4 = categoryCode4;
	}

	protected void setLandedCostRule(String landedCostRule) {
		this.landedCostRule = landedCostRule;
	}

	protected void setCategoryCode6(String categoryCode6) {
		this.categoryCode6 = categoryCode6;
	}

	protected void setCategoryCode7(String categoryCode7) {
		this.categoryCode7 = categoryCode7;
	}

	protected void setCategoryCode8(String categoryCode8) {
		this.categoryCode8 = categoryCode8;
	}

	protected void setCategoryCode9(String categoryCode9) {
		this.categoryCode9 = categoryCode9;
	}

	protected void setCategoryCode10(String categoryCode10) {
		this.categoryCode10 = categoryCode10;
	}

	protected void setSalesReportingCode1(String salesReportingCode1) {
		this.salesReportingCode1 = salesReportingCode1;
	}

	protected void setSalesReportingCode2(String salesReportingCode2) {
		this.salesReportingCode2 = salesReportingCode2;
	}

	protected void setSalesReportingCode3(String salesReportingCode3) {
		this.salesReportingCode3 = salesReportingCode3;
	}

	protected void setSalesReportingCode4(String salesReportingCode4) {
		this.salesReportingCode4 = salesReportingCode4;
	}

	protected void setSalesReportingCode5(String salesReportingCode5) {
		this.salesReportingCode5 = salesReportingCode5;
	}

	protected void setSalesReportingCode6(String salesReportingCode6) {
		this.salesReportingCode6 = salesReportingCode6;
	}

	protected void setSalesReportingCode7(String salesReportingCode7) {
		this.salesReportingCode7 = salesReportingCode7;
	}

	protected void setSalesReportingCode8(String salesReportingCode8) {
		this.salesReportingCode8 = salesReportingCode8;
	}

	protected void setSalesReportingCode9(String salesReportingCode9) {
		this.salesReportingCode9 = salesReportingCode9;
	}

	protected void setSalesReportingCode0(String salesReportingCode0) {
		this.salesReportingCode0 = salesReportingCode0;
	}

	
}
