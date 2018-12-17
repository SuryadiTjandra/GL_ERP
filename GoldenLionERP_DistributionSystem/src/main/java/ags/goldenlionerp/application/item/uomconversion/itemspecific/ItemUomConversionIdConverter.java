package ags.goldenlionerp.application.item.uomconversion.itemspecific;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component 
public class ItemUomConversionIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemUomConversion.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null)
			return null;
		
		String ids[] = id.split("_");
		return new ItemUomConversionPK(ids[0], ids[1], ids[2]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ItemUomConversionPK pk = (ItemUomConversionPK) id;
		return String.join("_", pk.getItemCode(), pk.getUomFrom(), pk.getUomTo());
	}

}
