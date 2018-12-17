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
public abstract class TransactionDatabaseEntityImpl<ID extends Serializable> extends DatabaseEntity<ID> implements TransactionDatabaseEntity {
	@Column(name="DTLT")
	protected Timestamp lastTransactionDate;

	@Override
	@JsonProperty(access=Access.READ_ONLY)
	public Optional<LocalDateTime> getLastTransactionDate() {
		return Optional.ofNullable(lastTransactionDate).map(Timestamp::toLocalDateTime);
	}
	
	protected void setLastTransactionDate(LocalDateTime lastSynchronizedDate) {
		this.lastTransactionDate = Timestamp.valueOf(lastSynchronizedDate);
	}
}
