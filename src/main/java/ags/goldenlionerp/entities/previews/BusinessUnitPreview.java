package ags.goldenlionerp.entities.previews;

import org.springframework.hateoas.ResourceSupport;

import ags.goldenlionerp.masterdata.businessunit.BusinessUnit;

public class BusinessUnitPreview extends ResourceSupport{
	private String businessUnitId;
	private String description;
	
	BusinessUnitPreview(BusinessUnit bu) {
		this.businessUnitId = bu.getBusinessUnitId();
		this.description = bu.getDescription();
	}

	public String getDescription() {
		return description;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}


}
