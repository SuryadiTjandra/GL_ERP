package ags.goldenlionerp.application.purchase.purchasereceipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReceiptResourceProcessor implements ResourceProcessor<Resource<PurchaseReceipt>> {

	@Override
	public Resource<PurchaseReceipt> process(Resource<PurchaseReceipt> resource) {
		String selfref = resource.getLink("self").getHref();
		resource.add(new Link(selfref + "/sameReceipt", "sameReceipt"));
		return resource;
	}

}
