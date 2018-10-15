package ags.goldenlionerp.application.item.uomconversion;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class StandardUomConversionIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return StandardUomConversion.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String ids[] = id.split("_");
		return new StandardUomConversionPK(ids[0], ids[1]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		StandardUomConversionPK pk = (StandardUomConversionPK) id;
		return pk.getUomFrom() + "_" + pk.getUomTo();
	}

}
