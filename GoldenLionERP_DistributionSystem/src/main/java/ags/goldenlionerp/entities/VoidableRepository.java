package ags.goldenlionerp.entities;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@NoRepositoryBean
public interface VoidableRepository<T extends Voidable, ID> extends PagingAndSortingRepository<T, ID> {

	@Query("SELECT e FROM #{#entityName} e")
	Page<T> findAllIncludeVoided(Pageable pageable);
	
	@Query("SELECT e FROM #{#entityName} e WHERE e.pk = ?1")
	Optional<T> findIncludeVoided(ID id);
	
	@Override
	@Query("SELECT e FROM #{#entityName} e WHERE e.voided = false")
	Page<T> findAll(Pageable pageable);
	
	@Override @RestResource(exported=false)
	@Query("SELECT e FROM #{#entityName} e WHERE e.voided = false")
	Iterable<T> findAll(Sort sort);
	
	@Override
	@Query("SELECT e FROM #{#entityName} e WHERE e.voided = false AND e.pk = ?1")
	Optional<T> findById(ID id);

	@Override @RestResource(exported=false)
	@Query("SELECT e FROM #{#entityName} e WHERE e.voided = false")
	Iterable<T> findAll();

	@Override
	@Query("SELECT e FROM #{#entityName} e WHERE e.voided = false AND e.pk IN ?1")
	Iterable<T> findAllById(Iterable<ID> ids);
	
	@Override
	@Query("SELECT COUNT(e.pk) FROM #{#entityName} e WHERE e.voided = false")
	long count();
	
	@Override
	default boolean existsById(ID id) {
		return findById(id).isPresent();
	}
}
