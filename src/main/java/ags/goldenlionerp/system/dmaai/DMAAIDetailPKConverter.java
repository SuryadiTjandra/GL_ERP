package ags.goldenlionerp.system.dmaai;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

@Component
public class DMAAIDetailPKConverter implements BackendIdConverter{

	@Override
	public boolean supports(Class<?> delimiter) {
		return DMAAIDetail.class.equals(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		String[] ids = id.split("_", 6);
		
		return new DMAAIDetailPK(Integer.parseInt(ids[0]), ids[1], ids[2], ids[3], ids[4], ids[5]);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return id.toString();
	}

}
