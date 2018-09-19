package ags.goldenlionerp.application.setups.discount;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class DiscountResourceProcessor implements ResourceProcessor<Resource<DiscountMaster>> {

	@Override
	public Resource<DiscountMaster> process(Resource<DiscountMaster> resource) {
		String selfref = resource.getLink("self").getHref();
		resource.add(new Link(selfref + "/calculate", "calculate"));
		return resource;
	}

}
