package ags.goldenlionerp.system.businessunit;

public interface BusinessUnitCustomRepository {
	<S extends BusinessUnit> S save(S businessUnit);
}
