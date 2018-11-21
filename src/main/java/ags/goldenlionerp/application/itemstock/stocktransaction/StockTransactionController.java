package ags.goldenlionerp.application.itemstock.stocktransaction;

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

@RepositoryRestController
@RequestMapping("/stockTransactions/")
public class StockTransactionController {

	@Autowired private StockTransactionRepository repo;
	@Autowired private StockTransactionIdConverter conv;
	
	@GetMapping("/{id}") @ResponseBody
	public Resource<StockTransactionHeader> getItemTransaction(@PathVariable("id") String id){
		StockTransactionPK pk = (StockTransactionPK) conv.fromRequestId(id, StockTransaction.class);
		List<StockTransaction> transList = Lists.newArrayList(
												repo.findAll(StockTransactionPredicates.getInstance().sameHeaderAs(pk))
											);
		if (transList.isEmpty())
			throw new ResourceNotFoundException();
		
		StockTransactionHeader header = new StockTransactionHeader(transList);
		return new Resource<>(header);
	}
}
