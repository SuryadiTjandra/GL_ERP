package ags.goldenlionerp.application.addresses.address;

import java.time.LocalDate;

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

import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0104")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="EAUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="EADTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="EATMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="EAUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="EADTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="EATMLU")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="EADTLS")),
	@AttributeOverride(name="computerId", column=@Column(name="EACID"))
})
public class EffectiveAddress extends SynchronizedDatabaseEntityImpl<EffectiveAddressPK> {

	@EmbeddedId @JsonUnwrapped
	private EffectiveAddressPK pk;
	
	@Column(name="EAEFDT")
	private LocalDate effectiveDate = LocalDate.now();
	
	@Column(name="EAADDR1")
	private String address1 = "";
	
	@Column(name="EAADDR2")
	private String address2 = "";
	
	@Column(name="EAADDR3")
	private String address3 = "";
	
	@Column(name="EAADDR4")
	private String address4 = "";
	
	@Column(name="EAEMAIL")
	private String email = "";
	
	@Column(name="EACNTRY")
	private String country = "";
	
	@Column(name="EASTATE")
	private String state = "";
	
	@Column(name="EACNTY")
	private String county = "";
	
	@Column(name="EACITY")
	private String city = "";
	
	@Column(name="EAPSCD")
	private String postalCode = "";
	
	@Column(name="EANOU")
	private String neighborhoodOrganizationUnit = ""; //RW
	
	@Column(name="EANAU")
	private String neigborhoodAdministrationUnit = ""; //RT
	
	@Column(name="EAPOLDIS")
	private String politicalDistrict = ""; //kelurahan
	
	@Column(name="EASUBDIS")
	private String subDistrict = ""; //kecamatan
	
	@JoinColumn(name="EAANUM", insertable=false, updatable=false) 
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private AddressBookMaster master;
	
	
	public EffectiveAddressPK getPk() {
		return pk;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}


	public String getAddress3() {
		return address3;
	}

	public String getAddress4() {
		return address4;
	}


	public String getEmail() {
		return email;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCounty() {
		return county;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getNeighborhoodOrganizationUnit() {
		return neighborhoodOrganizationUnit;
	}


	public String getNeigborhoodAdministrationUnit() {
		return neigborhoodAdministrationUnit;
	}


	public String getPoliticalDistrict() {
		return politicalDistrict;
	}


	public String getSubDistrict() {
		return subDistrict;
	}

	@Override
	public EffectiveAddressPK getId() {
		return getPk();
	}

	void setPk(EffectiveAddressPK pk) {
		this.pk = pk;
	}

	void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	void setAddress1(String address1) {
		this.address1 = address1;
	}

	void setAddress2(String address2) {
		this.address2 = address2;
	}

	void setAddress3(String address3) {
		this.address3 = address3;
	}

	void setAddress4(String address4) {
		this.address4 = address4;
	}

	void setEmail(String email) {
		this.email = email;
	}

	void setCountry(String country) {
		this.country = country;
	}

	void setState(String state) {
		this.state = state;
	}

	void setCounty(String county) {
		this.county = county;
	}

	void setCity(String city) {
		this.city = city;
	}

	void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	void setNeighborhoodOrganizationUnit(String neighborhoodOrganizationUnit) {
		this.neighborhoodOrganizationUnit = neighborhoodOrganizationUnit;
	}

	void setNeigborhoodAdministrationUnit(String neigborhoodAdministrationUnit) {
		this.neigborhoodAdministrationUnit = neigborhoodAdministrationUnit;
	}

	void setPoliticalDistrict(String politicalDistrict) {
		this.politicalDistrict = politicalDistrict;
	}

	void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	
}
