package ags.goldenlionerp.application.item.uomconversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import ags.goldenlionerp.entities.SynchronizedDatabaseEntityImpl;
import static java.math.BigDecimal.ONE;

@Entity
@Table(name="T41003")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="UCUID", updatable=false)),
	@AttributeOverride(name="inputDate", column=@Column(name="UCDTIN", updatable=false)),
	@AttributeOverride(name="inputTime", column=@Column(name="UCTMIN", updatable=false)),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="UCUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="UCDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="UCTMLU")),
	@AttributeOverride(name="lastSynchronizedDate", column=@Column(name="UCDTLS")),
	@AttributeOverride(name="computerId", column=@Column(name="UCCID")),
})
@IdClass(StandardUomConversionPK.class)
public class StandardUomConversion extends SynchronizedDatabaseEntityImpl<StandardUomConversionPK>{

	@Id
	@Column(name="UCUOM")
	private String uomFrom;
	
	@Id
	@Column(name="UCUOMR")
	private String uomTo;
	
	@Column(name="UCCNV", precision=19, scale=9)
	private BigDecimal conversionValue;
	
	@SuppressWarnings("unused")
	private StandardUomConversion() {};
	
	public StandardUomConversion(String uomFrom, String uomTo, BigDecimal conversionValue) {
		super();
		this.uomFrom = uomFrom;
		this.uomTo = uomTo;
		this.conversionValue = conversionValue;
	}

	@Override
	public StandardUomConversionPK getId() {
		return new StandardUomConversionPK(uomFrom, uomTo);
	}

	public String getUomFrom() {
		return uomFrom;
	}

	public String getUomTo() {
		return uomTo;
	}

	public BigDecimal getConversionValue() {
		return conversionValue;
	}

	void setUomFrom(String uomFrom) {
		this.uomFrom = uomFrom;
	}

	void setUomTo(String uomTo) {
		this.uomTo = uomTo;
	}

	void setConversionValue(BigDecimal conversionValue) {
		this.conversionValue = conversionValue;
	}
	
	public StandardUomConversion getReverseConversion() {
		return new StandardUomConversion(uomTo, uomFrom, ONE.divide(conversionValue, 9, RoundingMode.HALF_UP));
	}

}
