package ags.goldenlionerp.application.meta.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserResourceProcessor<U extends UserBase> implements ResourceProcessor<Resource<U>>{

	@Override
	public Resource<U> process(Resource<U> resource) {
		String selfref = resource.getLink("self").getHref();
		resource.add(new Link(selfref + "/password", "password"));
		return resource;
	}

}
