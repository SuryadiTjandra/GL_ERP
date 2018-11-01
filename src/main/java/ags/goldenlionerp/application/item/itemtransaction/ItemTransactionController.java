package ags.goldenlionerp.application.item.itemtransaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import ags.goldenlionerp.entities.DocumentDetailPredicates;

@RepositoryRestController
@RequestMapping("/itemTransactions/")
public class ItemTransactionController {

	@Autowired private ItemTransactionRepository repo;
	@Autowired private ItemTransactionIdConverter conv;
	
	@GetMapping("/{id}") @ResponseBody
	public Resource<ItemTransactionHeader> getItemTransaction(@PathVariable("id") String id){
		ItemTransactionPK pk = (ItemTransactionPK) conv.fromRequestId(id, ItemTransaction.class);
		List<ItemTransaction> transList = Lists.newArrayList(
												repo.findAll(ItemTransactionPredicates.getInstance().sameHeaderAs(pk))
											);
		if (transList.isEmpty())
			throw new ResourceNotFoundException();
		
		ItemTransactionHeader header = new ItemTransactionHeader(transList);
		return new Resource<>(header);
	}
}
