package ags.goldenlionerp.entities;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SynchronizedDatabaseEntity {

	public Optional<LocalDateTime> getLastSynchronizedDate();
}
