package ags.goldenlionerp.application.item.lotmaster;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class LotMasterIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return LotMaster.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		String[] ids = id.split("_");
		String buId = ids[0];
		String serialLotNo = ids[ids.length - 1];
		String itemCode = id.substring(
								buId.length() + 1, 
								id.lastIndexOf(serialLotNo) - 1);
		itemCode = WebIdUtil.toEntityId(itemCode);
		return new LotMasterPK(buId, itemCode, serialLotNo);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return WebIdUtil.toWebId(((LotMasterPK)id).toString());
	}

}
