package ags.goldenlionerp.masterdata.itembranchinfo;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class ItemBranchInfoIdConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return ItemBranchInfo.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		String[] str = id.split("_", 2);
		String buId = str[0];
		String itemId = str[1].replaceAll("_", " ");
		return new ItemBranchInfoPK(buId, itemId);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		ItemBranchInfoPK pk = (ItemBranchInfoPK) id;
		return (pk.getBranchCode() + "_" + pk.getItemCode()).replaceAll(" ", "_");
	}

}
