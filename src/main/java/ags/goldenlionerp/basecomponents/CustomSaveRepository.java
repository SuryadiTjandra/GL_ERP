package ags.goldenlionerp.basecomponents;

public interface CustomSaveRepository<T> {
	<S extends T> S save(S entity);
}
