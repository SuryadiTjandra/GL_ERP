package ags.goldenlionerp.masterdata.accountmaster;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.application.system.company.Company;
import ags.goldenlionerp.entities.DatabaseEntityUtil;
import ags.goldenlionerp.entities.TransactionDatabaseEntityImpl;
import ags.goldenlionerp.masterdata.chartofaccount.ChartOfAccount;
@Entity
@Table(name="T0901")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="AMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="AMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="AMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="AMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="AMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="AMTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="AMCID")),
	@AttributeOverride(name="lastTransactionDate", column=@Column(name="AMDTLT"))
})
public class AccountMaster extends TransactionDatabaseEntityImpl<AccountMasterPK> {

	@EmbeddedId
	@JsonUnwrapped
	private AccountMasterPK pk;
	
	@Column(name="AMACID", updatable=false)
	private String accountId;
	
	
	@JoinColumn(name="AMCOID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private Company company;
	
	
	@JoinColumn(name="AMBUID", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private BusinessUnit businessUnit;
	
	
	@JoinColumn(name="AMOBJ", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private ChartOfAccount objectAccount;
	
	
	
	@Column(name="AMDESB1")
	private String description = "";
	
	@Column(name="AMLOD")
	private int levelOfDetail = 0;
	
	@Column(name="AMPEC")
	private Boolean postingEditCode = false;
	
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
		String acc = pk.getBusinessUnitId() + "." + pk.getObjectAccountCode();
		if (!pk.getSubsidiaryCode().isEmpty())
			acc = acc + "." + pk.getSubsidiaryCode();
		
		accountId = acc;
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

	public Company getCompany() {
		return company;
	}
	
	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public ChartOfAccount getObjectAccount() {
		return objectAccount;
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

	void setCompany(Company company) {
		this.company = company;
	}

	void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	void setObjectAccount(ChartOfAccount objectAccount) {
		this.objectAccount = objectAccount;
	}

	public AccountMasterPK getPk() {
		return pk;
	}

	void setPk(AccountMasterPK pk) {
		this.pk = pk;
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

	@Override
	public AccountMasterPK getId() {
		return getPk();
	}
	
	
}
