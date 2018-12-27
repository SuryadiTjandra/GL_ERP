package ags.goldenlionerp.application.setups.currencyconversion;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.util.WebIdUtil;

@Embeddable
public class CurrencyConversionPK implements Serializable{

	private static final long serialVersionUID = -5246371468815558795L;

	@Column(name="VCEFDT", nullable=false)
	private Timestamp effectiveDate;
	
	@Column(name="VCFRCUR")
	private String fromCurrency;
	
	@Column(name="VCTOCUR")
	private String toCurrency;

	@SuppressWarnings("unused")
	private CurrencyConversionPK() {}
	
	@JsonCreator
	public CurrencyConversionPK(
			@JsonProperty("effectiveDate") LocalDate effectiveDate, 
			@JsonProperty("fromCurrency") String fromCurrency, 
			@JsonProperty("toCurrency") String toCurrency) {
		super();
		this.effectiveDate = Timestamp.valueOf(effectiveDate.atStartOfDay());
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate.toLocalDateTime().toLocalDate();
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = WebIdUtil.getWebIdDateFormatter();
		return String.join("_",
				format.format(getEffectiveDate()),
				fromCurrency, toCurrency);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((fromCurrency == null) ? 0 : fromCurrency.hashCode());
		result = prime * result + ((toCurrency == null) ? 0 : toCurrency.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyConversionPK other = (CurrencyConversionPK) obj;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (fromCurrency == null) {
			if (other.fromCurrency != null)
				return false;
		} else if (!fromCurrency.equals(other.fromCurrency))
			return false;
		if (toCurrency == null) {
			if (other.toCurrency != null)
				return false;
		} else if (!toCurrency.equals(other.toCurrency))
			return false;
		return true;
	}
	
	
	
}
