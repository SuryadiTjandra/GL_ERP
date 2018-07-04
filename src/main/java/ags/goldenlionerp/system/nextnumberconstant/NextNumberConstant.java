package ags.goldenlionerp.system.nextnumberconstant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0010N")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="NCUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="NCDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="NCTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="NCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="NCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="NCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="NCCID"))
})
public class NextNumberConstant extends DatabaseEntity {

	@Id
	@Column(name="NCDOCTY")
	private String documentType;
	
	@Column(name="NCRSAT")
	private int resetNumber = 0;
	
	@Column(name="NCRSMT")
	private String resetMethod = "";
	
	@Column(name="NCINMO")
	private Boolean includeMonthInNextNumber = false;
	
	@Column(name="NCINYR")
	private Boolean includeYearInNextNumber = false;

	public String getDocumentType() {
		return documentType;
	}

	public int getResetNumber() {
		return resetNumber;
	}

	public String getResetMethod() {
		return resetMethod;
	}

	public Boolean getIncludeMonthInNextNumber() {
		return includeMonthInNextNumber;
	}

	public Boolean getIncludeYearInNextNumber() {
		return includeYearInNextNumber;
	}

	void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	void setResetNumber(int resetNumber) {
		this.resetNumber = resetNumber;
	}

	void setResetMethod(String resetMethod) {
		this.resetMethod = resetMethod;
	}

	void setIncludeMonthInNextNumber(Boolean includeMonthInNextNumber) {
		this.includeMonthInNextNumber = includeMonthInNextNumber;
	}

	void setIncludeYearInNextNumber(Boolean includeYearInNextNumber) {
		this.includeYearInNextNumber = includeYearInNextNumber;
	}
	
	
}
