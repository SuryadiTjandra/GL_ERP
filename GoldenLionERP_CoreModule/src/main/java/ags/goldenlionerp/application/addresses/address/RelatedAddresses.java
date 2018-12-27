package ags.goldenlionerp.application.addresses.address;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RelatedAddresses {

	@Column(name="ADRLID1")
	private String salesperson = "";
	
	@Column(name="ADRLID2")
	private String relatedAddress2 = "";
	
	@Column(name="ADRLID3")
	private String relatedAddress3 = "";
	
	@Column(name="ADRLID4")
	private String relatedAddress4 = "";
	
	@Column(name="ADRLID5")
	private String relatedAddress5 = "";
	
	@Column(name="ADRLID6")
	private String relatedAddress6 = "";
	
	@Column(name="ADRLID7")
	private String relatedAddress7 = "";
	
	@Column(name="ADRLID8")
	private String relatedAddress8 = "";
	
	@Column(name="ADRLID9")
	private String relatedAddress9 = "";
	
	@Column(name="ADRLID0")
	private String relatedAddress0 = "";

	RelatedAddresses () {}
	
	public String getSalesperson() {
		return salesperson;
	}

	public String getRelatedAddress2() {
		return relatedAddress2;
	}

	public String getRelatedAddress3() {
		return relatedAddress3;
	}

	public String getRelatedAddress4() {
		return relatedAddress4;
	}

	public String getRelatedAddress5() {
		return relatedAddress5;
	}

	public String getRelatedAddress6() {
		return relatedAddress6;
	}

	public String getRelatedAddress7() {
		return relatedAddress7;
	}

	public String getRelatedAddress8() {
		return relatedAddress8;
	}

	public String getRelatedAddress9() {
		return relatedAddress9;
	}

	public String getRelatedAddress0() {
		return relatedAddress0;
	}

	void setSalesperson(String salesperson) {
		this.salesperson = salesperson;
	}

	void setRelatedAddress2(String relatedAddress2) {
		this.relatedAddress2 = relatedAddress2;
	}

	void setRelatedAddress3(String relatedAddress3) {
		this.relatedAddress3 = relatedAddress3;
	}

	void setRelatedAddress4(String relatedAddress4) {
		this.relatedAddress4 = relatedAddress4;
	}

	void setRelatedAddress5(String relatedAddress5) {
		this.relatedAddress5 = relatedAddress5;
	}

	void setRelatedAddress6(String relatedAddress6) {
		this.relatedAddress6 = relatedAddress6;
	}

	void setRelatedAddress7(String relatedAddress7) {
		this.relatedAddress7 = relatedAddress7;
	}

	void setRelatedAddress8(String relatedAddress8) {
		this.relatedAddress8 = relatedAddress8;
	}

	void setRelatedAddress9(String relatedAddress9) {
		this.relatedAddress9 = relatedAddress9;
	}

	void setRelatedAddress0(String relatedAddress0) {
		this.relatedAddress0 = relatedAddress0;
	}
	
	
}
