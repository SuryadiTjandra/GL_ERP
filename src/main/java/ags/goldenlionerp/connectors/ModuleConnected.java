package ags.goldenlionerp.connectors;

import ags.goldenlionerp.entities.DatabaseEntity;

public interface ModuleConnected<T extends DatabaseEntity<?>> {

	public void registerConnector(ModuleConnector<T, ?> connector);
}
