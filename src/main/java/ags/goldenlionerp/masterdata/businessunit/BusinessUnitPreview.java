package ags.goldenlionerp.masterdata.businessunit;

import org.springframework.hateoas.ResourceSupport;

public class BusinessUnitPreview extends ResourceSupport{
	private String businessUnitId;
	private String description;
	
	private BusinessUnitPreview(BusinessUnit bu) {
		this.businessUnitId = bu.getBusinessUnitId();
		this.description = bu.getDescription();
	}
	
	public static BusinessUnitPreview getPreview(BusinessUnit bu) {
		return new BusinessUnitPreview(bu);
	}

	public String getDescription() {
		return description;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}


}
