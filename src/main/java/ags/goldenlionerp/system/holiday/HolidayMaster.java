package ags.goldenlionerp.system.holiday;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ags.goldenlionerp.entities.DatabaseEntity;

@Entity
@Table(name="T0035")
@AttributeOverrides({
	@AttributeOverride(name="inputUserId", column=@Column(name="HDUID")),
	@AttributeOverride(name="inputDate", column=@Column(name="HDDTIN")),
	@AttributeOverride(name="inputTime", column=@Column(name="HDTMIN")),
	@AttributeOverride(name="lastUpdateUserId", column=@Column(name="HDUIDM")),
	@AttributeOverride(name="lastUpdateDate", column=@Column(name="HDDTLU")),
	@AttributeOverride(name="lastUpdateTime", column=@Column(name="HDTMLU")),
	@AttributeOverride(name="computerId", column=@Column(name="HDCID")),
})
public class HolidayMaster extends DatabaseEntity {

	@Id
	@Column(name="HDOFDT")
	private LocalDate offDate;
	
	@Column(name="HDOFTY")
	private String offDateType = "";
	
	@Column(name="HDDESB1")
	private String description = "";

	public LocalDate getOffDate() {
		return offDate;
	}

	public String getOffDateType() {
		return offDateType;
	}

	public String getDescription() {
		return description;
	}

	void setOffDate(LocalDate offDate) {
		this.offDate = offDate;
	}

	void setOffDateType(String offDateType) {
		this.offDateType = offDateType;
	}

	void setDescription(String description) {
		this.description = description;
	}
	
	
}
