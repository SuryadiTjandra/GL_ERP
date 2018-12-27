package ags.goldenlionerp.application.addresses.phone;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0103")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="CMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="CMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="CMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="CMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="CMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="CMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="CMCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="CMDTLS")),
})
public class PhoneNumber extends SynchronizedDatabaseEntityImpl<PhoneNumberPK> {

	@EmbeddedId @JsonUnwrapped
	private PhoneNumberPK pk;
	
	@Column(name="CMPHCC")
	private String countryCode = "";
	
	@Column(name="CMPHAC")
	private String areaCode = "";
	
	@Column(name="CMPHNO")
	private String phoneNumber = "";
	
	@Column(name="CMPHTY")
	private String phoneType = "";
	
	@Column(name="CMDESB1")
	private String description = "";
	
	@JoinColumn(name="CMANUM", insertable = false, updatable = false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private AddressBookMaster master;
	
	@Override
	public PhoneNumberPK getId() {
		return getPk();
	}

	public PhoneNumberPK getPk() {
		return pk;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPhoneType() {
		return phoneType;
	}

	void setPk(PhoneNumberPK pk) {
		this.pk = pk;
	}

	void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	public AddressBookMaster getMaster() {
		return master;
	}

	void setMaster(AddressBookMaster master) {
		this.master = master;
	}

	
}
