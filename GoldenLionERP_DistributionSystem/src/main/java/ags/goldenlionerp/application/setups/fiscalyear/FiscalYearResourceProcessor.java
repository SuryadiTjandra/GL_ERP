package ags.goldenlionerp.application.setups.fiscalyear;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class FiscalYearResourceProcessor implements ResourceProcessor<Resource<FiscalYear>> {

	@Override
	public Resource<FiscalYear> process(Resource<FiscalYear> resource) {
		String selfref = resource.getLink("self").getHref();
		resource.add(new Link(selfref + "/extend", "extend"));
		return resource;
	}

}
