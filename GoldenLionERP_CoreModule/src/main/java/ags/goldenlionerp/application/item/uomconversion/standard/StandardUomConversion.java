package ags.goldenlionerp.application.item.uomconversion.standard;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
//@IdClass(StandardUomConversionPK.class)
public class StandardUomConversion extends SynchronizedDatabaseEntityImpl<StandardUomConversionPK>{

	//@Id
	//@Column(name="UCUOM")
	//private String uomFrom;
	
	//@Id
	//@Column(name="UCUOMR")
	//private String uomTo;
	@EmbeddedId
	private StandardUomConversionPK pk;
	
	@Column(name="UCCNV", precision=19, scale=9, nullable=false)
	private BigDecimal conversionValue;
	
	@SuppressWarnings("unused")
	private StandardUomConversion() {};
	
	@JsonCreator
	public StandardUomConversion(
			@JsonProperty("uomFrom") String uomFrom, 
			@JsonProperty("uomTo") String uomTo, 
			@JsonProperty("conversionValue") BigDecimal conversionValue) {
		super();
		//this.uomFrom = uomFrom;
		//this.uomTo = uomTo;
		this.pk = new StandardUomConversionPK(uomFrom, uomTo);
		this.conversionValue = conversionValue;
	}

	@Override
	public StandardUomConversionPK getId() {
		//return new StandardUomConversionPK(uomFrom, uomTo);
		return pk;
	}

	public String getUomFrom() {
		return pk.getUomFrom();//uomFrom;
	}

	public String getUomTo() {
		return pk.getUomTo();//uomTo;
	}

	public BigDecimal getConversionValue() {
		return conversionValue;
	}

	void setUomFrom(String uomFrom) {
		//this.uomFrom = uomFrom;
		this.pk = new StandardUomConversionPK(uomFrom, pk.getUomTo());
	}

	void setUomTo(String uomTo) {
		//this.uomTo = uomTo;
		this.pk = new StandardUomConversionPK(pk.getUomFrom(), uomTo);
	}

	void setConversionValue(BigDecimal conversionValue) {
		this.conversionValue = conversionValue;
	}
	
	public StandardUomConversion reverseConversion() {
		return new StandardUomConversion(pk.getUomTo(), pk.getUomFrom(), ONE.divide(conversionValue, 9, RoundingMode.HALF_UP));
	}

	@Override
	public String toString() {
		return String.format("1 %s = %d %s", this.pk.getUomFrom(), this.conversionValue, this.pk.getUomTo());
	}
}
