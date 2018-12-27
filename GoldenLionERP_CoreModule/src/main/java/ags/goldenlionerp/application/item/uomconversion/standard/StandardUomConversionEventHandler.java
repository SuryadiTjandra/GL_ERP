package ags.goldenlionerp.application.item.uomconversion.standard;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.exceptions.ResourceAlreadyExistsException;

@RepositoryEventHandler(StandardUomConversion.class)
@Component
public class StandardUomConversionEventHandler {

	@Autowired private StandardUomConversionService service;
	
	@HandleBeforeCreate
	public void checkIfAlreadyExist(StandardUomConversion uomConv) {
		Optional<BigDecimal> existing = service.findConversionValue(uomConv.getUomFrom(), uomConv.getUomTo());
		if (existing.isPresent()) {
			double dblExistingVal = existing.get().doubleValue();
			double requestVal = uomConv.getConversionValue().doubleValue();
			if (Math.abs(dblExistingVal - requestVal) > 0.001)
				throw new ResourceAlreadyExistsException("Standard unit of measure conversion", uomConv.getUomFrom() + "->" + uomConv.getUomTo());
		}
	}
}
