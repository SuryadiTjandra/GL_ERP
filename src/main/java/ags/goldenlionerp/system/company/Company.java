package ags.goldenlionerp.system.company;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0020")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="CNUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="CNDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="CNTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="CNUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="CNDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="CNTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="CNCID")),
})
public class Company extends DatabaseEntity {
	@Id
	@Column(name="CNCOID")
	private String companyId;
	@Column(name="CNDESB1")
	private String description = "";
	@Column(name="CNCRCB")
	private String currencyCodeBase = "";
	
	@Column(name="CNANUM")
	private String businessPartnerId = "";
	
	@Column(name="CNFDP")
	private String fiscalDatePattern = "";
	@Column(name="CNCFY")
	private int currentFiscalYear = 0;
	
	@Column(name="CNCAP")
	private int currentAccountingPeriod = 0;
	@Column(name="CNCPP")
	private int currentPayablePeriod = 0;		//hutang
	@Column(name="CNCRP")
	private int currentReceivablePeriod = 0;	//piutang
	@Column(name="CNCIP")
	private int currentInventoryPeriod = 0;

	public Company() {};

	public String getCompanyId() {
		return companyId;
	}
	public String getDescription() {
		return description;
	}
	public String getCurrencyCodeBase() {
		return currencyCodeBase;
	}
	public String getBusinessPartnerId() {
		return businessPartnerId;
	}
	public String getFiscalDatePattern() {
		return fiscalDatePattern;
	}
	public int getCurrentFiscalYear() {
		return currentFiscalYear;
	}
	public int getCurrentAccountingPeriod() {
		return currentAccountingPeriod;
	}
	public int getCurrentPayablePeriod() {
		return currentPayablePeriod;
	}
	public int getCurrentReceivablePeriod() {
		return currentReceivablePeriod;
	}
	public int getCurrentInventoryPeriod() {
		return currentInventoryPeriod;
	}

	void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setCurrencyCodeBase(String currencyCodeBase) {
		this.currencyCodeBase = currencyCodeBase;
	}

	void setBusinessPartnerId(String businessPartnerId) {
		this.businessPartnerId = businessPartnerId;
	}

	void setFiscalDatePattern(String fiscalDatePattern) {
		this.fiscalDatePattern = fiscalDatePattern;
	}

	void setCurrentFiscalYear(int currentFiscalYear) {
		this.currentFiscalYear = currentFiscalYear;
	}

	void setCurrentAccountingPeriod(int currentAccountingPeriod) {
		this.currentAccountingPeriod = currentAccountingPeriod;
	}

	void setCurrentPayablePeriod(int currentPayablePeriod) {
		this.currentPayablePeriod = currentPayablePeriod;
	}

	void setCurrentReceivablePeriod(int currentReceivablePeriod) {
		this.currentReceivablePeriod = currentReceivablePeriod;
	}

	void setCurrentInventoryPeriod(int currentInventoryPeriod) {
		this.currentInventoryPeriod = currentInventoryPeriod;
	}


}
