package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;

@Entity
@Table(name="T41002")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="UMUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="UMDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="UMTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="UMUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="UMDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="UMTMLU")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="UMDTLS")),
	@AttributeOverride(name="computerId", column=@Column(name="UMCID")),
})
public class ItemUomConversion extends SynchronizedDatabaseEntityImpl<ItemUomConversionPK>{

	@EmbeddedId
	private ItemUomConversionPK pk;
	
	@Column(name="UMCNV")
	private BigDecimal conversionValue;
	
	@Column(name="UMCNV1")
	private BigDecimal conversionValueToPrimary;
	
	@Column(name="UMUOMST")
	private String uomStructure;
	
	@Column(name="UMDESA1")
	private String uomFromDescription;
	
	@Column(name="UMDESA2")
	private String uomToDescription;
	
	@SuppressWarnings("unused")
	private  ItemUomConversion() {}
	
	@JsonCreator
	public ItemUomConversion(
			@JsonProperty("itemCode") String itemCode, 
			@JsonProperty("uomFrom") String uomFrom, 
			@JsonProperty("uomTo") String uomTo, 
			@JsonProperty("conversionValue") BigDecimal conversionValue, 
			@JsonProperty("uomStructure") @Nullable String uomStructure) {
		super();
		this.pk = new ItemUomConversionPK(itemCode, uomFrom, uomTo);
		this.conversionValue = conversionValue;
		this.uomStructure = uomStructure;
	}

	@Override
	public ItemUomConversionPK getId() {
		return pk;
	}
	
	public String getItemCode() {
		return pk.getItemCode();
	}

	public String getUomFrom() {
		return pk.getUomFrom();
	}

	public String getUomTo() {
		return pk.getUomTo();
	}
	
	public BigDecimal getConversionValue() {
		return conversionValue;
	}

	public BigDecimal getConversionValueToPrimary() {
		return conversionValueToPrimary;
	}

	public String getUomStructure() {
		return uomStructure;
	}

	public String getUomFromDescription() {
		return uomFromDescription;
	}

	public String getUomToDescription() {
		return uomToDescription;
	}

	void setConversionValueToPrimary(BigDecimal conversionValueToPrimary) {
		this.conversionValueToPrimary = conversionValueToPrimary;
	}

	void setUomFromDescription(String uomFromDescription) {
		this.uomFromDescription = uomFromDescription;
	}

	void setUomToDescription(String uomToDescription) {
		this.uomToDescription = uomToDescription;
	}

}
