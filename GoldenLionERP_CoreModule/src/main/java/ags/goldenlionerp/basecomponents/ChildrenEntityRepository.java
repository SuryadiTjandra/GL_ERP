package ags.goldenlionerp.basecomponents;

import java.util.Collection;

import org.springframework.data.rest.core.annotation.RestResource;

public interface ChildrenEntityRepository<T, PID> {

	@RestResource(exported=false)
	Collection<T> findChildrenByParentId(PID parentId);
}
