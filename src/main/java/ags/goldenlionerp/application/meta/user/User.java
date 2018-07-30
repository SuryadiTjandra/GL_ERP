package ags.goldenlionerp.application.meta.user;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T9801")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="SCUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="SCDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="SCTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="SCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="SCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="SCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="SCCID")),
})
public class User extends DatabaseEntity<String> {

	@Id
	@Column(name="SCUSI")
	private String userSecurityId;
	
	@Column(name="SCUSC")
	private String userSecurityCode;
	
	@Column(name="SCUSL")
	private int userSecurityLevel;
	
	@Column(name="SCUSG")
	private String userSecurityGroup;
	
	@Column(name="SCUST")
	private String userSecurityType;
	
	@Column(name="SCANUM")
	private String addressNumber;
	
	@Column(name="SCMTOE")
	private String menuToExecuteBOS;
	
	@Column(name="SCMTOE1")
	private String menuToExecutePOS;
	
	@Column(name="SCMTOE2")
	private String menuToExecuteHOS;
	
	@Column(name="SCTAGID")
	private String tagId;
	
	@Column(name="SCAPRCD")
	private String approvalRouteCode;
	
	@Column(name="SCBUID")
	private String businessUnitId;
	
	@Override
	public String getId() {
		return getUserSecurityId();
	}

	public String getUserSecurityId() {
		return userSecurityId;
	}

	public String getUserSecurityCode() {
		return userSecurityCode;
	}

	public int getUserSecurityLevel() {
		return userSecurityLevel;
	}

	public String getUserSecurityGroup() {
		return userSecurityGroup;
	}

	public String getUserSecurityType() {
		return userSecurityType;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public String getMenuToExecuteBOS() {
		return menuToExecuteBOS;
	}

	public String getMenuToExecutePOS() {
		return menuToExecutePOS;
	}

	public String getMenuToExecuteHOS() {
		return menuToExecuteHOS;
	}

	public String getTagId() {
		return tagId;
	}

	public String getApprovalRouteCode() {
		return approvalRouteCode;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	void setUserSecurityId(String userSecurityId) {
		this.userSecurityId = userSecurityId;
	}

	void setUserSecurityCode(String userSecurityCode) {
		this.userSecurityCode = userSecurityCode;
	}

	void setUserSecurityLevel(int userSecurityLevel) {
		this.userSecurityLevel = userSecurityLevel;
	}

	void setUserSecurityGroup(String userSecurityGroup) {
		this.userSecurityGroup = userSecurityGroup;
	}

	void setUserSecurityType(String userSecurityType) {
		this.userSecurityType = userSecurityType;
	}

	void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	void setMenuToExecuteBOS(String menuToExecuteBOS) {
		this.menuToExecuteBOS = menuToExecuteBOS;
	}

	void setMenuToExecutePOS(String menuToExecutePOS) {
		this.menuToExecutePOS = menuToExecutePOS;
	}

	void setMenuToExecuteHOS(String menuToExecuteHOS) {
		this.menuToExecuteHOS = menuToExecuteHOS;
	}

	void setTagId(String tagId) {
		this.tagId = tagId;
	}

	void setApprovalRouteCode(String approvalRouteCode) {
		this.approvalRouteCode = approvalRouteCode;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

}
