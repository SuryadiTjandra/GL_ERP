package ags.goldenlionerp.masterdata.lotmaster;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.entities.DocumentId;

@Service
public class LotMasterService {

	@Autowired
	private LotMasterRepository repo;
	
	public Iterable<LotMaster> createLotsWithSerialNumbers(String businessUnitId, String itemCode, Set<String> serialNumbers, @Nullable DocumentId creationDocument){
		Collection<LotMaster> lots = new ArrayList<>();
		for (String serialNo: serialNumbers) {
			LotMasterPK pk = new LotMasterPK(businessUnitId, itemCode, serialNo);
			LotMaster lot = new LotMaster();
			lot.setPk(pk);
			lot.setSerialNumber(serialNo);
			lot.setOnHandDate(LocalDateTime.now());
			if (creationDocument != null) {
				String desc = creationDocument.getDocumentType() + ": " + creationDocument.getDocumentNumber();
				lot.setLotDescription(desc);
			}
			lots.add(lot);
		}
		return repo.saveAll(lots);
	}
	
}
