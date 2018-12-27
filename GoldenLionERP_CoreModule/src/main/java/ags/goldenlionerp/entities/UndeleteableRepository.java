package ags.goldenlionerp.entities;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@NoRepositoryBean
public interface UndeleteableRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
	
	@Override @RestResource(exported=false)
	default void delete(T entity) {
		throw new UnsupportedOperationException("This type of document must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteById(ID id) {
		throw new UnsupportedOperationException("This type of document must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll() {
		throw new UnsupportedOperationException("This type of document must not be deleted!");
	}
	
	@Override @RestResource(exported=false)
	default void deleteAll(Iterable<? extends T> entities) {
		throw new UnsupportedOperationException("This type of document must not be deleted!");
	}
}
