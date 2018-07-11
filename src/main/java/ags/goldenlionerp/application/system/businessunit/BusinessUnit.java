package ags.goldenlionerp.application.system.businessunit;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.application.system.branchplantconstant.BranchPlantConstant;
import ags.goldenlionerp.application.system.company.Company;
import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.entities.previews.BusinessUnitPreview;
import ags.goldenlionerp.entities.previews.CompanyPreview;
import ags.goldenlionerp.entities.previews.Previews;
import ags.goldenlionerp.masterdata.itembranchinfo.ItemBranchInfo;
import ags.goldenlionerp.masterdata.location.LocationMaster;

@Entity
@Table(name="T0021")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="BNUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="BNDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="BNTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="BNUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="BNDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="BNTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="BNCID")),
})
public class BusinessUnit extends DatabaseEntity{
	@Id
	@Column(name="BNBUID")
	private String businessUnitId;
	@Column(name="BNDESB1")
	private String description = "";
	@Column(name="BNBUTY")
	private String businessUnitType = "";
	
	@Column(name="BNANUM")
	private String idNumber = "";
	
	@Column(name="BNCOID")
	private String companyId = "";
	
	@ManyToOne(optional=false)
	@JoinColumn(name="BNCOID", updatable=false, insertable=false)
	private Company company;
	
	@Column(name="BNBUID1")
	private String relatedBusinessUnitId = "";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BNBUID1", updatable=false, insertable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	@RestResource(exported=true, path="related", rel="related")
	private BusinessUnit relatedBusinessUnit;
	
	@Column(name="BNFMOD")
	private String modelOrConsolidated = "";

	@OneToOne(fetch=FetchType.LAZY, mappedBy="branch")
	private BranchPlantConstant configuration;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="businessUnit")
	private Collection<LocationMaster> locations;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="branch")
	private Collection<ItemBranchInfo> itemBranchInfos;

	public String getBusinessUnitId() {
		return businessUnitId;
	}
	
	public String getDescription() {
		return description;
	}

	public String getBusinessUnitType() {
		return businessUnitType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public Company getCompany() {
		return company;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public String getRelatedBusinessUnitId() {
		return relatedBusinessUnitId;
	}
	
	public Optional<BusinessUnit> getRelatedBusinessUnit() {
		return Optional.ofNullable(relatedBusinessUnit);
	}

	public String getModelOrConsolidated() {
		return modelOrConsolidated;
	}
	
	public Optional<BranchPlantConstant> getConfiguration() {
		return Optional.ofNullable(configuration);
	}
	
	public Collection<LocationMaster> getLocations(){
		return this.locations;
	}
	public Collection<ItemBranchInfo> getItemBranchInfos() {
		return itemBranchInfos;
	}
	
	@JsonProperty("relatedPreview")
	public Optional<BusinessUnitPreview> getRelatedBusinessUnitPreview() {
		return getRelatedBusinessUnit().map(Previews::getPreview);
	}
	public CompanyPreview getCompanyPreview() {
		return Previews.getPreview(getCompany());
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setBusinessUnitType(String businessUnitType) {
		this.businessUnitType = businessUnitType;
	}

	void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	void setCompany(Company company) {
		this.company = company;
	}

	void setRelatedBusinessUnitId(String relatedBusinessUnitId) {
		this.relatedBusinessUnitId = relatedBusinessUnitId;
	}
	
	void setRelatedBusinessUnit(BusinessUnit relatedBusinessUnit) {
		this.relatedBusinessUnit = relatedBusinessUnit;
	}

	void setModelOrConsolidated(String modelOrConsolidated) {
		this.modelOrConsolidated = modelOrConsolidated;
	}

	void setConfiguration(BranchPlantConstant configuration) {
		this.configuration = configuration;
	}
	void setLocations(Collection<LocationMaster> locations) {
		this.locations = locations;
	}
}
