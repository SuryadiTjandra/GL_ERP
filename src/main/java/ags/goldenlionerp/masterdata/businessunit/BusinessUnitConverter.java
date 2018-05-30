package ags.goldenlionerp.masterdata.businessunit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.hateoas.EntityLinks;

import ags.goldenlionerp.util.BeanFinder;

@Converter
public class BusinessUnitConverter implements AttributeConverter<BusinessUnitPreview, String>{

	@Override
	public String convertToDatabaseColumn(BusinessUnitPreview businessUnit) {
		return businessUnit != null ? businessUnit.getBusinessUnitId() : "";
	}

	@Override
	public BusinessUnitPreview convertToEntityAttribute(String businessUnitId) {
		if (businessUnitId.isEmpty()) 
			return null;
		
		BusinessUnitRepository repo = BeanFinder.findBean(BusinessUnitRepository.class);
		BusinessUnit bu = repo.findById(businessUnitId).orElse(null);
		if (bu == null)
			return null;
		
		BusinessUnitPreview bup = BusinessUnitPreview.getPreview(bu);
		EntityLinks el = BeanFinder.findBean(EntityLinks.class);
		bup.add(el.linkForSingleResource(BusinessUnit.class, businessUnitId).withSelfRel());
		
		
		return bup;
	}

}
