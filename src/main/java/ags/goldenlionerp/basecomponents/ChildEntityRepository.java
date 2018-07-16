package ags.goldenlionerp.basecomponents;

import java.util.Collection;

import org.springframework.data.rest.core.annotation.RestResource;

public interface ChildEntityRepository<T, PID> {

	@RestResource(exported=false)
	Collection<T> findChildrenByParentId(PID parentId);
}
