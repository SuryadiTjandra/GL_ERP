package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;

class PurchaseReceiptHeaderResource extends Resource<PurchaseReceiptHeader>{

	private List<ResourceSupport> details;

	public PurchaseReceiptHeaderResource(PurchaseReceiptHeader content, ResourceAssembler<Object, ? extends ResourceSupport> detailAssembler, Link... links) {
		super(content, links);
		List<ResourceSupport> dets = content.getDetails().stream()
									.map(d -> detailAssembler.toResource(d))
									.collect(Collectors.toList());
		
		details = new ArrayList<>(dets);
		this.add(details.get(0).getId());
	}
	
	public List<ResourceSupport> getDetails(){
		return details;
	}

}
