package ags.goldenlionerp.application.system.user;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="SCUST")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "userSecurityType")
@JsonSubTypes({ 
	@Type(value = User.class, name = "U"), 
	@Type(value = UserGroup.class, name = "G") 
})
public abstract class UserBase extends DatabaseEntity<String> {

	@Id
	@Column(name="SCUSI")
	protected String userSecurityId;
	
	@Column(name="SCUSC")
	@RestResource(exported=false)
	protected String userSecurityCode;
	
	@Column(name="SCUSL")
	protected int userSecurityLevel;
	
	@Column(name="SCUSG")
	protected String userSecurityGroupId;
	
	@Column(name="SCUST", insertable=false, updatable=false)
	protected String userSecurityType;
	
	@Column(name="SCANUM")
	protected String addressNumber;
	
	@Column(name="SCMTOE")
	protected String menuToExecuteBOS;
	
	@Column(name="SCMTOE1")
	protected String menuToExecutePOS;
	
	@Column(name="SCMTOE2")
	protected String menuToExecuteHOS;
	
	@Column(name="SCTAGID")
	protected String tagId;
	
	@Column(name="SCAPRCD")
	protected String approvalRouteCode;
	
	@Column(name="SCBUID")
	protected String businessUnitId;
	
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

	public String getUserSecurityGroupId() {
		return userSecurityGroupId;
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

	void setUserSecurityGroupId(String userSecurityGroupId) {
		this.userSecurityGroupId = userSecurityGroupId;
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
