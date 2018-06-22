package ags.goldenlionerp.masterdata.itembranchinfo;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class ItemBranchInfoIdConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemBranchInfo.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		
		String[] str = id.split("_", 2);
		String buId = str[0];
		String itemId = WebIdUtil.toEntityId(str[1]);
		return new ItemBranchInfoPK(buId, itemId);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ItemBranchInfoPK pk = (ItemBranchInfoPK) id;
		return WebIdUtil.toWebId(pk.toString());
	}

}
