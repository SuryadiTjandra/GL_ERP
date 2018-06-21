package ags.goldenlionerp.masterdata.itemmaster;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.entities.embeddables.DataGroups;

@Embeddable
@AttributeOverrides({
	@AttributeOverride(name="categoryCode", column=@Column(name="IMITC01")),
	@AttributeOverride(name="brandCode", column=@Column(name="IMITC02")),
	@AttributeOverride(name="typeCode", column=@Column(name="IMITC03")),
	@AttributeOverride(name="categoryCode4", column=@Column(name="IMITC04")),
	@AttributeOverride(name="landedCostRule", column=@Column(name="IMITC05")),
	@AttributeOverride(name="categoryCode6", column=@Column(name="IMITC06")),
	@AttributeOverride(name="categoryCode7", column=@Column(name="IMITC07")),
	@AttributeOverride(name="categoryCode8", column=@Column(name="IMITC08")),
	@AttributeOverride(name="categoryCode9", column=@Column(name="IMITC09")),
	@AttributeOverride(name="categoryCode10", column=@Column(name="IMITC10")),
	@AttributeOverride(name="salesReportingCode1", column=@Column(name="IMSLRC1")),
	@AttributeOverride(name="salesReportingCode2", column=@Column(name="IMSLRC2")),
	@AttributeOverride(name="salesReportingCode3", column=@Column(name="IMSLRC3")),
	@AttributeOverride(name="salesReportingCode4", column=@Column(name="IMSLRC4")),
	@AttributeOverride(name="salesReportingCode5", column=@Column(name="IMSLRC5")),
	@AttributeOverride(name="salesReportingCode6", column=@Column(name="IMSLRC6")),
	@AttributeOverride(name="salesReportingCode7", column=@Column(name="IMSLRC7")),
	@AttributeOverride(name="salesReportingCode8", column=@Column(name="IMSLRC8")),
	@AttributeOverride(name="salesReportingCode9", column=@Column(name="IMSLRC9")),
	@AttributeOverride(name="salesReportingCode0", column=@Column(name="IMSLRC0")),
})
public class ItemDataGroups extends DataGroups {
	
	//for JPA
	ItemDataGroups() {}

	
}
