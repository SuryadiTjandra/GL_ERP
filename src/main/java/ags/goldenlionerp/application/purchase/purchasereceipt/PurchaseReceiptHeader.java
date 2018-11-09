package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.entities.DatabaseAuditable;

class PurchaseReceiptHeader implements DatabaseAuditable{
	
	private List<PurchaseReceipt> details;
	
	public PurchaseReceiptHeader(List<PurchaseReceipt> details) {
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Cannot pass empty list as arguments!");
		//setSequence(details);
		this.details = details;
		this.details.sort(Comparator.comparing(det -> det.getPk().getSequence()));
	}

	@JsonCreator
	public PurchaseReceiptHeader(@JsonProperty("companyId") String companyId, 
			@JsonProperty("purchaseReceiptNumber")int purchaseReceiptNumber, 
			@JsonProperty("purchaseReceiptType")String purchaseReceiptType,
			@JsonProperty("businessUnit")String businessUnitId, 
			@JsonProperty("documentDate")LocalDate documentDate, 
			@JsonProperty("vendorId")String vendorId, 
			@JsonProperty("customerOrderNumber")String customerOrderNumber,
			@JsonProperty("description")String description, 
			@JsonProperty("details")List<PurchaseReceipt> details) {
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Cannot pass empty list as arguments!");
		this.details = details;
		
		setIdInfo(companyId, purchaseReceiptNumber, purchaseReceiptType);

		
		setBusinessUnitId(businessUnitId);
		setDocumentDate(documentDate);
		setVendorId(vendorId);
		setCustomerOrderNumber(customerOrderNumber);
		setDescription(description);

	}
	
	@JsonIgnore
	public int getHighestSequence() {
		return this.details.stream()
						.mapToInt(rec -> rec.getPk().getSequence())
						.max().orElse(0);
	}

	/*private void setSequence(List<PurchaseReceipt> details2) {
		if (details.stream().allMatch(det -> det.getPk().getSequence() != 0)) {
			//if all details already have sequence numbers, then nothing to do
			return;
		} else if (details.stream().allMatch(det -> det.getPk().getSequence() == 0)) {
			//if no details have sequence number 
			for (int i = 0; i < details.size(); i++) {
				PurchaseReceiptPK oldPk = details.get(i).getPk();
				PurchaseReceiptPK pk = new PurchaseReceiptPK(
						oldPk.getCompanyId(), 
						oldPk.getPurchaseReceiptNumber(), 
						oldPk.getPurchaseReceiptType(), 
						(i + 1) * 10);
				details.get(i).setPk(pk);
			}
			
		} else {
			//if mixed, continue sequence numbering from the highest count
			int highestSeq = details.stream().mapToInt(det -> det.getPk().getSequence()).max().getAsInt();
			List<PurchaseReceipt> unsequencedDetails = details.stream()
					.filter(det -> det.getPk().getPurchaseReceiptNumber() == 0)
					.collect(Collectors.toList());
			
			for (int i = 0; i < unsequencedDetails.size(); i++) {
				PurchaseReceiptPK oldPk = unsequencedDetails.get(i).getPk();
				PurchaseReceiptPK pk = new PurchaseReceiptPK(
						oldPk.getCompanyId(), 
						oldPk.getPurchaseReceiptNumber(), 
						oldPk.getPurchaseReceiptType(), 
						highestSeq + (i + 1) * 10);
				unsequencedDetails.get(i).setPk(pk);
			}
		}
		
	}*/

	public String getCompanyId() {
		return details.get(0).getPk().getCompanyId();
	}

	public int getPurchaseReceiptNumber() {
		return details.get(0).getPk().getPurchaseReceiptNumber();
	}

	public String getPurchaseReceiptType() {
		return details.get(0).getPk().getPurchaseReceiptType();
	}

	public String getBusinessUnitId() {
		return details.get(0).getBusinessUnitId();
	}

	public LocalDate getDocumentDate() {
		return details.get(0).getDocumentDate();
	}

	public String getVendorId() {
		return details.get(0).getVendorId();
	}

	public String getCustomerOrderNumber() {
		return details.get(0).getCustomerOrderNumber();
	}

	public String getDescription() {
		return details.get(0).getDescription();
	}
	
	public int getBatchNumber() {
		return details.get(0).getBatchNumber();
	}
	
	public String getBatchType() {
		return details.get(0).getBatchType();
	}

	public List<PurchaseReceipt> getDetails() {
		return details;
	}

	void setBusinessUnitId(String businessUnitId) {
		this.details.forEach(rec -> rec.setBusinessUnitId(businessUnitId));
	}

	void setDocumentDate(LocalDate documentDate) {
		this.details.forEach(rec -> rec.setDocumentDate(documentDate));
	}

	void setVendorId(String vendorId) {
		this.details.forEach(rec -> rec.setVendorId(vendorId));
	}

	void setCustomerOrderNumber(String customerOrderNumber) {
		this.details.forEach(rec -> rec.setCustomerOrderNumber(customerOrderNumber));
	}

	void setDescription(String description) {
		this.details.forEach(rec -> rec.setDescription(description));
	}
	
	void setBatchNumber(int batchNumber) {
		this.details.forEach(rec -> rec.setBatchNumber(batchNumber));
	}
	
	void setBatchType(String batchType) {
		this.details.forEach(rec -> rec.setBatchType(batchType));
	}

	@Override
	public String getInputUserId() {
		return details.get(0).getInputUserId();
	}

	@Override
	public LocalDateTime getInputDateTime() {
		return details.get(0).getInputDateTime();
	}

	@Override
	public String getLastUpdateUserId() {
		return getLastUpdatedDetail().getLastUpdateUserId();
	}

	@Override
	public LocalDateTime getLastUpdateDateTime() {
		return getLastUpdatedDetail().getLastUpdateDateTime();
	}

	@Override
	public String getComputerId() {
		return getLastUpdatedDetail().getComputerId();
	}
	
	private PurchaseReceipt getLastUpdatedDetail() {
		return details.stream()
				.sorted(Comparator.comparing(PurchaseReceipt::getLastUpdateDateTime).reversed())
				.findFirst().get();
	}

	void setIdInfo(String companyId, int purchaseReceiptNumber, String purchaseReceiptType) {
		for (PurchaseReceipt rec: details) {
			int sequence = Optional.ofNullable(rec.getPk())
							.map(pk -> pk.getSequence()).orElse(0);
			PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, purchaseReceiptNumber, purchaseReceiptType, sequence);
			rec.setPk(pk);
		}
	}
}
