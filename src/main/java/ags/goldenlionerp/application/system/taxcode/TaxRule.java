package ags.goldenlionerp.application.system.taxcode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0025")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="TSUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="TSDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="TSTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="TSUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="TSDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="TSTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="TSCID"))
})
public class TaxRule extends DatabaseEntity<TaxRulePK>{

	@EmbeddedId
	@JsonUnwrapped
	private TaxRulePK pk;
	
	@Column(name="TSEXPDT", nullable=false)
	private Timestamp expiredDate;
	
	@Column(name="TSDESB1")
	private String description = "";
	
	@Column(name="TSTAXAU1")
	private String taxAuthority1 = "";
	@Column(name="TSTAXCL1")
	private String taxClass1 = "";
	@Column(name="TSPCN01", precision=9, scale=5)
	private BigDecimal taxPercentage1 = new BigDecimal(0);
	
	@Column(name="TSTAXAU2")
	private String taxAuthority2 = "";
	@Column(name="TSTAXCL2")
	private String taxClass2 = "";
	@Column(name="TSPCN02", precision=9, scale=5)
	private BigDecimal taxPercentage2 = new BigDecimal(0);
	
	@Column(name="TSTAXAU3")
	private String taxAuthority3 = "";
	@Column(name="TSTAXCL3")
	private String taxClass3 = "";
	@Column(name="TSPCN03", precision=9, scale=5)
	private BigDecimal taxPercentage3 = new BigDecimal(0);
	
	@Column(name="TSTAXAU4")
	private String taxAuthority4 = "";
	@Column(name="TSTAXCL4")
	private String taxClass4 = "";
	@Column(name="TSPCN04", precision=9, scale=5)
	private BigDecimal taxPercentage4 = new BigDecimal(0);
	
	@Column(name="TSTAXAU5")
	private String taxAuthority5 = "";
	@Column(name="TSTAXCL5")
	private String taxClass5 = "";
	@Column(name="TSPCN05", precision=9, scale=5)
	private BigDecimal taxPercentage5 = new BigDecimal(0);
	
	@Column(name="TSTAXAL")
	private Boolean taxAllowance = false;
	
	@Column(name="TSDOCTY")
	private String documentType = "";

	public TaxRulePK getPk() {
		return pk;
	}

	public LocalDate getExpiredDate() {
		return expiredDate.toLocalDateTime().toLocalDate();
	}

	public String getDescription() {
		return description;
	}

	public String getTaxAuthority1() {
		return taxAuthority1;
	}

	public String getTaxClass1() {
		return taxClass1;
	}

	public BigDecimal getTaxPercentage1() {
		return taxPercentage1;
	}

	public String getTaxAuthority2() {
		return taxAuthority2;
	}

	public String getTaxClass2() {
		return taxClass2;
	}

	public BigDecimal getTaxPercentage2() {
		return taxPercentage2;
	}

	public String getTaxAuthority3() {
		return taxAuthority3;
	}

	public String getTaxClass3() {
		return taxClass3;
	}

	public BigDecimal getTaxPercentage3() {
		return taxPercentage3;
	}

	public String getTaxAuthority4() {
		return taxAuthority4;
	}

	public String getTaxClass4() {
		return taxClass4;
	}

	public BigDecimal getTaxPercentage4() {
		return taxPercentage4;
	}

	public String getTaxAuthority5() {
		return taxAuthority5;
	}

	public String getTaxClass5() {
		return taxClass5;
	}

	public BigDecimal getTaxPercentage5() {
		return taxPercentage5;
	}

	public Boolean getTaxAllowance() {
		return taxAllowance;
	}

	public String getDocumentType() {
		return documentType;
	}

	void setPk(TaxRulePK pk) {
		this.pk = pk;
	}

	void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = Timestamp.valueOf(expiredDate.atStartOfDay());
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setTaxAuthority1(String taxAuthority1) {
		this.taxAuthority1 = taxAuthority1;
	}

	void setTaxClass1(String taxClass1) {
		this.taxClass1 = taxClass1;
	}

	void setTaxPercentage1(BigDecimal taxPercentage1) {
		this.taxPercentage1 = taxPercentage1;
	}

	void setTaxAuthority2(String taxAuthority2) {
		this.taxAuthority2 = taxAuthority2;
	}

	void setTaxClass2(String taxClass2) {
		this.taxClass2 = taxClass2;
	}

	void setTaxPercentage2(BigDecimal taxPercentage2) {
		this.taxPercentage2 = taxPercentage2;
	}

	void setTaxAuthority3(String taxAuthority3) {
		this.taxAuthority3 = taxAuthority3;
	}

	void setTaxClass3(String taxClass3) {
		this.taxClass3 = taxClass3;
	}

	void setTaxPercentage3(BigDecimal taxPercentage3) {
		this.taxPercentage3 = taxPercentage3;
	}

	void setTaxAuthority4(String taxAuthority4) {
		this.taxAuthority4 = taxAuthority4;
	}

	void setTaxClass4(String taxClass4) {
		this.taxClass4 = taxClass4;
	}

	void setTaxPercentage4(BigDecimal taxPercentage4) {
		this.taxPercentage4 = taxPercentage4;
	}

	void setTaxAuthority5(String taxAuthority5) {
		this.taxAuthority5 = taxAuthority5;
	}

	void setTaxClass5(String taxClass5) {
		this.taxClass5 = taxClass5;
	}

	void setTaxPercentage5(BigDecimal taxPercentage5) {
		this.taxPercentage5 = taxPercentage5;
	}

	void setTaxAllowance(Boolean taxAllowance) {
		this.taxAllowance = taxAllowance;
	}

	void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	public TaxRulePK getId() {
		return getPk();
	}
	
	
}
