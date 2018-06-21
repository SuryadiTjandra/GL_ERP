package ags.goldenlionerp.masterdata.itemmaster;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class ItemIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return delimiter.getClass().equals(String.class);
	}

	@Override
	public Serializable fromRequestId(String requestId, Class<?> entityType) {
		return requestId.replaceAll("_", " ");
	}

	@Override
	public String toRequestId(Serializable entityId, Class<?> entityType) {
		return ((String) entityId).replaceAll(" ", "_");
	}

}
