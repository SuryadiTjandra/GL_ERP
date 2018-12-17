package ags.goldenlionerp.application.sales.salesshipment;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@RepositoryRestController
@RequestMapping("/salesShipments/")
public class SalesShipmentController {

	@Autowired private SalesShipmentRepository repo;
	@Autowired private SalesShipmentService serv;
	@Autowired private SalesShipmentIdConverter conv;
	
	private SalesShipmentPredicates pred = SalesShipmentPredicates.getInstance();
	
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
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody SalesShipmentHeader shipmentRequest, PersistentEntityResourceAssembler assembler){
		if (repo.exists(pred.sameHeaderAs(shipmentRequest.getDetails().get(0))))
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		SalesShipmentHeader shipment = serv.createSalesShipments(shipmentRequest);
		SalesShipmentHeaderResource shipmentResource = new SalesShipmentHeaderResource(shipment, assembler);
		URI resUri = URI.create(shipmentResource.getId().getHref());
		
		return ResponseEntity.created(resUri).body(shipmentResource);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(){
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> patch(@PathVariable("id") String id, 
			@RequestBody SalesShipmentHeader patchRequest,
			PersistentEntityResourceAssembler assembler){
		SalesShipmentPK pk = (SalesShipmentPK) conv.fromRequestId(id, SalesShipment.class);
		if (!repo.existsById(pk))
			throw new ResourceNotFoundException();
		
		patchRequest.setIdInfo(pk.getCompanyId(), pk.getDocumentNumber(), pk.getDocumentType());
		
		SalesShipmentHeader shipment = serv.updateSalesShipment(pk, patchRequest);
		SalesShipmentHeaderResource shipmentResource = new SalesShipmentHeaderResource(shipment, assembler);
		return ResponseEntity.ok(shipmentResource);
	}
}
