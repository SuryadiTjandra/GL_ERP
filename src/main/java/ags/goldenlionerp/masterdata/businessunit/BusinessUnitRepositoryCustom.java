package ags.goldenlionerp.masterdata.businessunit;

public interface BusinessUnitRepositoryCustom {
	<S extends BusinessUnit> S save(S businessUnit);
}
