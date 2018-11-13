package ags.goldenlionerp.application.sales.salesshipment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;

public class SalesShipmentHeaderResource extends Resource<SalesShipmentHeader>{

	List<ResourceSupport> details;
	
	public SalesShipmentHeaderResource(SalesShipmentHeader content, ResourceAssembler<Object, ? extends ResourceSupport> assembler, Link... links) {
		super(content, links);
		List<ResourceSupport> dets = content.getDetails().stream()
										.map(d -> assembler.toResource(d))
										.collect(Collectors.toList());

		details = new ArrayList<>(dets);
		this.add(details.get(0).getId());
	}
		
	public List<ResourceSupport> getDetails(){
		return details;
	}
}
