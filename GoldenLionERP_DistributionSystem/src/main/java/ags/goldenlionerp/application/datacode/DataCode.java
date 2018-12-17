package ags.goldenlionerp.application.datacode;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Table(name="T0009")
public class DataCode {

	@EmbeddedId @JsonUnwrapped
	private DataCodePK pk;
	
	@Column(name="DTDESC1")
	private String description;
	
	@Column(name="DTDESC2")
	private String description2;
	
	@Column(name="DTSPHD")
	private String handlingCode;
	
	@Column(name="DTHC")
	private Boolean hardcoded;
	
	@Column(name="DTUID", updatable=false)
	protected String inputUserId;

	@Column(name="DTDTIN", updatable=false)
	protected LocalDate inputDate;
	
	@Column(name="DTUIDM")
	protected String lastUpdateUserId;

	@Column(name="DTDTLU")
	protected LocalDate lastUpdateDate;

	public DataCodePK getPk() {
		return pk;
	}

	public String getDescription() {
		return description;
	}

	public String getDescription2() {
		return description2;
	}

	public String getHandlingCode() {
		return handlingCode;
	}

	public Boolean getHardcoded() {
		return hardcoded;
	}

	public String getInputUserId() {
		return inputUserId;
	}

	public LocalDate getInputDate() {
		return inputDate;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public LocalDate getLastUpdateDate() {
		return lastUpdateDate;
	}

	void setPk(DataCodePK pk) {
		this.pk = pk;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDescription2(String description2) {
		this.description2 = description2;
	}

	void setHandlingCode(String handlingCode) {
		this.handlingCode = handlingCode;
	}

	void setHardcoded(Boolean hardcoded) {
		this.hardcoded = hardcoded;
	}

	void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}

	void setInputDate(LocalDate inputDate) {
		this.inputDate = inputDate;
	}

	void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	

}
