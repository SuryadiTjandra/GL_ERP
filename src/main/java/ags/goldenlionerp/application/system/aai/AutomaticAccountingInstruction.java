package ags.goldenlionerp.application.system.aai;

import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.masterdata.accountmaster.AccountMaster;
import ags.goldenlionerp.masterdata.chartofaccount.ChartOfAccount;
@Entity
@Table(name="T0015")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="AIUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="AIDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="AITMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="AIUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="AIDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="AITMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="AICID"))
})
public class AutomaticAccountingInstruction extends DatabaseEntity<AutomaticAccountingInstructionPK> {

	@EmbeddedId
	@JsonUnwrapped
	private AutomaticAccountingInstructionPK pk;
	
	@Column(name="AIDESB1")
	private String description1 = "";
	
	@Column(name="AIDESB2")
	private String description2 = "";
	
	@Column(name="AIDESB3")
	private String description3 = "";
	
	@Column(name="AIDESB4")
	private String description4 = "";
	
	@Column(name="AIDESB5")
	private String description5 = "";
	
	@Column(name="AICOID")
	private String companyId = "";
	
	@Column(name="AIBUID")
	private String businessUnitId = "";
	
	@Column(name="AIOBJ")
	private String objectAccountCode = "";
	
	@Column(name="AISUB")
	private String subsidiaryCode = "";
	
	@JoinColumns({
		@JoinColumn(name="AICOID", referencedColumnName="AMCOID", insertable=false, updatable=false),
		@JoinColumn(name="AIBUID", referencedColumnName="AMBUID", insertable=false, updatable=false),
		@JoinColumn(name="AIOBJ", referencedColumnName="AMOBJ", insertable=false, updatable=false),
		@JoinColumn(name="AISUB", referencedColumnName="AMSUB", insertable=false, updatable=false)
	})
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private AccountMaster accountMaster;
	
	@JoinColumn(name="AIOBJ", insertable = false, updatable = false)
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private ChartOfAccount account;

	public AutomaticAccountingInstructionPK getPk() {
		return pk;
	}

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
	}

	public String getDescription3() {
		return description3;
	}

	public String getDescription4() {
		return description4;
	}

	public String getDescription5() {
		return description5;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public String getObjectAccountCode() {
		return objectAccountCode;
	}

	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}

	public Optional<AccountMaster> getAccountMaster() {
		return Optional.ofNullable(accountMaster);
	}

	void setPk(AutomaticAccountingInstructionPK pk) {
		this.pk = pk;
	}

	void setDescription1(String description1) {
		this.description1 = description1;
	}

	void setDescription2(String description2) {
		this.description2 = description2;
	}

	void setDescription3(String description3) {
		this.description3 = description3;
	}

	void setDescription4(String description4) {
		this.description4 = description4;
	}

	void setDescription5(String description5) {
		this.description5 = description5;
	}

	void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setObjectAccountCode(String objectAccountCode) {
		this.objectAccountCode = objectAccountCode;
	}

	void setSubsidiaryCode(String subsidiaryCode) {
		this.subsidiaryCode = subsidiaryCode;
	}

	void setAccountMaster(AccountMaster accountMaster) {
		this.accountMaster = accountMaster;
	}
	
	public Optional<ChartOfAccount> getAccount() {
		return Optional.ofNullable(account);
	}

	void setAccount(ChartOfAccount account) {
		this.account = account;
	}

	@Override
	public AutomaticAccountingInstructionPK getId() {
		return getPk();
	}
	
	
}
