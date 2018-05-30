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

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@MappedSuperclass
@EntityListeners(DatabaseEntityListener.class)
public abstract class DatabaseEntity extends ResourceSupport {
	
	@Column(name="UID")
	@JsonProperty(access=Access.READ_ONLY)
	protected String inputUserId;

	@Column(name="DTIN")
	protected Timestamp inputDate;
	
	@Column(name="TMIN")
	protected String inputTime="";
	
	@JsonProperty(access=Access.READ_ONLY)
	@Column(name="UIDM")W
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
		return toLocalDateTime(inputDate, inputTime);
	}
	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}
	@JsonProperty(access=Access.READ_ONLY)
	public LocalDateTime getLastUpdateDateTime() {
		return toLocalDateTime(lastUpdateDate, lastUpdateTime);
	}	
	public String getComputerId() {
		return computerId;
	}
	

	protected void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}
	protected void setInputDateTime(LocalDateTime inputDateTime) {
		this.inputDate = toDate(inputDateTime);
		this.inputTime = toTimeString(inputDateTime);
	}
	protected void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
	protected void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
		this.lastUpdateDate = toDate(lastUpdateDateTime);
		this.lastUpdateTime = toTimeString(lastUpdateDateTime);
	}
	protected void setComputerId(String computerId) {
		this.computerId = computerId;
	}
	
	private LocalDateTime toLocalDateTime(Timestamp date, String time) {
		if (date == null) return null;
		
		LocalDateTime datetime = date.toLocalDateTime();
		if (time == null || time.isEmpty())
			return datetime;
		
		try {
			DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_TIME;
			LocalTime localTime = LocalTime.parse(time, format);
			return LocalDateTime.of(datetime.toLocalDate(), localTime);
		} catch (DateTimeParseException e) {
			return datetime;
		}
	}
	
	private Timestamp toDate(LocalDateTime datetime) {
		return Timestamp.valueOf(datetime.truncatedTo(ChronoUnit.DAYS));
	}
	private String toTimeString(LocalDateTime datetime) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
		return datetime.toLocalTime().format(format);
	}
}
