package ags.goldenlionerp.masterdata.branchplantconstant;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.masterdata.businessunit.BusinessUnitRepository;

@Service
public class BranchPlantConstantService {
	@Autowired private BusinessUnitRepository buRepo;
	@Autowired private BranchPlantConstantRepository repo;
	
	public void createConfiguration(String id, BranchPlantConstant config) {
		if (repo.existsById(id))
			throw new EntityExistsException("Entity BranchPlantConstant with ID " + id + " already exists");
		
		config.setBranchCode(id);
		repo.save(config);
	}
}
