package ags.goldenlionerp.application.item.itemLocation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.item.location.LocationMaster;
import ags.goldenlionerp.application.item.lotmaster.LotMaster;
import ags.goldenlionerp.entities.TransactionDatabaseEntityImpl;

@Entity
@Table(name="T41021")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="ILUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="ILDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="ILTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="ILUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="ILDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="ILTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="ILCID")),
	@AttributeOverride(name="lastTransactionDate", column=@Column(name="ILDTLT"))
})
public class ItemLocation extends TransactionDatabaseEntityImpl<ItemLocationPK>{

	@EmbeddedId @JsonUnwrapped
	private ItemLocationPK pk;
	
	@Column(name="ILLSC")
	private String lotStatusCode = "";
	
	@Column(name="ILLOCST")
	private String locationStatus = "";
	
	@Column(name="ILEXPDT")
	private Timestamp expiredDate;
	
	@Column(name="ILGLCLS")
	private String glClass = "";
	
	@Embedded
	private ItemLocationQuantities quantities = new ItemLocationQuantities();
	
	@JoinColumn(name="ILINUM", updatable = false, insertable = false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private ItemMaster item;
	
	@JoinColumns({
		@JoinColumn(name="ILBUID", referencedColumnName="LMBUID", updatable=false, insertable = false),
		@JoinColumn(name="ILLOCID", referencedColumnName = "LMLOCID", updatable = false, insertable = false)
	})
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	private LocationMaster location;
	
	@JoinColumns({
		@JoinColumn(name="ILINUM", referencedColumnName="LTINUM", updatable=false, insertable = false),
		@JoinColumn(name="ILBUID", referencedColumnName="LTBUID", updatable = false, insertable = false),
		@JoinColumn(name="ILSNLOT", referencedColumnName="LTSNLOT", updatable=false, insertable = false),
	})
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	private LotMaster lot;

	public ItemLocationPK getPk() {
		return pk;
	}
	
	public String getLotStatusCode() {
		return lotStatusCode;
	}

	public String getLocationStatus() {
		return locationStatus;
	}

	public Optional<LocalDateTime> getExpiredDate() {
		return Optional.ofNullable(expiredDate).map(Timestamp::toLocalDateTime);
	}

	public String getGlClass() {
		return glClass;
	}

	public ItemLocationQuantities getQuantities() {
		return quantities;
	}

	public ItemMaster getItem() {
		return item;
	}

	public void setItem(ItemMaster item) {
		this.item = item;
	}

	public LocationMaster getLocation() {
		return location;
	}

	public void setLocation(LocationMaster location) {
		this.location = location;
	}

	public LotMaster getLot() {
		return lot;
	}

	public void setLot(LotMaster lot) {
		this.lot = lot;
	}

	void setPk(ItemLocationPK pk) {
		this.pk = pk;
	}

	void setLotStatusCode(String lotStatusCode) {
		this.lotStatusCode = lotStatusCode;
	}

	void setLocationStatus(String locationStatus) {
		this.locationStatus = locationStatus;
	}

	void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = Timestamp.valueOf(expiredDate);
	}

	void setGlClass(String glClass) {
		this.glClass = glClass;
	}

	void setQuantities(ItemLocationQuantities quantities) {
		this.quantities = quantities;
	}

	public boolean isPrimary() {
		return this.locationStatus.equals("P");
	}
	
	@Override
	public ItemLocationPK getId() {
		return getPk();
	}
	
	
}
