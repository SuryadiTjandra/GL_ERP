package ags.goldenlionerp.masterdata.lotmaster;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

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

import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.entities.DatabaseEntity;
import ags.goldenlionerp.masterdata.itemmaster.ItemMaster;

@Entity
@Table(name="T4108")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="LTUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="LTDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="LTTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="LTUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="LTDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="LTTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="LTCID"))
})
public class LotMaster extends DatabaseEntity<LotMasterPK> {
	
	@EmbeddedId
	@JsonUnwrapped
	private LotMasterPK pk;
	
	@Column(name="LTLDSC")
	private String lotDescription = "";
	
	@Column(name="LTLSC")
	private String lotStatusCode = "";
	
	@Column(name="LTSERN")
	private String serialNumber = "";
	
	@Column(name="LTMLOT1")
	private String memoLot1 = "";
	
	@Column(name="LTMLOT2")
	private String memoLot2 = "";
	
	@Column(name="LTMLOT3")
	private String memoLot3 = "";
	
	@Column(name="LTEXPDT")
	private Timestamp expiredDate;
	
	@Column(name="LTBBDT")
	private Timestamp bestBeforeDate;
	
	@Column(name="LTBODT")
	private Timestamp baseOnDate;
	
	@Column(name="LTLEDT")
	private Timestamp lotEffectiveDate;
	
	@Column(name="LTOHDT")
	private Timestamp onHandDate;
	
	@Column(name="LTCLDT")
	private Timestamp closedDate;
	
	@JoinColumn(name="LTBUID", updatable = false, insertable = false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private BusinessUnit businessUnit;
	
	@JoinColumn(name="LTINUM", updatable = false, insertable = false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private ItemMaster item;

	public LotMasterPK getPk() {
		return pk;
	}

	public String getLotDescription() {
		return lotDescription;
	}

	public String getLotStatusCode() {
		return lotStatusCode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getMemoLot1() {
		return memoLot1;
	}

	public String getMemoLot2() {
		return memoLot2;
	}

	public String getMemoLot3() {
		return memoLot3;
	}

	public Optional<LocalDateTime> getExpiredDate() {
		return Optional.ofNullable(expiredDate).map(Timestamp::toLocalDateTime);
	}

	public Optional<LocalDateTime> getBestBeforeDate() {
		return Optional.ofNullable(bestBeforeDate).map(Timestamp::toLocalDateTime);
	}

	public Optional<LocalDateTime> getBaseOnDate() {
		return Optional.ofNullable(baseOnDate).map(Timestamp::toLocalDateTime);
	}

	public Optional<LocalDateTime> getLotEffectiveDate() {
		return Optional.ofNullable(lotEffectiveDate).map(Timestamp::toLocalDateTime);
	}

	public Optional<LocalDateTime> getOnHandDate() {
		return Optional.ofNullable(onHandDate).map(Timestamp::toLocalDateTime);
	}

	public Optional<LocalDateTime> getClosedDate() {
		return Optional.ofNullable(closedDate).map(Timestamp::toLocalDateTime);
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public ItemMaster getItem() {
		return item;
	}

	void setPk(LotMasterPK pk) {
		this.pk = pk;
	}

	void setLotDescription(String lotDescription) {
		this.lotDescription = lotDescription;
	}

	void setLotStatusCode(String lotStatusCode) {
		this.lotStatusCode = lotStatusCode;
	}

	void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	void setMemoLot1(String memoLot1) {
		this.memoLot1 = memoLot1;
	}

	void setMemoLot2(String memoLot2) {
		this.memoLot2 = memoLot2;
	}

	void setMemoLot3(String memoLot3) {
		this.memoLot3 = memoLot3;
	}

	void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = Timestamp.valueOf(expiredDate);
	}

	void setBestBeforeDate(LocalDateTime bestBeforeDate) {
		this.bestBeforeDate = Timestamp.valueOf(bestBeforeDate);
	}

	void setBaseOnDate(LocalDateTime baseOnDate) {
		this.baseOnDate = Timestamp.valueOf(baseOnDate);
	}

	void setLotEffectiveDate(LocalDateTime lotEffectiveDate) {
		this.lotEffectiveDate = Timestamp.valueOf(lotEffectiveDate);
	}

	void setOnHandDate(LocalDateTime onHandDate) {
		this.onHandDate = Timestamp.valueOf(onHandDate);
	}

	void setClosedDate(LocalDateTime closedDate) {
		this.closedDate = Timestamp.valueOf(closedDate);
	}

	@Override
	public LotMasterPK getId() {
		return getPk();
	}
	
	
}
