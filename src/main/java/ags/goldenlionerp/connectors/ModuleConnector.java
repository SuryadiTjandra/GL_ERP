package ags.goldenlionerp.connectors;

import java.util.Collection;

import ags.goldenlionerp.entities.DatabaseEntity;

public interface ModuleConnector<T1 extends DatabaseEntity<?>, T2 extends DatabaseEntity<?>> {

	public void handleCreated(T1 entity);
	
	public void handleUpdated(T1 entity);
	
	public void handleDeleted(T1 entity);
	
	default void handleCreated(Collection<T1> entities) {
		entities.forEach(this::handleCreated);
	}
	
	default void handleUpdated(Collection<T1> entities) {
		entities.forEach(this::handleUpdated);
	}
	
	default void handleDeleted(Collection<T1> entities) {
		entities.forEach(this::handleDeleted);
	}
}
