package ags.goldenlionerp.application.ap.voucher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ags.goldenlionerp.application.addresses.address.AddressBookMaster;
import ags.goldenlionerp.application.addresses.address.AddressBookRepository;
import ags.goldenlionerp.application.ar.invoice.AlreadyVoidedException;
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
public class PayableVoucherService {
	@Autowired
	private PayableVoucherRepository repo;
	@Autowired private NextNumberService nnServ;
	@Autowired private PaymentTermRepository payRepo;
	@Autowired private CompanyRepository compRepo;
	@Autowired private AutomaticAccountingInstructionRepository aaiRepo;
	@Autowired private AccountMasterRepository accRepo;
	@Autowired private AddressBookRepository addRepo;

	private final static String DOCUMENT_TYPE_VOUCHER = "PV";
	private final static String INSTRUCTION_TYPE = "P";
	
	public PayableVoucher create(PayableVoucher newVoucher) {
		newVoucher = setPrimaryKey(newVoucher);
		newVoucher = setDueDates(newVoucher);
		newVoucher = setAmounts(newVoucher);
		newVoucher = setForeignAmounts(newVoucher);
		newVoucher = setTaxInformation(newVoucher);
		newVoucher = setVendorVoucherInformation(newVoucher);
		newVoucher = setDocumentOrderInformation(newVoucher);
		newVoucher = setOriginalDocumentInformation(newVoucher);
		newVoucher = setAccountInformation(newVoucher);
		newVoucher = setMiscellaneous(newVoucher);
		newVoucher = setVendorInformation(newVoucher);
		return repo.save(newVoucher);
	}
	
	private PayableVoucher setPrimaryKey(PayableVoucher newVoucher) {
		PayableVoucherPK pk = newVoucher.getPk();
		
		int invNo = pk.getVoucherNumber();
		if (invNo == 0) {
			String nextNumber = nnServ.findNextDocumentNumber(pk.getCompanyId(), DOCUMENT_TYPE_VOUCHER, YearMonth.now());
			invNo = Integer.parseInt(nextNumber);
		}
		
		int maxSeq = repo.findByPkCompanyIdAndPkVoucherNumber(pk.getCompanyId(), invNo)
							.stream()
							.mapToInt(ri -> ri.getPk().getVoucherSequence())
							.max().orElse(0);
		
		PayableVoucherPK newPk = new PayableVoucherPK(pk.getCompanyId(), invNo, DOCUMENT_TYPE_VOUCHER, maxSeq + 10);
		newVoucher.setPk(newPk);
		return newVoucher;
	}
	
	private PayableVoucher setDueDates(PayableVoucher newVoucher) {
		PaymentTerm payTerm = payRepo.findById(newVoucher.getPaymentTermCode())
									.orElseThrow(() -> new InvalidDataCodeException("Payment term code " + newVoucher.getPaymentTermCode() + " does not exist"));
		
		LocalDate dueDate = newVoucher.getVoucherDate().plusDays(payTerm.getNetDaysToPay());
		newVoucher.setDueDate(dueDate);
		
		LocalDate discountDueDate = newVoucher.getVoucherDate().plusDays(payTerm.getDiscountDays());
		newVoucher.setDiscountDueDate(discountDueDate);
		
		newVoucher.setGlDate(newVoucher.getVoucherDate());
		return newVoucher;
	}
	
	private PayableVoucher setAmounts(PayableVoucher newVoucher) {
		BigDecimal netAmount = newVoucher.getNetAmount();
		newVoucher.setOpenAmount(netAmount); //voucher is newly created, so open is the same as net amount
		
		PaymentTerm payTerm = payRepo.findById(newVoucher.getPaymentTermCode())
									.orElseThrow(() -> new InvalidDataCodeException("Payment term code " + newVoucher.getPaymentTermCode() + " does not exist"));
		
		BigDecimal discountRate = payTerm.getDiscountPercentage().divide(BigDecimal.valueOf(100));
		BigDecimal discountAmount = netAmount.multiply(discountRate);
		newVoucher.setDiscountAmountAvailable(discountAmount);
		newVoucher.setDiscountAmountTaken(BigDecimal.valueOf(0)); //newly created, so no discount taken yet
		
		BigDecimal taxable = newVoucher.getTaxableAmount();
		newVoucher.setBaseTaxableAmount(netAmount.subtract(taxable));
		
		return newVoucher;
	}
	
	private PayableVoucher setForeignAmounts(PayableVoucher newVoucher) {
		//TODO still not sure what to do
		return newVoucher;
	}
	
	private PayableVoucher setTaxInformation(PayableVoucher newVoucher) {
		//TODO still not sure what to do
		return newVoucher;
	}
	
	private PayableVoucher setVendorVoucherInformation(PayableVoucher newVoucher) {
		//TODO still not sure what to do
		return newVoucher;
	}
	
	private PayableVoucher setDocumentOrderInformation(PayableVoucher newVoucher) {
		//TODO still not sure what to do
		return newVoucher;
	}
	
	private PayableVoucher setOriginalDocumentInformation(PayableVoucher newVoucher) {
		//TODO still not sure what to do
		return newVoucher;
	}
	
	private PayableVoucher setMiscellaneous(PayableVoucher newVoucher) {
		//batch type and number
		newVoucher.setBatchType(INSTRUCTION_TYPE);
		int nextBatchNumber = nnServ.findNextNumber(
				newVoucher.getPk().getCompanyId(), 
				INSTRUCTION_TYPE, 
				YearMonth.now()).getNextSequence();
		newVoucher.setBatchNumber(nextBatchNumber);
		
		//close date
		newVoucher.setClosedDate(null);
		
		//currency
		Company company = compRepo.findById(newVoucher.getPk().getCompanyId())
									.orElseThrow(() -> new ResourceNotFoundException("Company with id " + newVoucher.getPk().getCompanyId() + " does not exist"));
		String baseCurrency = company.getCurrencyCodeBase();
		newVoucher.setBaseCurrency(baseCurrency);
		newVoucher.setDomesticOrForeignTransaction(
				newVoucher.getTransactionCurrency().equals(baseCurrency) ? "D" : "F"	
			);
		
		//misc
		newVoucher.setDocumentStatusCode("A");
		newVoucher.setDocumentVoidStatus("");
		//newVoucher.set
		return newVoucher;
	}
	
	private PayableVoucher setAccountInformation(PayableVoucher newVoucher) {
		//TODO find out without hardcoding how to get the information
		AutomaticAccountingInstruction aai = aaiRepo.findByPkAaiCodeAndCompanyId("PC", newVoucher.getPk().getCompanyId())
													.stream().findFirst()
													.orElseThrow(() -> new ResourceNotFoundException("No AAI with code PC found for company with id " + newVoucher.getPk().getCompanyId()));
		
		newVoucher.setObjectAccountCode(aai.getObjectAccountCode());
		newVoucher.setSubsidiaryCode(aai.getSubsidiaryCode());
		newVoucher.setAccountDescription(aai.getDescription1());
		
		AccountMasterPK accPk = new AccountMasterPK(
				aai.getCompanyId(),
				aai.getBusinessUnitId(), 
				aai.getObjectAccountCode(), 
				aai.getSubsidiaryCode());
		AccountMaster acc = accRepo.findById(accPk).get();
		newVoucher.setAccountId(acc.getAccountId());
		
		return newVoucher;
	}
	
	private PayableVoucher setVendorInformation(PayableVoucher newVoucher) {
		AddressBookMaster vendor = addRepo.findById(newVoucher.getVendorId())
										.orElseThrow(() -> new ResourceNotFoundException("Could not find vendor with ID: " + newVoucher.getVendorId()));
		
		String newDesc = newVoucher.getDescription().isEmpty() ?
							vendor.getName() :
							vendor.getName() + "~" + newVoucher.getDescription();
		newVoucher.setDescription(newDesc);
		
		return newVoucher;
	}
	
	public PayableVoucher voidVoucher(PayableVoucherPK pk) {
		PayableVoucher voucher = repo.findIncludeVoided(pk)
										.orElseThrow(() -> new ResourceNotFoundException("Could not find voucher with number: " + pk.getVoucherNumber()));
		if (voucher.isVoided())
			throw new AlreadyVoidedException("Voucher with number " + pk.getVoucherNumber() + " is already voided!");
		
		voucher.voidDocument();
		return repo.save(voucher);
	}
}
