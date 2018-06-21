package ags.goldenlionerp.masterdata.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

	@Autowired
	private LocationRepository repo;
	
	@Transactional
	public void putCollection(String buId, Collection<LocationMaster> newLocations) throws Exception{
		newLocations.forEach(loc -> loc.setBusinessUnitId(buId));
		
		Collection<LocationMaster> oldLocations = repo.findByPk_BusinessUnitId(buId);
		 
		Collection<LocationMaster> newLocToBeInserted = new ArrayList<>();
		Collection<LocationMaster> oldLocToBeDeleted = new ArrayList<>();
		
		Collection<LocationMaster> oldLocToBeUpdated = new ArrayList<>();
		Map<String, LocationMaster> newLocForUpdating = new HashMap<>();
		
		BiPredicate<LocationMaster, LocationMaster> pkEqual =
				(loc1, loc2) -> loc1.getBusinessUnitId().equals(loc2.getBusinessUnitId())
							 && loc1.getLocationId().equals(loc2.getLocationId());
				
		for (LocationMaster oldLoc: oldLocations) {
			boolean found = false;
			for (LocationMaster newLoc: newLocations) {
				if (pkEqual.test(oldLoc, newLoc)) {
					found = true;
					oldLocToBeUpdated.add(oldLoc);
					newLocForUpdating.put(newLoc.toString(), newLoc);
				}
			}
			if (!found) oldLocToBeDeleted.add(oldLoc);
		}
		
		for (LocationMaster newLoc: newLocations) {
			if (!newLocForUpdating.containsKey(newLoc.toString()))
				newLocToBeInserted.add(newLoc);
		}
		
		for (LocationMaster updLoc: oldLocToBeUpdated) {
			LocationMaster newLoc = newLocForUpdating.get(updLoc.toString());

			updLoc.setWarehouseCode(newLoc.getWarehouseCode());
			updLoc.setAisle(newLoc.getAisle());
			updLoc.setRow(newLoc.getRow());
			updLoc.setColumn(newLoc.getColumn());
			updLoc.setDescription(newLoc.getDescription());
		}
		
		
		repo.deleteAll(oldLocToBeDeleted);
		repo.saveAll(newLocToBeInserted);
		repo.saveAll(oldLocToBeUpdated);
	}
}
