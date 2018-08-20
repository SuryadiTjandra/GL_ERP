package ags.goldenlionerp.application.ar.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.setups.aai.AutomaticAccountingInstruction;
import ags.goldenlionerp.application.setups.aai.AutomaticAccountingInstructionRepository;
import ags.goldenlionerp.application.setups.company.Company;
import ags.goldenlionerp.application.setups.company.CompanyRepository;
import ags.goldenlionerp.application.setups.nextnumber.NextNumberService;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTerm;
import ags.goldenlionerp.application.setups.paymentterm.PaymentTermRepository;
import ags.goldenlionerp.exceptions.InvalidDataCodeException;
import ags.goldenlionerp.masterdata.accountmaster.AccountMaster;
import ags.goldenlionerp.masterdata.accountmaster.AccountMasterPK;
import ags.goldenlionerp.masterdata.accountmaster.AccountMasterRepository;

@Service @Transactional
public class ReceivableInvoiceService {

	@Autowired private ReceivableInvoiceRepository repo;
	@Autowired private NextNumberService nnServ;
	@Autowired private PaymentTermRepository payRepo;
	@Autowired private AutomaticAccountingInstructionRepository aaiRepo;
	@Autowired private AccountMasterRepository accRepo;
	@Autowired private AddressBookRepository addRepo;
	@Autowired private CompanyRepository compRepo;
	
	private final static String DOCUMENT_TYPE_INVOICE = "RI";
	private final static String INSTRUCTION_TYPE = "R";
	
	public ReceivableInvoice create(ReceivableInvoice newInvoice) {
		newInvoice = setPrimaryKey(newInvoice);
		newInvoice = setDueDates(newInvoice);
		newInvoice = setAmounts(newInvoice);
		newInvoice = setForeignAmounts(newInvoice);
		newInvoice = setTaxInformation(newInvoice);
		newInvoice = setVendorInvoiceInformation(newInvoice);
		newInvoice = setDocumentOrderInformation(newInvoice);
		newInvoice = setOriginalDocumentInformation(newInvoice);
		newInvoice = setAccountInformation(newInvoice);
		newInvoice = setMiscellaneous(newInvoice);
		newInvoice = setCustomerInformation(newInvoice);
		return repo.save(newInvoice);
	}
	
	private ReceivableInvoice setPrimaryKey(ReceivableInvoice newInvoice) {
		ReceivableInvoicePK pk = newInvoice.getPk();
		
		int invNo = pk.getInvoiceNumber();
		if (invNo == 0) {
			String nextNumber = nnServ.findNextDocumentNumber(pk.getCompanyId(), DOCUMENT_TYPE_INVOICE, YearMonth.now());
			invNo = Integer.parseInt(nextNumber);
		}
		
		int maxSeq = repo.findByPkInvoiceNumber(invNo)
							.stream()
							.mapToInt(ri -> ri.getPk().getInvoiceSequence())
							.max().orElse(0);
		
		ReceivableInvoicePK newPk = new ReceivableInvoicePK(pk.getCompanyId(), invNo, DOCUMENT_TYPE_INVOICE, maxSeq + 10);
		newInvoice.setPk(newPk);
		return newInvoice;
	}
	
	private ReceivableInvoice setDueDates(ReceivableInvoice newInvoice) {
		PaymentTerm payTerm = payRepo.findById(newInvoice.getPaymentTermCode())
									.orElseThrow(() -> new InvalidDataCodeException("Payment term code " + newInvoice.getPaymentTermCode() + " does not exist"));
		
		LocalDate dueDate = newInvoice.getInvoiceDate().plusDays(payTerm.getNetDaysToPay());
		newInvoice.setDueDate(dueDate);
		
		LocalDate discountDueDate = newInvoice.getInvoiceDate().plusDays(payTerm.getDiscountDays());
		newInvoice.setDiscountDueDate(discountDueDate);
		
		newInvoice.setGlDate(newInvoice.getInvoiceDate());
		return newInvoice;
	}
	
	private ReceivableInvoice setAmounts(ReceivableInvoice newInvoice) {
		BigDecimal netAmount = newInvoice.getNetAmount();
		newInvoice.setOpenAmount(netAmount); //invoice is newly created, so open is the same as net amount
		
		PaymentTerm payTerm = payRepo.findById(newInvoice.getPaymentTermCode())
									.orElseThrow(() -> new InvalidDataCodeException("Payment term code " + newInvoice.getPaymentTermCode() + " does not exist"));
		
		BigDecimal discountRate = payTerm.getDiscountPercentage().divide(BigDecimal.valueOf(100));
		BigDecimal discountAmount = netAmount.multiply(discountRate);
		newInvoice.setDiscountAmountAvailable(discountAmount);
		newInvoice.setDiscountAmountTaken(BigDecimal.valueOf(0)); //newly created, so no discount taken yet
		
		BigDecimal taxable = newInvoice.getTaxableAmount();
		newInvoice.setBaseTaxableAmount(netAmount.subtract(taxable));
		
		return newInvoice;
	}
	
	private ReceivableInvoice setForeignAmounts(ReceivableInvoice newInvoice) {
		//TODO still not sure what to do
		return newInvoice;
	}
	
	private ReceivableInvoice setTaxInformation(ReceivableInvoice newInvoice) {
		//TODO still not sure what to do
		return newInvoice;
	}
	
	private ReceivableInvoice setVendorInvoiceInformation(ReceivableInvoice newInvoice) {
		//TODO still not sure what to do
		return newInvoice;
	}
	
	private ReceivableInvoice setDocumentOrderInformation(ReceivableInvoice newInvoice) {
		//TODO still not sure what to do
		return newInvoice;
	}
	
	private ReceivableInvoice setOriginalDocumentInformation(ReceivableInvoice newInvoice) {
		//TODO still not sure what to do
		return newInvoice;
	}
	
	private ReceivableInvoice setMiscellaneous(ReceivableInvoice newInvoice) {
		//batch type and number
		newInvoice.setBatchType(INSTRUCTION_TYPE);
		int nextBatchNumber = nnServ.findNextNumber(
				newInvoice.getPk().getCompanyId(), 
				INSTRUCTION_TYPE, 
				YearMonth.now()).getNextSequence();
		newInvoice.setBatchNumber(nextBatchNumber);
		
		//close date
		newInvoice.setClosedDate(null);
		
		//currency
		Company company = compRepo.findById(newInvoice.getPk().getCompanyId())
									.orElseThrow(() -> new ResourceNotFoundException("Company with id " + newInvoice.getPk().getCompanyId() + " does not exist"));
		String baseCurrency = company.getCurrencyCodeBase();
		newInvoice.setBaseCurrency(baseCurrency);
		newInvoice.setDomesticOrForeignTransaction(
				newInvoice.getTransactionCurrency().equals(baseCurrency) ? "D" : "F"	
			);
		
		//misc
		newInvoice.setDocumentStatusCode("A");
		newInvoice.setDocumentVoidStatus("");
		//newInvoice.set
		return newInvoice;
	}
	
	private ReceivableInvoice setAccountInformation(ReceivableInvoice newInvoice) {
		//TODO find out without hardcoding how to get the information
		AutomaticAccountingInstruction aai = aaiRepo.findByPkAaiCodeAndCompanyId("RC", newInvoice.getPk().getCompanyId())
													.stream().findFirst()
													.orElseThrow(() -> new ResourceNotFoundException("No AAI with code RC found for company with id " + newInvoice.getPk().getCompanyId()));
		
		newInvoice.setObjectAccountCode(aai.getObjectAccountCode());
		newInvoice.setSubsidiaryCode(aai.getSubsidiaryCode());
		newInvoice.setAccountDescription(aai.getDescription1());
		
		AccountMasterPK accPk = new AccountMasterPK(
				aai.getCompanyId(),
				aai.getBusinessUnitId(), 
				aai.getObjectAccountCode(), 
				aai.getSubsidiaryCode());
		AccountMaster acc = accRepo.findById(accPk).get();
		newInvoice.setAccountId(acc.getAccountId());
		
		return newInvoice;
	}
	
	private ReceivableInvoice setCustomerInformation(ReceivableInvoice newInvoice) {
		AddressBookMaster customer = addRepo.findById(newInvoice.getCustomerId())
										.orElseThrow(() -> new ResourceNotFoundException("Could not find customer with ID: " + newInvoice.getCustomerId()));
		
		String newDesc = newInvoice.getDescription().isEmpty() ?
							customer.getName() :
							customer.getName() + "~" + newInvoice.getDescription();
		newInvoice.setDescription(newDesc);
		
		return newInvoice;
	}


	public ReceivableInvoice voidInvoice(ReceivableInvoicePK pk) {
		ReceivableInvoice invoice = repo.findIncludeVoided(pk)
										.orElseThrow(() -> new ResourceNotFoundException("Could not find invoice with number: " + pk.getInvoiceNumber()));
		if (invoice.isVoided())
			throw new AlreadyVoidedException("Invoice with number " + pk.getInvoiceNumber() + " is already voided!");
		
		invoice.voidDocument();
		return repo.save(invoice);
	}
}
