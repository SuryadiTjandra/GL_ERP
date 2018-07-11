package ags.goldenlionerp.application.system.fiscalyear;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FiscalYearPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="FPFDP")
	private String fiscalDatePattern;
	
	@Column(name="FPYR")
	private int fiscalYear;

	@SuppressWarnings("unused")
	private FiscalYearPK() {}
	
	public FiscalYearPK(String fiscalDatePattern, int fiscalYear) {
		super();
		this.fiscalDatePattern = fiscalDatePattern;
		this.fiscalYear = fiscalYear;
	}

	public String getFiscalDatePattern() {
		return fiscalDatePattern;
	}

	public int getFiscalYear() {
		return fiscalYear;
	}

	@Override
	public String toString() {
		return fiscalDatePattern + "_" + fiscalYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fiscalDatePattern == null) ? 0 : fiscalDatePattern.hashCode());
		result = prime * result + fiscalYear;
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
		FiscalYearPK other = (FiscalYearPK) obj;
		if (fiscalDatePattern == null) {
			if (other.fiscalDatePattern != null)
				return false;
		} else if (!fiscalDatePattern.equals(other.fiscalDatePattern))
			return false;
		if (fiscalYear != other.fiscalYear)
			return false;
		return true;
	}
	
	
}
