package ags.goldenlionerp.masterdata.itemmaster;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class ItemIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemMaster.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String requestId, Class<?> entityType) {
		String res = WebIdUtil.toEntityId(requestId);
		return res;
	}

	@Override
	public String toRequestId(Serializable entityId, Class<?> entityType) {
		String res = WebIdUtil.toWebId((String) entityId);
		return res;
	}

}
