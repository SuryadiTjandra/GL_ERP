package ags.goldenlionerp.masterdata.location;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class LocationIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return LocationMaster.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		if (!id.contains("_")) 
			return new LocationMasterPK("", "");
		
		String[] ids = id.split("_", 2);
		return new LocationMasterPK(ids[0], ids[1]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		LocationMasterPK pk = (LocationMasterPK) id;
		return pk.getBusinessUnitId() + "_" + pk.getLocationId();
	}

}
