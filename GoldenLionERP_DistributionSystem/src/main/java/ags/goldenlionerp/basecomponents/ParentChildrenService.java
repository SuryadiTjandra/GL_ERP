package ags.goldenlionerp.basecomponents;

import static java.util.stream.Collectors.toSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Identifiable;

import ags.goldenlionerp.custompatchers.CustomPatcher;

//P == class of parent, PID == class of parentId, C = class of child, CID = class of child id
public abstract class ParentChildrenService<
	P extends Identifiable<PID>, 
	C extends Identifiable<CID>, 
	PID extends Serializable, 
	CID extends Serializable
> extends BaseService<P, PID>{

	@Autowired
	protected CustomPatcher patcher;
	
	private Object parentRepo;
	private Object childRepo;
	
	protected <PREPO extends CrudRepository<P, PID>, 
			   CREPO extends CrudRepository<C, CID> & ChildrenEntityRepository<C, PID>>
		ParentChildrenService(PREPO parentRepo, CREPO childRepo){
		this.parentRepo = parentRepo;
		this.childRepo = childRepo;
	}
	
	@SuppressWarnings("unchecked")
	protected Collection<C> saveChildrenToParent(
			PID  parentId, 
			Collection<Map<String, Object>> childPatchRequests) {
		
		CrudRepository<P,PID> parentRepo = (CrudRepository<P, PID>) this.parentRepo;
		CrudRepository<C,CID> childRepo = (CrudRepository<C, CID>) this.childRepo;
		
		//check if parent exist 
		P parent = parentRepo.findById(parentId)
					.orElseThrow(
						() -> new ResourceNotFoundException("Cannot find the parent with id " + parentId)
					);
		
		//set the child requests' id to match the parent
		childPatchRequests.forEach(request -> setIdToMatchParent(parentId, request));
		
		//separate the children into new and existing
		Map<Boolean, Collection<Map<String, Object>>> map = separateRequestsIntoNewAndExisting(getChildrenOf(parent), childPatchRequests);
		Collection<Map<String, Object>> news = map.get(true);
		Collection<Map<String, Object>> existings = map.get(false);
		
		//the new ones can be immediately saved
		Iterable<C> created = news.stream().map(m -> constructChild(m)).collect(toSet());
		created = childRepo.saveAll(created);
		
		//patch the existing ones
		Collection<C> updated = new HashSet<>();
		for (Map<String, Object> existing: existings) {
			C current = getChildrenOf(parent).stream()
									.filter(cur -> cur.getId().equals(constructChildId(existing)))
									.findFirst().get();
			updated.add(patchChild(current, existing));
		}
		Iterable<C> updateds = childRepo.saveAll(updated);
		
		//return both created and updated
		Collection<C> res = new HashSet<>();
		for (C c: created)
			res.add(c);
		for (C u: updateds)
			res.add(u);
		return res;
	}
	
	protected Map<Boolean, Collection<Map<String, Object>>> separateRequestsIntoNewAndExisting(Collection<C> existingChildren, Collection<Map<String, Object>> patchRequests){
		Collection<CID> currentIds = existingChildren.stream().map(ch -> ch.getId()).collect(toSet());
		
		Set<Map<String, Object>> newRequest = patchRequests.stream()
				.filter(request -> !currentIds.contains(constructChildId(request)))
				.collect(toSet());
		Set<Map<String, Object>> existingRequest = patchRequests.stream()
						.filter(request -> currentIds.contains(constructChildId(request)))
						.collect(toSet());
		Map<Boolean, Collection<Map<String, Object>>> map = new HashMap<>();
		map.put(true, newRequest);
		map.put(false, existingRequest);
		return map;
	}
	
	protected C patchChild(C child, Map<String, Object> patchRequest) {
		return patcher.patch(child, patchRequest);
	}
	
	protected abstract void setIdToMatchParent(PID parentId, Map<String, Object> childRequest);
	
	protected Collection<C> getChildrenOf(P parent){
		@SuppressWarnings("unchecked")
		ChildrenEntityRepository<C, PID> crepo = (ChildrenEntityRepository<C, PID>) childRepo;
		return crepo.findChildrenByParentId(parent.getId());
	}
	protected abstract CID constructChildId(Map<String, Object> childRequest);
	protected abstract C constructChild(Map<String, Object> childRequest);
	
}
