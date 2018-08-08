package ags.goldenlionerp.application.setups.nextnumber;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0002")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="NNUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="NNDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="NNTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="NNUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="NNDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="NNTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="NNCID"))
})
public class NextNumber extends DatabaseEntity<NextNumberPK> {

	@EmbeddedId
	@JsonUnwrapped
	private NextNumberPK pk;
	
	@Column(name="NNSEQ")
	private int nextSequence;
	

	@Override
	public NextNumberPK getId() {
		return getPk();
	}
	
	public NextNumberPK getPk() {
		return pk;
	}

	public int getNextSequence() {
		return nextSequence;
	}

	void setPk(NextNumberPK pk) {
		this.pk = pk;
	}

	void setNextSequence(int nextNumber) {
		this.nextSequence = nextNumber;
	}


}
