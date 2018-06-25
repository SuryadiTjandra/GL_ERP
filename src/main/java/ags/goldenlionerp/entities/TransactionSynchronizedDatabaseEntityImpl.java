package ags.goldenlionerp.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class TransactionSynchronizedDatabaseEntityImpl extends DatabaseEntity implements TransactionDatabaseEntity, SynchronizedDatabaseEntity{

	@Column(name="DTLS")
	protected Timestamp lastSynchronizedDate;
	
	@Column(name="DTLT")
	protected Timestamp lastTransactionDate;

	@JsonProperty(access=Access.READ_ONLY)
	public Optional<LocalDateTime> getLastSynchronizedDate() {
		return Optional.ofNullable(lastSynchronizedDate).map(Timestamp::toLocalDateTime);
	}
	
	protected void setLastSynchronizedDate(LocalDateTime lastSynchronizedDate) {
		this.lastSynchronizedDate = Timestamp.valueOf(lastSynchronizedDate);
	}
	
	@Override
	@JsonProperty(access=Access.READ_ONLY)
	public Optional<LocalDateTime> getLastTransactionDate() {
		return Optional.ofNullable(lastTransactionDate).map(Timestamp::toLocalDateTime);
	}
	
	protected void setLastTransactionDate(LocalDateTime lastSynchronizedDate) {
		this.lastTransactionDate = Timestamp.valueOf(lastSynchronizedDate);
	}

}
