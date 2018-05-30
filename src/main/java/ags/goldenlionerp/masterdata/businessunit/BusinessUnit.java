package ags.goldenlionerp.masterdata.businessunit;

import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.masterdata.company.Company;

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
	private String buId;
	@Column(name="BNDESB1")
	private String description;
	@Column(name="BNBUTY")
	private String businessUnitType;
	
	@Column(name="BNANUM")
	private String idNumber;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="BNCOID")
	private Company company;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="BNBUID1")
	@NotFound(action = NotFoundAction.IGNORE)
	private BusinessUnit relatedBusinessUnit;
	
	//private String relatedBusinessUnit;
	
	@Column(name="BNFMOD")
	private String modelOrConsolidated;


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

	public Optional<BusinessUnit> getRelatedBusinessUnit() {
		return Optional.ofNullable(relatedBusinessUnit);
	}
	//@Column(name="BNBUID1")
	//public Optional<String> getRelatedBusinessUnit() {
	//	return Optional.empty();
	//}

	public String getModelOrConsolidated() {
		return modelOrConsolidated;
	}

}
