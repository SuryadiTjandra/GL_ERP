package ags.goldenlionerp.application.addresses.address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ags.goldenlionerp.application.addresses.bankaccount.BankAccount;
import ags.goldenlionerp.application.addresses.contact.ContactPerson;
import ags.goldenlionerp.application.addresses.phone.PhoneNumber;
import ags.goldenlionerp.application.ap.setting.AccountPayableSetting;
import ags.goldenlionerp.application.ar.setting.AccountReceivableSetting;
import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.entities.DatabaseEntityUtil;
import ags.goldenlionerp.entities.TransactionSynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0101")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="ADUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="ADDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="ADTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="ADUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="ADDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="ADTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="ADCID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="ADDTLS")),
	@AttributeOverride(name="lastTransactionDate", column=@Column(name="ADDTLT"))
})
public class AddressBookMaster extends TransactionSynchronizedDatabaseEntityImpl<String> {

	@Id @Column(name="ADANUM")
	private String addressNumber;
	
	@Column(name="ADST", nullable=false)
	private String searchType;
	
	@Column(name="ADLAN", nullable=false)
	private String longAddressNumber;
	
	@Column(name="ADNM")
	private String name;
	
	@Column(name="ADMLNM")
	private String mailingName = "";
	
	@Column(name="ADPAN")
	private String parentAddressNumber = "";
	@JoinColumn(name="ADPAN", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY) @NotFound(action=NotFoundAction.IGNORE)
	private AddressBookMaster parent;
	
	@Column(name="ADTAXID")
	private String taxId = "";
	
	@Column(name="ADAP")
	private Boolean accountPayable = false;
	
	@Column(name="ADAR")
	private Boolean accountReceivable = false;
	
	@Column(name="ADEMPL")
	private Boolean employee = false;
	
	@Column(name="ADBUID", nullable=false)
	private String businessUnitId;
	@JoinColumn(name="ADBUID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private BusinessUnit businessUnit;
	
	@Column(name="ADRECID")
	private String recordId = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	
	@Column(name="ADTMLT")
	private String lastTransactionTime = "00:00:00";
	
	@Embedded
	private RelatedAddresses relatedAddresses = new RelatedAddresses();
	
	@Embedded
	private AddressCategories categories = new AddressCategories();
	
	@OneToMany(mappedBy="master", cascade= {CascadeType.ALL}, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<EffectiveAddress> addressHistory= new ArrayList<>();
	
	@OneToMany(mappedBy="master", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE})
	private List<ContactPerson> contacts = Collections.emptyList();
	@OneToMany(mappedBy="master", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE})
	private List<PhoneNumber> phoneNumbers = Collections.emptyList();
	@OneToMany(mappedBy="master", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE})
	private List<BankAccount> bankAccounts = Collections.emptyList();
	
	@OneToMany(mappedBy="master", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE})
	//should be one to one, but it doesn't do lazy fetching properly
	private List<AccountReceivableSetting> arSetting;
	
	@OneToMany(mappedBy="master", fetch=FetchType.LAZY, cascade= {CascadeType.REMOVE})
	//should be one to one, but it doesn't do lazy fetching properly
	private List<AccountPayableSetting> apSetting;
	 
	@PrePersist
	private void prePersist() {
		/*if (addressHistory.isEmpty()) {
			EffectiveAddressPK pk = new EffectiveAddressPK(addressNumber);
			EffectiveAddress ea = new EffectiveAddress();
			ea.setPk(pk);
			addressHistory.add(ea);
		}*/
		
		if (parentAddressNumber == null || parentAddressNumber.isEmpty()) {
			parentAddressNumber = addressNumber;
		}
	}
	
	public EffectiveAddress getCurrentAddress() {
		return getAddressHistory().stream()
				.filter(ea -> ea.getEffectiveDate().compareTo(LocalDate.now()) <= 0)
				.sorted(Comparator.comparing(EffectiveAddress::getEffectiveDate).reversed())
				.findFirst().orElse(null);
	}

	void setCurrentAddress(EffectiveAddress currentAddress) {
		if (currentAddress == null) throw new NullPointerException("currentAddress cannot be null!");
		addressHistory.add(currentAddress);
	}
	
	
	public String getAddressNumber() {
		return addressNumber;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getLongAddressNumber() {
		return longAddressNumber;
	}

	public String getName() {
		return name;
	}

	public String getMailingName() {
		return mailingName;
	}

	public String getParentAddressNumber() {
		return parentAddressNumber;
	}

	public Optional<AddressBookMaster> getParent() {
		return Optional.ofNullable(parent);
	}

	public String getTaxId() {
		return taxId;
	}

	public Boolean getAccountPayable() {
		return accountPayable;
	}

	public Boolean getAccountReceivable() {
		return accountReceivable;
	}

	public Boolean getEmployee() {
		return employee;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public List<EffectiveAddress> getAddressHistory(){
		return addressHistory;
	}
	


	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public String getRecordId() {
		return recordId;
	}

	public RelatedAddresses getRelatedAddresses() {
		return relatedAddresses;
	}

	public AddressCategories getCategories() {
		return categories;
	}

	@Override
	public String getId() {
		return getAddressNumber();
	}

	void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	void setLongAddressNumber(String longAddressNumber) {
		this.longAddressNumber = longAddressNumber;
	}

	void setName(String name) {
		this.name = name;
	}

	void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	void setParentAddressNumber(String parentAddressNumber) {
		this.parentAddressNumber = parentAddressNumber;
	}

	void setParent(AddressBookMaster parent) {
		this.parent = parent;
	}

	void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	void setAccountPayable(Boolean accountPayable) {
		this.accountPayable = accountPayable;
	}

	void setAccountReceivable(Boolean accountReceivable) {
		this.accountReceivable = accountReceivable;
	}

	void setEmployee(Boolean employee) {
		this.employee = employee;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Override
	public Optional<LocalDateTime> getLastTransactionDate() {
		return Optional.ofNullable(DatabaseEntityUtil.toLocalDateTime(lastTransactionDate, lastTransactionTime));
	}
	
	@Override
	protected void setLastTransactionDate(LocalDateTime lastTransactionDate) {
		this.lastTransactionDate = DatabaseEntityUtil.toDate(lastTransactionDate);
		this.lastTransactionTime = DatabaseEntityUtil.toTimeString(lastTransactionDate);
	}


	void setRelatedAddresses(RelatedAddresses relatedAddresses) {
		this.relatedAddresses = relatedAddresses;
	}

	void setCategories(AddressCategories categories) {
		this.categories = categories;
	}

	void setAddressHistory(List<EffectiveAddress> addressHistory) {
		this.addressHistory = addressHistory;
	}

	public List<ContactPerson> getContacts() {
		return contacts;
	}

	void setContacts(List<ContactPerson> contactPeople) {
		this.contacts = contactPeople;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public Optional<AccountReceivableSetting> getArSetting() {
		if (arSetting == null || arSetting.isEmpty())
			return Optional.empty();
		return Optional.of(arSetting.get(0));
		//return Optional.ofNullable(arSetting);
	}

	public Optional<AccountPayableSetting> getApSetting() {
		if (apSetting == null || apSetting.isEmpty())
			return Optional.empty();
		return Optional.of(apSetting.get(0));
		//return Optional.ofNullable(apSetting);
	}

	
}
