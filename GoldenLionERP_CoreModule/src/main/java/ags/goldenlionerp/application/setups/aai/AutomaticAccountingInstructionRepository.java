package ags.goldenlionerp.application.setups.aai;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="aai")
public interface AutomaticAccountingInstructionRepository
		extends PagingAndSortingRepository<AutomaticAccountingInstruction, AutomaticAccountingInstructionPK> {

	Collection<AutomaticAccountingInstruction> findByPkAaiCodeAndCompanyId(String aaiCode, String companyId);
}
