package ags.goldenlionerp.application.addresses.contact;

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

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0102")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="CIUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="CIDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="CITMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="CIUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="CIDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="CITMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="CICID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="CIDTLS")),
})
public class ContactPerson extends SynchronizedDatabaseEntityImpl<ContactPersonPK> {

	static final String DEFAULT_TYPE = "C";
	
	@EmbeddedId @JsonUnwrapped
	private ContactPersonPK pk;
	
	@Column(name="CINM")
	private String name = "";
	
	@Column(name="CIMLNM")
	private String mailingName = "";
	
	@Column(name="CIDESB1")
	private String description = "";
	
	@Column(name="CIFSNM")
	private String firstName = "";
	
	@Column(name="CIMDNM")
	private String middleName = "";
	
	@Column(name="CILSNM")
	private String lastName = "";
	
	@Column(name="CINKNM")
	private String nickname = "";
	
	@Column(name="CISLTN")
	private String salutation = "";
	
	@Column(name="CIGDR")
	private String gender = "";
	
	@Column(name="CITYCD")
	private String type= "";
	
	@Column(name="CIRLG")
	private String religion = "";
	
	@Column(name="CIDOB")
	private LocalDate dateOfBirth;
	
	@Column(name="CIPOB")
	private String placeOfBirth = "";
	
	@Column(name="CISSN")
	private String socialSecurityNumber = "";
	
	@Column(name="CISSDT")
	private LocalDate socialSecurityDate; //activation date of the social security number
	
	@Column(name="CIMOREF")
	private String mediaObjectReference = "";
	
	@JoinColumn(name="CIANUM", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private AddressBookMaster master;
	
	public ContactPersonPK getPk() {
		return pk;
	}

	public String getName() {
		return name;
	}

	public String getMailingName() {
		return mailingName;
	}

	public String getDescription() {
		return description;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public String getSalutation() {
		return salutation;
	}

	public String getGender() {
		return gender;
	}

	public String getType() {
		return type;
	}

	public String getReligion() {
		return religion;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public LocalDate getSocialSecurityDate() {
		return socialSecurityDate;
	}

	public String getMediaObjectReference() {
		return mediaObjectReference;
	}

	@Override
	public ContactPersonPK getId() {
		return getPk();
	}

	void setPk(ContactPersonPK pk) {
		this.pk = pk;
	}

	void setName(String name) {
		this.name = name;
	}

	void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

	void setNickname(String nickname) {
		this.nickname = nickname;
	}

	void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	void setGender(String gender) {
		this.gender = gender;
	}

	void setType(String type) {
		this.type = type;
	}

	void setReligion(String religion) {
		this.religion = religion;
	}

	void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	void setSocialSecurityDate(LocalDate socialSecurityDate) {
		this.socialSecurityDate = socialSecurityDate;
	}

	void setMediaObjectReference(String mediaObjectReference) {
		this.mediaObjectReference = mediaObjectReference;
	}
	
	

}
