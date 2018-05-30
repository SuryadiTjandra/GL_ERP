package ags.goldenlionerp.entities;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.domain.AuditorAware;
import ags.goldenlionerp.util.BeanFinder;

public class DatabaseEntityListener {

	@PrePersist
	void setCreationInfo(DatabaseEntity e) {
		e.setInputUserId(getCurrentUserId());
		e.setInputDateTime(LocalDateTime.now());
		setUpdateInfo(e);
	}
	
	@PreUpdate
	void setUpdateInfo(DatabaseEntity e) {
		e.setLastUpdateUserId(getCurrentUserId());
		e.setLastUpdateDateTime(LocalDateTime.now());
	}
	
	@SuppressWarnings("unchecked")
	private String getCurrentUserId() {
		AuditorAware<String> auditorAware = BeanFinder.findBean(AuditorAware.class);
		return auditorAware.getCurrentAuditor().orElse("");
	}
}
