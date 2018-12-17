package ags.goldenlionerp.application.datacode;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DataCodeRepository extends CrudRepository<DataCode, DataCodePK> {

	List<DataCode> findByPk_ProductCodeAndPk_SystemCode(String productCode, String systemCode);
}
