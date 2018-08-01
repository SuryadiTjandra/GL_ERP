package ags.goldenlionerp.application.setups.aai;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class AAIIdConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return AutomaticAccountingInstruction.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		return new AutomaticAccountingInstructionPK(
				Integer.parseInt(ids[0]), 
				ids[1]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return id.toString();
	}

}
