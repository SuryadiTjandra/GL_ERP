package ags.goldenlionerp.application.setups.nextnumberconstant;

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
	@AttributeOverride(name="inputUserId", column=@Column(name="NCUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="NCDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="NCTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="NCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="NCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="NCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="NCCID"))
})
public class NextNumberConstant extends DatabaseEntity<String> {

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

	public boolean getIncludeMonthInNextNumber() {
		return includeMonthInNextNumber;
	}

	public boolean getIncludeYearInNextNumber() {
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

	void setIncludeMonthInNextNumber(boolean includeMonthInNextNumber) {
		this.includeMonthInNextNumber = includeMonthInNextNumber;
	}

	void setIncludeYearInNextNumber(boolean includeYearInNextNumber) {
		this.includeYearInNextNumber = includeYearInNextNumber;
	}

	@Override
	public String getId() {
		return getDocumentType();
	}

	public static NextNumberConstant defaultSetting() {
		return DefaultNextNumberConstant.getInstance();
	}
	
	
}
