package ags.goldenlionerp.masterdata.accountmaster;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntityUtil;
import ags.goldenlionerp.entities.TransactionDatabaseEntityImpl;
import ags.goldenlionerp.masterdata.businessunit.BusinessUnit;
import ags.goldenlionerp.masterdata.chartofaccount.ChartOfAccount;
import ags.goldenlionerp.masterdata.company.Company;
@Entity
@Table(name="T0901")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="AMUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="AMDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="AMTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="AMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="AMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="AMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="AMCID")),
	@AttributeOverride(name="lastTransactionDate", column=@Column(name="AMDTLT"))
})
public class AccountMaster extends TransactionDatabaseEntityImpl {

	@Id
	@Column(name="AMACID")
	private String accountId;
	
	@Column(name="AMCOID", updatable=false)
	private String companyId;
	@JoinColumn(name="AMCOID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private Company company;
	
	@Column(name="AMBUID", updatable=false)
	private String businessUnitId;
	@JoinColumn(name="AMBUID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private BusinessUnit businessUnit;
	
	@Column(name="AMOBJ", updatable=false)
	private String objectAccountCode;
	@JoinColumn(name="AMOBJ", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private ChartOfAccount objectAccount;
	
	@Column(name="AMSUB", updatable=false)
	private String subsidiaryCode = "";
	
	@Column(name="AMDESB1")
	private String description = "";
	
	@Column(name="AMLOD")
	private int levelOfDetail = 0;
	
	@Column(name="AMPEC")
	private boolean postingEditCode;
	
	@Column(name="AMBLC")
	private String balanceType = "";
	
	@Column(name="AMMAC")
	private String modelAccount = "";
	
	@Column(name="AMCRCT")
	private String currencyCodeTransaction = "";
	
	@Column(name="AMBPC")
	private String budgetPatternCode = "";
	
	@Column(name="AMGLC01")
	private String category01 = "";
	
	@Column(name="AMGLC02")
	private String category02 = "";
	
	@Column(name="AMGLC03")
	private String category03 = "";
	
	@Column(name="AMGLC04")
	private String category04 = "";
	
	@Column(name="AMGLC05")
	private String category05 = "";
	
	@Column(name="AMOAN")
	private int objectAccountNumber;
	
	@Column(name="AMTMLT")
	private String lastTransactionTime = "";

	@PrePersist
	private void prePersist() {
		accountId = businessUnitId + "." + objectAccountCode + "." + subsidiaryCode;
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



	public String getAccountId() {
		return accountId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Company getCompany() {
		return company;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public String getObjectAccountCode() {
		return objectAccountCode;
	}

	public ChartOfAccount getObjectAccount() {
		return objectAccount;
	}

	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}

	public String getDescription() {
		return description;
	}

	public int getLevelOfDetail() {
		return levelOfDetail;
	}

	public boolean isPostingEditCode() {
		return postingEditCode;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public String getModelAccount() {
		return modelAccount;
	}

	public String getCurrencyCodeTransaction() {
		return currencyCodeTransaction;
	}

	public String getBudgetPatternCode() {
		return budgetPatternCode;
	}

	public String getCategory01() {
		return category01;
	}

	public String getCategory02() {
		return category02;
	}

	public String getCategory03() {
		return category03;
	}

	public String getCategory04() {
		return category04;
	}

	public String getCategory05() {
		return category05;
	}

	public int getObjectAccountNumber() {
		return objectAccountNumber;
	}

	public String getLastTransactionTime() {
		return lastTransactionTime;
	}

	void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	void setCompany(Company company) {
		this.company = company;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	void setObjectAccountCode(String objectAccountCode) {
		this.objectAccountCode = objectAccountCode;
	}

	void setObjectAccount(ChartOfAccount objectAccount) {
		this.objectAccount = objectAccount;
	}

	void setSubsidiaryCode(String subsidiaryCode) {
		this.subsidiaryCode = subsidiaryCode;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setLevelOfDetail(int levelOfDetail) {
		this.levelOfDetail = levelOfDetail;
	}

	void setPostingEditCode(boolean postingEditCode) {
		this.postingEditCode = postingEditCode;
	}

	void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	void setModelAccount(String modelAccount) {
		this.modelAccount = modelAccount;
	}

	void setCurrencyCodeTransaction(String currencyCodeTransaction) {
		this.currencyCodeTransaction = currencyCodeTransaction;
	}

	void setBudgetPatternCode(String budgetPatternCode) {
		this.budgetPatternCode = budgetPatternCode;
	}

	void setCategory01(String category01) {
		this.category01 = category01;
	}

	void setCategory02(String category02) {
		this.category02 = category02;
	}

	void setCategory03(String category03) {
		this.category03 = category03;
	}

	void setCategory04(String category04) {
		this.category04 = category04;
	}

	void setCategory05(String category05) {
		this.category05 = category05;
	}

	void setObjectAccountNumber(int objectAccountNumber) {
		this.objectAccountNumber = objectAccountNumber;
	}

	void setLastTransactionTime(String lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}
	
	
}
