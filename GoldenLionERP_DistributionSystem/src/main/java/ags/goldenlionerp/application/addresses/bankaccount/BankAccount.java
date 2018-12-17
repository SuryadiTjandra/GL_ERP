package ags.goldenlionerp.application.addresses.bankaccount;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T0105")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="BAUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="BADTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="BATMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="BAUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="BADTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="BATMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="BACID")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="BADTLS")),
})
public class BankAccount extends SynchronizedDatabaseEntityImpl<BankAccountPK> {

	@EmbeddedId @JsonUnwrapped
	private BankAccountPK pk;
	
	@Column(name="BADESB1")
	private String description;
	
	@JoinColumn(name="BAANUM", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private AddressBookMaster master;
	
	public BankAccountPK getPk() {
		return pk;
	}
	
	public String getDescription() {
		return description;
	}

	void setPk(BankAccountPK pk) {
		this.pk = pk;
	}

	void setDescription(String description) {
		this.description = description;
	}

	@Override
	public BankAccountPK getId() {
		return getPk();
	}

}
