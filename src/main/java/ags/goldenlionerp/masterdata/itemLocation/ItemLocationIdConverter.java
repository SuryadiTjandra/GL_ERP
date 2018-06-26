package ags.goldenlionerp.masterdata.itemLocation;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class ItemLocationIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemLocation.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] ids = id.split("_");
		if (id.endsWith("_")) {
			ids = Arrays.copyOf(ids, ids.length + 1);
			ids[ids.length-1] = "";
		}
		
		String serialLotNo = ids[ids.length - 1];
		String locationId = ids[ids.length - 2];
		String businessUnitId = ids[ids.length - 3];
		String itemCode = id.substring(0, id.lastIndexOf(businessUnitId)-1);
		itemCode = WebIdUtil.toEntityId(itemCode);
		return new ItemLocationPK(itemCode, businessUnitId, locationId, serialLotNo);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return WebIdUtil.toWebId(((ItemLocationPK) id).toString());
	}

}
