package ags.goldenlionerp.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@MappedSuperclass
public abstract class SynchronizedDatabaseEntityImpl<ID extends Serializable> extends DatabaseEntity<ID> implements SynchronizedDatabaseEntity {
	@Column(name="DTLS")
	protected Timestamp lastSynchronizedDate;
	
	@JsonProperty(access=Access.READ_ONLY)
	public Optional<LocalDateTime> getLastSynchronizedDate() {
		return Optional.ofNullable(lastSynchronizedDate).map(Timestamp::toLocalDateTime);
	}
	
	protected void setLastSynchronizedDate(LocalDateTime lastSynchronizedDate) {
		this.lastSynchronizedDate = Timestamp.valueOf(lastSynchronizedDate);
	}
}
