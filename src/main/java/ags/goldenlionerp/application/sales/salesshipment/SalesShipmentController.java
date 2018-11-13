package ags.goldenlionerp.application.sales.salesshipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@RepositoryRestController
@RequestMapping("/salesShipments/")
public class SalesShipmentController {

	@Autowired private SalesShipmentRepository repo;
	@Autowired private SalesShipmentIdConverter conv;
	
	@GetMapping("/{id}") @ResponseBody
	public Resource<SalesShipmentHeader> getSalesShipment(@PathVariable("id") String id, 
			PersistentEntityResourceAssembler assembler){
		SalesShipmentPK pk = (SalesShipmentPK) conv.fromRequestId(id, SalesShipmentPK.class);
		SalesShipment receipt = repo.findById(pk)
									.orElseThrow(() -> new ResourceNotFoundException());
		
		List<SalesShipment> receipts = Lists.newArrayList(repo.findAll(SalesShipmentPredicates.getInstance().sameHeaderAs(receipt)));
		SalesShipmentHeader header = new SalesShipmentHeader(receipts);
		
		return new SalesShipmentHeaderResource(header, assembler);
	}
}
