package ags.goldenlionerp.application.accounting.chartofaccount;

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

import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0900")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="OAUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="OADTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="OATMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="OAUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="OADTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="OATMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="OACID")),
})
public class ChartOfAccount extends DatabaseEntity<String>{

	@Id
	@Column(name="OAOBJ")
	private String objectAccountCode;
	
	@Column(name="OACOID")
	private String companyId = "00000";
	
	@Column(name="OADESB1")
	private String description = "";
	
	@Column(name="OADESC1")
	private String descriptionLong = "";
	
	@Column(name="OALOD")
	private int levelOfDetail = 0;
	
	@Column(name="OAPEC")
	private Boolean postingEditCode;
	
	@Column(name="OABLC")
	private String balanceType = "";
	
	@JoinColumn(name="OACOID", updatable=false, insertable=false)
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private Company company;

	public String getObjectAccountCode() {
		return objectAccountCode;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionLong() {
		return descriptionLong;
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

	public Company getCompany() {
		return company;
	}

	void setObjectAccountCode(String objectAccountCode) {
		this.objectAccountCode = objectAccountCode;
	}

	void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
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

	void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String getId() {
		return getObjectAccountCode();
	}
	
	
}
