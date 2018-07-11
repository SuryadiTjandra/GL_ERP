package ags.goldenlionerp.application.system.generalconstant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.util.BeanFinder;

@Entity
@Table(name="T0010")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="GCUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="GCDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="GCTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="GCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="GCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="GCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="GCCID"))
})
public class GeneralConstant extends DatabaseEntity<String> {

	public final static String DEFAULT_CODE = "00";
	
	//singleton
	private static GeneralConstant instance = null;	
	public static GeneralConstant getInstance() {
		if (instance != null) return instance;
		
		GeneralConstantRepository repo = BeanFinder.findBean(GeneralConstantRepository.class);
		instance = repo.find();
		return instance;
	}	
	private GeneralConstant() {}
	
	@Id
	@Column(name="GCPC")
	private String code;
	
	@Column(name="GCCCM")
	private String currencyConversionMethod;
	
	@Column(name="GCAODM")
	private int ageOfDataMaster;
	
	@Column(name="GCITGL")
	private Boolean interfaceToGL;
	
	@Column(name="GCITAR")
	private Boolean interfaceToAccountReceivable;
	
	@Column(name="GCITAP")
	private Boolean interfaceToAccountPayable;
	
	@Column(name="GCITFA")
	private Boolean interfaceToFixedAssets;
	
	@Column(name="GCITIN")
	private Boolean interfaceToInventory;
	
	@Column(name="GCITMF")
	private Boolean interfaceToManufacturer;
	
	@Column(name="GCITHC")
	private Boolean interfaceToPayroll;
	
	@Column(name="GCITCS")
	private Boolean interfaceToCS; //not used?
	
	@Column(name="GCPUCM")
	private String purchaseCostMethod;
	
	@Column(name="GCSICM")
	private String salesInventoryCostMethod;
	
	@Column(name="GCTBCC")
	private Boolean timeBasedCostCalculation;
	
	@Column(name="GCCOID")
	private String companyId;
	
	@Column(name="GCBANKID")
	private String bankCode;
	
	@Column(name="GCVERID")
	private String versionId;
	
	@Column(name="GCURL")
	private String url; //also not used?
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="accountReceivables", column=@Column(name="GCPCN01", precision=9, scale=5)),
		@AttributeOverride(name="internalReceivables", column=@Column(name="GCPCN02", precision=9, scale=5)),
		@AttributeOverride(name="accountPayables", column=@Column(name="GCPCN03", precision=9, scale=5)),
		@AttributeOverride(name="internalPayables", column=@Column(name="GCPCN04", precision=9, scale=5)),
		@AttributeOverride(name="deposits", column=@Column(name="GCPCN05", precision=9, scale=5))
	})
	private CalculationRounding baseCurrencyRoundings;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="accountReceivables", column=@Column(name="GCPCN06", precision=9, scale=5)),
		@AttributeOverride(name="internalReceivables", column=@Column(name="GCPCN07", precision=9, scale=5)),
		@AttributeOverride(name="accountPayables", column=@Column(name="GCPCN08", precision=9, scale=5)),
		@AttributeOverride(name="internalPayables", column=@Column(name="GCPCN09", precision=9, scale=5)),
		@AttributeOverride(name="deposits", column=@Column(name="GCPCN10", precision=9, scale=5))
	})
	private CalculationRounding foreignCurrencyRoundings;

	public String getCode() {
		return code;
	}

	public String getCurrencyConversionMethod() {
		return currencyConversionMethod;
	}

	public int getAgeOfDataMaster() {
		return ageOfDataMaster;
	}

	public boolean isInterfaceToGL() {
		return interfaceToGL;
	}

	public boolean isInterfaceToAccountReceivable() {
		return interfaceToAccountReceivable;
	}

	public boolean isInterfaceToAccountPayable() {
		return interfaceToAccountPayable;
	}

	public boolean isInterfaceToFixedAssets() {
		return interfaceToFixedAssets;
	}

	public boolean isInterfaceToInventory() {
		return interfaceToInventory;
	}

	public boolean isInterfaceToManufacturer() {
		return interfaceToManufacturer;
	}

	public boolean isInterfaceToPayroll() {
		return interfaceToPayroll;
	}

	public boolean isInterfaceToCS() {
		return interfaceToCS;
	}

	public String getPurchaseCostMethod() {
		return purchaseCostMethod;
	}

	public String getSalesInventoryCostMethod() {
		return salesInventoryCostMethod;
	}

	public boolean isTimeBasedCostCalculation() {
		return timeBasedCostCalculation;
	}

	public CalculationRounding getBaseCurrencyRoundings() {
		return baseCurrencyRoundings;
	}

	public CalculationRounding getForeignCurrencyRoundings() {
		return foreignCurrencyRoundings;
	}

	public void setCode(String code) {
		this.code = code;
	}

	void setCurrencyConversionMethod(String currencyConversionMethod) {
		this.currencyConversionMethod = currencyConversionMethod;
	}

	void setAgeOfDataMaster(int ageOfDataMaster) {
		this.ageOfDataMaster = ageOfDataMaster;
	}

	void setInterfaceToGL(boolean interfaceToGL) {
		this.interfaceToGL = interfaceToGL;
	}

	void setInterfaceToAccountReceivable(boolean interfaceToAccountReceivable) {
		this.interfaceToAccountReceivable = interfaceToAccountReceivable;
	}

	void setInterfaceToAccountPayable(boolean interfaceToAccountPayable) {
		this.interfaceToAccountPayable = interfaceToAccountPayable;
	}

	void setInterfaceToFixedAssets(boolean interfaceToFixedAssets) {
		this.interfaceToFixedAssets = interfaceToFixedAssets;
	}

	void setInterfaceToInventory(boolean interfaceToInventory) {
		this.interfaceToInventory = interfaceToInventory;
	}

	void setInterfaceToManufacturer(boolean interfaceToManufacturer) {
		this.interfaceToManufacturer = interfaceToManufacturer;
	}

	void setInterfaceToPayroll(boolean interfaceToPayroll) {
		this.interfaceToPayroll = interfaceToPayroll;
	}

	void setInterfaceToCS(boolean interfaceToCS) {
		this.interfaceToCS = interfaceToCS;
	}

	void setPurchaseCostMethod(String purchaseCostMethod) {
		this.purchaseCostMethod = purchaseCostMethod;
	}

	void setSalesInventoryCostMethod(String salesInventoryCostMethod) {
		this.salesInventoryCostMethod = salesInventoryCostMethod;
	}

	void setTimeBasedCostCalculation(boolean timeBasedCostCalculation) {
		this.timeBasedCostCalculation = timeBasedCostCalculation;
	}

	void setBaseCurrencyRoundings(CalculationRounding baseCurrencyRoundings) {
		this.baseCurrencyRoundings = baseCurrencyRoundings;
	}

	void setForeignCurrencyRoundings(CalculationRounding foreignCurrencyRoundings) {
		this.foreignCurrencyRoundings = foreignCurrencyRoundings;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public String getVersionId() {
		return versionId;
	}
	public String getUrl() {
		return url;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String getId() {
		return getCode();
	}
	
	
}
