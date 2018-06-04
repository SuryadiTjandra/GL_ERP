package ags.goldenlionerp.entities.previews;

import org.springframework.hateoas.ResourceSupport;

import ags.goldenlionerp.masterdata.company.Company;

public class CompanyPreview extends ResourceSupport {
	private String companyId;
	private String description;
	
	CompanyPreview(Company company){
		this.companyId = company.getCompanyId();
		this.description = company.getDescription();
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public String getDescription() {
		return description;
	}
	
}
