package ags.goldenlionerp.system.dmaai;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="T0016")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class DMAAIHeader {

	@Id
	@Column(name="MKDMNO")
	private int dmaaiNo;
	
	@Column(name="MKDESB1")
	private String description1 = "";
	
	@Column(name="MKDESB2")
	private String description2 = "";
	
	@Column(name="MKPEC")
	private Boolean hasPostingEditCode = false;
	
	@Column(name="MKLOD")
	private int levelOfDetail;
	
	@Column(name="MKMFAC", precision = 19, scale=9)
	private BigDecimal multiplyFactor;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="header", orphanRemoval=true)
	private List<DMAAIDetail> details;

	public int getDmaaiNo() {
		return dmaaiNo;
	}

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
	}

	public Boolean getHasPostingEditCode() {
		return hasPostingEditCode;
	}

	public int getLevelOfDetail() {
		return levelOfDetail;
	}

	public BigDecimal getMultiplyFactor() {
		return multiplyFactor;
	}

	public List<DMAAIDetail> getDetails() {
		return details;
	}

	void setDmaaiNo(int dmaaiNo) {
		this.dmaaiNo = dmaaiNo;
	}

	void setDescription1(String description1) {
		this.description1 = description1;
	}

	void setDescription2(String description2) {
		this.description2 = description2;
	}

	void setHasPostingEditCode(Boolean hasPostingEditCode) {
		this.hasPostingEditCode = hasPostingEditCode;
	}

	void setLevelOfDetail(int levelOfDetail) {
		this.levelOfDetail = levelOfDetail;
	}

	void setMultiplyFactor(BigDecimal multiplyFactor) {
		this.multiplyFactor = multiplyFactor;
	}
	
}
