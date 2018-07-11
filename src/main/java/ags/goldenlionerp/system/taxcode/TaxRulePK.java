package ags.goldenlionerp.system.taxcode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class TaxRulePK implements Serializable {

	private static final long serialVersionUID = 7867471180298056727L;

	@Column(name="TSTAXCD")
	private String taxCode;
	
	@Column(name="TSEFDT", nullable=false)
	private Timestamp effectiveDate;

	@SuppressWarnings("unused")
	private TaxRulePK() {}
	
	@JsonCreator
	public TaxRulePK(
			@JsonProperty("taxCode") String taxCode, 
			@JsonProperty("effectiveDate") LocalDate effectiveDate) {
		super();
		this.taxCode = taxCode;
		this.effectiveDate = Timestamp.valueOf(effectiveDate.atStartOfDay());
	}

	public String getTaxCode() {
		return taxCode;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate.toLocalDateTime().toLocalDate();
	}

	
	
	@Override
	public String toString() {
		String efDtStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(getEffectiveDate());
		return taxCode + "_" + efDtStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((taxCode == null) ? 0 : taxCode.hashCode());
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
		TaxRulePK other = (TaxRulePK) obj;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (taxCode == null) {
			if (other.taxCode != null)
				return false;
		} else if (!taxCode.equals(other.taxCode))
			return false;
		return true;
	}
	
	
}
