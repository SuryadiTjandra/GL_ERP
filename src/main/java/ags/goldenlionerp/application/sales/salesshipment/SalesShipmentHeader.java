package ags.goldenlionerp.application.sales.salesshipment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ags.goldenlionerp.entities.DatabaseAuditable;

public class SalesShipmentHeader implements DatabaseAuditable {

	private List<SalesShipment> details;
	
	public SalesShipmentHeader(List<SalesShipment> details) {
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Details must not be empty");
		this.details = details;
	}
	
	@JsonCreator
	public SalesShipmentHeader(@JsonProperty("companyId") String companyId,
			@JsonProperty("documentNumber")int documentNumber,
			@JsonProperty("documentType")String documentType,
			@JsonProperty("businessUnitId")String businessUnitId,
			@JsonProperty("documentDate")LocalDate documentDate,
			@JsonProperty("customerId")String customerId,
			@JsonProperty("receiverId")String receiverId,
			@JsonProperty("description")String description,
			@JsonProperty("details") List<SalesShipment> details) {
		
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Details must not be empty");
		
		this.details = details;
		setIdInfo(companyId, documentNumber, documentType);
		setBusinessUnitId(businessUnitId);
		setDocumentDate(Optional.ofNullable(documentDate).orElse(LocalDate.now()));
		setCustomerId(customerId);
		setReceiverId(ObjectUtils.isEmpty(receiverId) ? customerId : receiverId);
		setDescription(description);
	}
	
	public String getCompanyId() {
		return details.get(0).getPk().getCompanyId();
	}
	
	public int getDocumentNumber() {
		return details.get(0).getPk().getDocumentNumber();
	}

	
	public String getDocumentType() {
		return details.get(0).getPk().getDocumentType();
	}

	
	public String getBusinessUnitId() {
		return details.get(0).getBusinessUnitId();
	}

	public LocalDate getDocumentDate() {
		return details.get(0).getTransactionDate();
	}
	
	public String getCustomerId() {
		return details.get(0).getCustomerId();
	}
	
	public String getReceiverId() {
		return details.get(0).getReceiverId();
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
	
	void setIdInfo(String companyId, int documentNumber, String documentType) {
		for (SalesShipment detail: details) {
			int seq = Optional.ofNullable(detail.getPk())
							.map(SalesShipmentPK::getSequence)
							.orElse(0);
			SalesShipmentPK pk = new SalesShipmentPK(companyId, documentNumber, documentType, seq);
			detail.setPk(pk);
		}
	}
	
	void setBusinessUnitId(String businessUnitId) {
		details.forEach(det -> det.setBusinessUnitId(businessUnitId));
	}
	
	void setDocumentDate(LocalDate documnetDate) {
		details.forEach(det -> det.setDocumentDate(documnetDate));
	}
	
	void setCustomerId(String customerId) {
		details.forEach(det -> det.setCustomerId(customerId));
	}
	
	void setReceiverId(String receiverId) {
		details.forEach(det -> det.setReceiverId(receiverId));
	}
	
	void setDescription(String description) {
		details.forEach(det -> det.setDescription(description));
	}
	
	void setBatchNumber(int batchNumber) {
		details.forEach(det -> det.setBatchNumber(batchNumber));
	}
	
	void setBatchType(String batchtype) {
		details.forEach(det -> det.setBatchType(batchtype));
	}

	public List<SalesShipment> getDetails() {
		return details;
	}

	void setDetails(List<SalesShipment> details) {
		this.details = details;
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
	
	private SalesShipment getLastUpdatedDetail() {
		return details.stream()
				.sorted(Comparator.comparing(SalesShipment::getLastUpdateDateTime).reversed())
				.findFirst().get();
	}
	
	
}
