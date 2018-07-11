package ags.goldenlionerp.application.system.aai;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="aai")
public interface AutomaticAccountingInstructionRepository
		extends PagingAndSortingRepository<AutomaticAccountingInstruction, AutomaticAccountingInstructionPK> {

}
