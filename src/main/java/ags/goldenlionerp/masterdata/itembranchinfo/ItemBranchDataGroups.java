package ags.goldenlionerp.masterdata.itembranchinfo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import ags.goldenlionerp.entities.embeddables.DataGroups;

@Embeddable
@AttributeOverrides({
	@AttributeOverride(name="categoryCode", column=@Column(name="IBITC01")),
	@AttributeOverride(name="brandCode", column=@Column(name="IBITC02")),
	@AttributeOverride(name="typeCode", column=@Column(name="IBITC03")),
	@AttributeOverride(name="categoryCode4", column=@Column(name="IBITC04")),
	@AttributeOverride(name="landedCostRule", column=@Column(name="IBITC05")),
	@AttributeOverride(name="categoryCode6", column=@Column(name="IBITC06")),
	@AttributeOverride(name="categoryCode7", column=@Column(name="IBITC07")),
	@AttributeOverride(name="categoryCode8", column=@Column(name="IBITC08")),
	@AttributeOverride(name="categoryCode9", column=@Column(name="IBITC09")),
	@AttributeOverride(name="categoryCode10", column=@Column(name="IBITC10")),
	@AttributeOverride(name="salesReportingCode1", column=@Column(name="IBSLRC1")),
	@AttributeOverride(name="salesReportingCode2", column=@Column(name="IBSLRC2")),
	@AttributeOverride(name="salesReportingCode3", column=@Column(name="IBSLRC3")),
	@AttributeOverride(name="salesReportingCode4", column=@Column(name="IBSLRC4")),
	@AttributeOverride(name="salesReportingCode5", column=@Column(name="IBSLRC5")),
	@AttributeOverride(name="salesReportingCode6", column=@Column(name="IBSLRC6")),
	@AttributeOverride(name="salesReportingCode7", column=@Column(name="IBSLRC7")),
	@AttributeOverride(name="salesReportingCode8", column=@Column(name="IBSLRC8")),
	@AttributeOverride(name="salesReportingCode9", column=@Column(name="IBSLRC9")),
	@AttributeOverride(name="salesReportingCode0", column=@Column(name="IBSLRC0")),
})
public class ItemBranchDataGroups extends DataGroups {

}
