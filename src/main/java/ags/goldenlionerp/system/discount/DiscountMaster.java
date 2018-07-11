package ags.goldenlionerp.system.discount;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;
@Entity
@Table(name="T0026")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="DUUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="DUDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="DUTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="DUUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="DUDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="DUTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="DUCID"))
})
public class DiscountMaster extends DatabaseEntity {

	@Id
	@Column(name="DUDCCD")
	private String discountCode = "";
	
	@Column(name="DUDESB1")
	private String description = "";
	
	@Column(name="DUPCN01", precision=19, scale=15)
	private BigDecimal discountPercentage1 = new BigDecimal(0);
	
	@Column(name="DUPCN02", precision=19, scale=15)
	private BigDecimal discountPercentage2 = new BigDecimal(0);
	
	@Column(name="DUPCN03", precision=19, scale=15)
	private BigDecimal discountPercentage3 = new BigDecimal(0);
	
	@Column(name="DUPCN04", precision=19, scale=15)
	private BigDecimal discountPercentage4 = new BigDecimal(0);
	
	@Column(name="DUPCN05", precision=19, scale=15)
	private BigDecimal discountPercentage5 = new BigDecimal(0);
	
	@Column(name="DUAMT01", precision=19, scale=5)
	private BigDecimal discountAmount1 = new BigDecimal(0);
	
	@Column(name="DUAMT02", precision=19, scale=5)
	private BigDecimal discountAmount2 = new BigDecimal(0);
	
	@Column(name="DUAMT03", precision=19, scale=5)
	private BigDecimal discountAmount3 = new BigDecimal(0);
	
	@Column(name="DUAMT04", precision=19, scale=5)
	private BigDecimal discountAmount4 = new BigDecimal(0);
	
	@Column(name="DUAMT05", precision=19, scale=5)
	private BigDecimal discountAmount5 = new BigDecimal(0);
	
	@Column(name="DUOBID")
	private String objectId = "";

	public String getDiscountCode() {
		return discountCode;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getDiscountPercentage1() {
		return discountPercentage1;
	}

	public BigDecimal getDiscountPercentage2() {
		return discountPercentage2;
	}

	public BigDecimal getDiscountPercentage3() {
		return discountPercentage3;
	}

	public BigDecimal getDiscountPercentage4() {
		return discountPercentage4;
	}

	public BigDecimal getDiscountPercentage5() {
		return discountPercentage5;
	}

	public BigDecimal getDiscountAmount1() {
		return discountAmount1;
	}

	public BigDecimal getDiscountAmount2() {
		return discountAmount2;
	}

	public BigDecimal getDiscountAmount3() {
		return discountAmount3;
	}

	public BigDecimal getDiscountAmount4() {
		return discountAmount4;
	}

	public BigDecimal getDiscountAmount5() {
		return discountAmount5;
	}

	public String getObjectId() {
		return objectId;
	}

	void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDiscountPercentage1(BigDecimal discountPercentage1) {
		this.discountPercentage1 = discountPercentage1;
	}

	void setDiscountPercentage2(BigDecimal discountPercentage2) {
		this.discountPercentage2 = discountPercentage2;
	}

	void setDiscountPercentage3(BigDecimal discountPercentage3) {
		this.discountPercentage3 = discountPercentage3;
	}

	void setDiscountPercentage4(BigDecimal discountPercentage4) {
		this.discountPercentage4 = discountPercentage4;
	}

	void setDiscountPercentage5(BigDecimal discountPercentage5) {
		this.discountPercentage5 = discountPercentage5;
	}

	void setDiscountAmount1(BigDecimal discountAmount1) {
		this.discountAmount1 = discountAmount1;
	}

	void setDiscountAmount2(BigDecimal discountAmount2) {
		this.discountAmount2 = discountAmount2;
	}

	void setDiscountAmount3(BigDecimal discountAmount3) {
		this.discountAmount3 = discountAmount3;
	}

	void setDiscountAmount4(BigDecimal discountAmount4) {
		this.discountAmount4 = discountAmount4;
	}

	void setDiscountAmount5(BigDecimal discountAmount5) {
		this.discountAmount5 = discountAmount5;
	}

	void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	
}
