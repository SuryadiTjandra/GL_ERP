package ags.goldenlionerp.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@MappedSuperclass
public abstract class DatabaseEntity extends ResourceSupport {
	
	@Column(name="UID")
	@JsonProperty(access=Access.READ_ONLY)
	protected String inputUserId;

	@Column(name="DTIN")
	protected Timestamp inputDate;
	
	@Column(name="TMIN")
	protected String inputTime="";
	
	@JsonProperty(access=Access.READ_ONLY)
	@Column(name="UIDM")
	protected String lastUpdateUserId;

	@Column(name="DTLU")
	protected Timestamp lastUpdateDate;

	@Column(name="TMLU")
	protected String lastUpdateTime="";
	
	@Column(name="CID")
	protected String computerId;
	
	public String getInputUserId() {
		return inputUserId;
	}
	@JsonProperty(access=Access.READ_ONLY)
	public LocalDateTime getInputDateTime() {
		return DatabaseEntityUtil.toLocalDateTime(inputDate, inputTime);
	}
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	@JsonProperty(access=Access.READ_ONLY)
	public LocalDateTime getLastUpdateDateTime() {
		return DatabaseEntityUtil.toLocalDateTime(lastUpdateDate, lastUpdateTime);
	}	
	public String getComputerId() {
		return computerId;
	}
	

	protected void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}
	protected void setInputDateTime(LocalDateTime inputDateTime) {
		this.inputDate = DatabaseEntityUtil.toDate(inputDateTime);
		this.inputTime = DatabaseEntityUtil.toTimeString(inputDateTime);
	}
	protected void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	protected void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
		this.lastUpdateDate = DatabaseEntityUtil.toDate(lastUpdateDateTime);
		this.lastUpdateTime = DatabaseEntityUtil.toTimeString(lastUpdateDateTime);
	}
	protected void setComputerId(String computerId) {
		this.computerId = computerId;
	}
	
	@PrePersist
	void setCreationInfo() {
		DatabaseEntityUtil.setCreationInfo(this);
	}
	
	@PreUpdate
	void setUpdateInfo() {
		DatabaseEntityUtil.setUpdateInfo(this);
	}
}
