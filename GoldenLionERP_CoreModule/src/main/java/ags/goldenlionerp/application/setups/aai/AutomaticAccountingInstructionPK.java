package ags.goldenlionerp.application.setups.aai;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AutomaticAccountingInstructionPK implements Serializable{


	private static final long serialVersionUID = 610989042561815313L;

	@Column(name="AISEQ")
	private int sequence;
	
	@Column(name="AIAAID")
	private String aaiCode;
	
	@SuppressWarnings("unused")
	private AutomaticAccountingInstructionPK() {}
	
	public AutomaticAccountingInstructionPK(int sequence, String aaiCode) {
		this.sequence = sequence;
		this.aaiCode = aaiCode;
	}

	public int getSequence() {
		return sequence;
	}

	public String getAaiCode() {
		return aaiCode;
	}

	
	
	@Override
	public String toString() {
		return sequence + "_" + aaiCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aaiCode == null) ? 0 : aaiCode.hashCode());
		result = prime * result + sequence;
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
		AutomaticAccountingInstructionPK other = (AutomaticAccountingInstructionPK) obj;
		if (aaiCode == null) {
			if (other.aaiCode != null)
				return false;
		} else if (!aaiCode.equals(other.aaiCode))
			return false;
		if (sequence != other.sequence)
			return false;
		return true;
	}
	
	
	
}
