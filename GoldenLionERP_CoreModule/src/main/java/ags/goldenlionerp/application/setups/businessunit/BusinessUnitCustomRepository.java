package ags.goldenlionerp.application.setups.businessunit;

public interface BusinessUnitCustomRepository {
	<S extends BusinessUnit> S save(S businessUnit);
}
