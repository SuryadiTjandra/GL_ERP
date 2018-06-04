package ags.goldenlionerp.entities.previews;

import ags.goldenlionerp.masterdata.businessunit.BusinessUnit;
import ags.goldenlionerp.masterdata.company.Company;

public class Previews {
	private Previews() {}
	
	public static CompanyPreview getPreview(Company company) {
		return new CompanyPreview(company);
	}
	
	public static BusinessUnitPreview getPreview(BusinessUnit bu) {
		return new BusinessUnitPreview(bu);
	}
}
