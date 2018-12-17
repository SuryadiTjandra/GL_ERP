package ags.goldenlionerp.application.system.user;

import java.io.Serializable;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.util.WebIdUtil;

@Component
public class UserIdConverter implements BackendIdConverter {

	@Override
	public boolean supports(Class<?> delimiter) {
		return UserBase.class.isAssignableFrom(delimiter);
	}

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		return WebIdUtil.toEntityId(id);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return WebIdUtil.toWebId((String) id);
	}

}
