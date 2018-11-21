package ags.goldenlionerp.application.itemstock.stocktransaction;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockTransactionHeader {

	private List<StockTransaction> details;
	
	@JsonCreator
	public StockTransactionHeader(@JsonProperty("details") List<StockTransaction> details,
			@JsonProperty("companyId") String companyId,
			@JsonProperty("documentNumber") int documentNumber,
			@JsonProperty("documentType") String documentType,
			@JsonProperty("documentDate") LocalDate documentDate,
			@JsonProperty("businessUnitId") String businessUnitId,
			@JsonProperty("glDate") LocalDate glDate,
			@JsonProperty("description") String description) {
		
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Cannot pass empty list as arguments!");
		this.details = details;
		
		for (int i = 0; i < details.size(); i++) {
			StockTransactionPK pk = new StockTransactionPK(companyId, documentNumber, documentType, (i + 1) * 10);
			details.get(i).setPk(pk);
		}
		
		setDocumentDate(documentDate);
		setBusinessUnitId(businessUnitId);
		setGlDate(glDate);
		setDescription(description);
	}
	
	public StockTransactionHeader(List<StockTransaction> details) {
		if (details == null || details.isEmpty())
			throw new IllegalArgumentException("Cannot pass empty list as arguments!");
		this.details = details;
		this.details.sort(Comparator.comparing(det -> det.getPk().getSequence()));
	}
	
	public List<StockTransaction> getDetails(){
		return details;
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
	
	public LocalDate getDocumentDate() {
		return details.get(0).getTransactionDate();
	}
	
	public String getBusinessUnitId() {
		return details.get(0).getBusinessUnitId();
	}
	
	public LocalDate getGlDate() {
		return details.get(0).getGlDate();
	}
	
	public String getDescription() {
		return details.get(0).getDescription();
	}
	
	void setDocumentDate(LocalDate documentDate) {
		details.forEach(d  -> d.setDocumentDate(documentDate));
	}
	
	void setBusinessUnitId(String businessUnitId) {
		details.forEach(d -> d.setBusinessUnitId(businessUnitId));
	}
	
	void setGlDate(LocalDate glDate) {
		details.forEach(d -> d.setGlDate(glDate));
	}
	
	void setDescription(String description) {
		details.forEach(d -> d.setDescription(description));
	}
}
