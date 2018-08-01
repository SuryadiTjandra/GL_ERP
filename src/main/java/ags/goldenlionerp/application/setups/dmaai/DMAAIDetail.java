package ags.goldenlionerp.application.setups.dmaai;

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
@Table(name="T0016D")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="MLUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="MLDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="MLTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="MLUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="MLDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="MLTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="MLCID"))
})
public class DMAAIDetail extends DatabaseEntity<DMAAIDetailPK> {

	@EmbeddedId
	@JsonUnwrapped
	private DMAAIDetailPK pk;
	
	@Column(name="MLBUID")
	private String businessUnitId = "";
	
	@Column(name="MLOBJ")
	private String objectAccountCode = "";
	
	@Column(name="MLSUB")
	private String subsidiaryCode = "";
	
	@Column(name="MLDESB1")
	private String description1 = "";
	
	@Column(name="MLDESB2")
	private String description2 = "";
	
	@Column(name="MLDESB3")
	private String description3 = "";
	
	@Column(name="MLDESB4")
	private String description4 = "";
	
	@JoinColumn(name="MLDMNO", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private DMAAIHeader header;
	
	@JoinColumn(name="MLOBJ", insertable = false, updatable = false)
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private ChartOfAccount account;
	
	@JoinColumns({
		@JoinColumn(name="MLCOID", referencedColumnName="AMCOID", insertable=false, updatable=false),
		@JoinColumn(name="MLBUID", referencedColumnName="AMBUID", insertable=false, updatable=false),
		@JoinColumn(name="MLOBJ", referencedColumnName="AMOBJ", insertable=false, updatable=false),
		@JoinColumn(name="MLSUB", referencedColumnName="AMSUB", insertable=false, updatable=false)
	})
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	private AccountMaster accountMaster;

	public DMAAIDetailPK getPk() {
		return pk;
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

	public DMAAIHeader getHeader() {
		return header;
	}

	 void setPk(DMAAIDetailPK pk) {
		this.pk = pk;
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

	public Optional<ChartOfAccount> getAccount() {
		return Optional.ofNullable(account);
	}

	void setAccount(ChartOfAccount account) {
		this.account = account;
	}

	public Optional<AccountMaster> getAccountMaster() {
		return Optional.ofNullable(accountMaster);
	}

	void setAccountMaster(AccountMaster accountMaster) {
		this.accountMaster = accountMaster;
	}

	@Override
	public DMAAIDetailPK getId() {
		return getPk();
	}
	
	
}
