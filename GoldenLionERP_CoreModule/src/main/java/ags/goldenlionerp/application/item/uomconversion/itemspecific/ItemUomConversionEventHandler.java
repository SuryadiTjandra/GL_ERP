package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.application.item.itemmaster.ItemMaster;
import ags.goldenlionerp.application.item.itemmaster.ItemMasterRepository;
import ags.goldenlionerp.application.item.uomconversion.UomConversionService;

@Component
@RepositoryEventHandler(ItemUomConversion.class)
public class ItemUomConversionEventHandler {

	@Autowired
	private UomConversionService convServ;
	@Autowired
	private ItemMasterRepository itemRepo;
	
	@HandleBeforeSave @HandleBeforeCreate
	public void fillInfoBeforeSave(ItemUomConversion uomConv) {
		ItemMaster item = itemRepo.findById(uomConv.getItemCode())
									.orElseThrow(() -> new ResourceNotFoundException("Could not find item with code " + uomConv.getItemCode()));
		String primaryUom = item.getUnitsOfMeasure().getPrimaryUnitOfMeasure();
		
		if (uomConv.getUomFrom().equals(primaryUom))
			uomConv.setConversionValueToPrimary(BigDecimal.ONE);
		else if (uomConv.getUomTo().equals(primaryUom))
			uomConv.setConversionValueToPrimary(uomConv.getConversionValue());
		else {
			BigDecimal uomToToPrimary = convServ.findConversionValue(uomConv.getItemCode(), uomConv.getUomFrom(), uomConv.getUomTo());
			uomConv.setConversionValueToPrimary(uomConv.getConversionValue().multiply(uomToToPrimary));
		}
	}
}
