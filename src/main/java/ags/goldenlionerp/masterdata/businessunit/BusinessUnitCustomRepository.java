package ags.goldenlionerp.masterdata.businessunit;

public interface BusinessUnitCustomRepository {
	<S extends BusinessUnit> S save(S businessUnit);
}
