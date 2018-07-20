package ags.goldenlionerp.application.system.dmaai;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ags.goldenlionerp.basecomponents.ChildrenEntityRepository;

@RepositoryRestResource(exported=true, collectionResourceRel="dmaaiDetails", path="dmaaiDetails")
public interface DMAAIDetailRepository extends CrudRepository<DMAAIDetail, DMAAIDetailPK>, ChildrenEntityRepository<DMAAIDetail, Integer>{

	@RestResource(exported=false)
	Iterable<DMAAIDetail> findAll();
	
	@RestResource(exported=false)
	<S extends DMAAIDetail> S save(DMAAIDetail entity);
	
	@Override
	default Collection<DMAAIDetail> findChildrenByParentId(Integer parentId) {
		return findByPk_DmaaiNo(parentId);
	}
	
	@RestResource(exported=false)
	Collection<DMAAIDetail> findByPk_DmaaiNo(Integer dmaaiNo);
}
