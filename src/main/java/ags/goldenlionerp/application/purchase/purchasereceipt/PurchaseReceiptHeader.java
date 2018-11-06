package ags.goldenlionerp.application.purchase.purchasereceipt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.entities.DatabaseAuditable;

class PurchaseReceiptHeader implements DatabaseAuditable{
	
	private List<PurchaseReceipt> details;
	
	public PurchaseReceiptHeader(List<PurchaseReceipt> details) {
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Cannot pass empty list as arguments!");
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
		
		for (int i = 0; i < details.size(); i++) {
			PurchaseReceiptPK pk = new PurchaseReceiptPK(companyId, purchaseReceiptNumber, purchaseReceiptType, (i + 1) * 10);
			details.get(i).setPk(pk);
		}
		
		setBusinessUnitId(businessUnitId);
		setDocumentDate(documentDate);
		setVendorId(vendorId);
		setCustomerOrderNumber(customerOrderNumber);
		setDescription(description);

	}

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
				.sorted(Comparator.comparing(PurchaseReceipt::getLastUpdateUserId))
				.findFirst().get();
	}
}
