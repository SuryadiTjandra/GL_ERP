package ags.goldenlionerp.application.system.currencyconversion;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0030")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="VCUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="VCDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="VCTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="VCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="VCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="VCTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="VCCID")),
})
public class CurrencyConversion extends DatabaseEntity {

	@EmbeddedId @JsonUnwrapped
	private CurrencyConversionPK pk;
	
	@Column(name="VCMFAC", precision=19, scale=9)
	private BigDecimal multiplyFactor = new BigDecimal(0);
	
	@Column(name="VCDFAC", precision=19, scale=9)
	private BigDecimal divisionFactor = new BigDecimal(0);
	
	@Column(name="VCDESB1")
	private String description = "";
	
	@Column(name="VCCNDT")
	private Timestamp conditionDate;

	public CurrencyConversionPK getPk() {
		return pk;
	}

	public BigDecimal getMultiplyFactor() {
		return multiplyFactor;
	}

	public BigDecimal getDivisionFactor() {
		return divisionFactor;
	}

	public String getDescription() {
		return description;
	}

	public Optional<LocalDate> getConditionDate() {
		return Optional.ofNullable(conditionDate)
					.map(Timestamp::toLocalDateTime)
					.map(LocalDateTime::toLocalDate);
	}

	void setPk(CurrencyConversionPK pk) {
		this.pk = pk;
	}

	void setMultiplyFactor(BigDecimal multiplyFactor) {
		this.multiplyFactor = multiplyFactor;
	}

	void setDivisionFactor(BigDecimal divisionFactor) {
		this.divisionFactor = divisionFactor;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setConditionDate(LocalDate conditionDate) {
		if (conditionDate == null) 
			this.conditionDate = null;
		this.conditionDate = Timestamp.valueOf(conditionDate.atStartOfDay());
	}
	
	
}
