package ags.goldenlionerp.application.item.lotmaster;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ags.goldenlionerp.documents.DocumentId;

@Service @Transactional
public class LotMasterService {

	@Autowired
	private LotMasterRepository repo;
	
	public Iterable<LotMaster> createLotsWithSerialNumbers(String businessUnitId, String itemCode, Collection<String> serialNumbers, @Nullable DocumentId creationDocument){
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

	public void deleteLotsWithSerialNumbers(String businessUnitId, String itemCode, Collection<String> serialOrLotNumbers) {
		for (String serialNo: serialOrLotNumbers) {
			LotMasterPK pk = new LotMasterPK(businessUnitId, itemCode, serialNo);
			repo.deleteById(pk);
		}
		
	}

	public Iterable<LotMaster> markLotsAsUsed(String businessUnitId, String itemCode, Collection<String> serialOrLotNumbers) {
		Collection<LotMasterPK> pks = serialOrLotNumbers.stream()
										.map(sn -> new LotMasterPK(businessUnitId, itemCode, sn))
										.collect(Collectors.toSet());
		Iterable<LotMaster> lots = repo.findAllById(pks);
		for (LotMaster lot: lots) {
			lot.setLotStatusCode("S");
		}
		return repo.saveAll(lots);
	}

	public Iterable<LotMaster> unmarkLotsAsUsed(String businessUnitId, String itemCode, Collection<String> serialOrLotNumbers) {
		Collection<LotMasterPK> pks = serialOrLotNumbers.stream()
				.map(sn -> new LotMasterPK(businessUnitId, itemCode, sn))
				.collect(Collectors.toSet());
		Iterable<LotMaster> lots = repo.findAllById(pks);
		for (LotMaster lot: lots) {
			lot.setLotStatusCode("");
		}
		return repo.saveAll(lots);
	}
	
}
