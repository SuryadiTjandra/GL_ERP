package ags.goldenlionerp.application.setups.nextnumber;

import java.time.YearMonth;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.setups.nextnumberconstant.NextNumberConstant;
import ags.goldenlionerp.application.setups.nextnumberconstant.NextNumberConstantRepository;
import ags.goldenlionerp.application.setups.nextnumberconstant.NextNumberResetMethod;

@Service
public class NextNumberService {

	@Autowired
	private NextNumberConstantRepository conRepo;
	@Autowired
	private NextNumberRepository repo;
	
	private static final String DEFAULT_COMPANY = "00000";
	private static final int DEFAULT_YEAR = 9999;
	private static final int DEFAULT_MONTH = 1;
	
	public String findNextDocumentNumber(String companyId, String docBType, YearMonth yearMonth) {
		NextNumberConstant nc = conRepo.findById(docBType)
								.orElse(NextNumberConstant.defaultSetting());
		NextNumber nn = findNextNumber(companyId, docBType, yearMonth);
		
		return toDocumentNumber(nn, nc.getIncludeYearInNextNumber(), nc.getIncludeMonthInNextNumber());
	}
	
	public NextNumber findNextNumber(String companyId, String docBType, YearMonth yearMonth){
		NextNumberConstant nc = conRepo.findById(docBType)
								.orElse(NextNumberConstant.defaultSetting());

		NextNumberPK pk = new NextNumberPK(companyId, docBType, yearMonth.getYear(), yearMonth.getMonthValue());
		return findNextNumber(nc, pk)
				.orElseGet(() -> createNextNumber(nc, companyId, docBType, yearMonth));
	}
	
	public void incrementNextNumber(String companyId, String docBType, YearMonth yearMonth) {
		NextNumber nn = findNextNumber(companyId, docBType, yearMonth);
		incrementNextNumber(nn);
	}
	
	private void incrementNextNumber(NextNumber nn) {
		nn.setNextSequence(nn.getNextSequence() + 1);
		repo.save(nn);
	}

	private Optional<NextNumber> findNextNumber(NextNumberConstant nc, NextNumberPK pk){
		//try with unmodified primary key
		Optional<NextNumber> tryWithPk = repo.findById(pk);
		if (tryWithPk.isPresent())
			return tryWithPk;
		
		//modify primary key according to the nextNumberConstants
		NextNumberPK newPk = constructPk(nc, pk.getCompanyId(), pk.getDocumentOrBatchType(), pk.getYear(), pk.getMonth());
		return repo.findById(newPk);
	}
	
	private NextNumber createNextNumber(NextNumberConstant nc, String companyId, String docBType, YearMonth yearMonth) {
		NextNumberPK pk = constructPk(nc, companyId, docBType, yearMonth.getYear(), yearMonth.getMonthValue());
		
		NextNumber nn = new NextNumber();
		nn.setPk(pk);
		nn.setNextSequence(nc.getResetNumber());
		return repo.save(nn);
	}

	private NextNumberPK constructPk(NextNumberConstant nc, String companyId, String docBType, int year, int month) {
		NextNumberPK pk = null;
		
		switch(NextNumberResetMethod.fromCode(nc.getResetMethod())) {
			case NO_RESET: 
				pk = new NextNumberPK(DEFAULT_COMPANY, docBType, DEFAULT_YEAR, DEFAULT_MONTH);
				break;
			case YEARLY:
				pk = new NextNumberPK(DEFAULT_COMPANY, docBType, year, DEFAULT_MONTH);
				break;
			case MONTHLY:
				pk = new NextNumberPK(DEFAULT_COMPANY, docBType, year, month);
				break;
			case YEARLY_BY_COMPANY:
				pk = new NextNumberPK(companyId, docBType, year, DEFAULT_MONTH);
				break;
			case MONTHLY_BY_COMPANY:
				pk = new NextNumberPK(companyId, docBType, year, month);
				break;
		}
		return pk;
	}
	
	private String toDocumentNumber(NextNumber nn, boolean includeYear, boolean includeMonth) {
		StringBuilder sb = new StringBuilder();
		if (includeYear) {
			sb.append(formatNumber(nn.getPk().getYear(), 2));
		}
		if (includeMonth) {
			sb.append(formatNumber(nn.getPk().getMonth(), 2));
		}
		sb.append(formatNumber(nn.getNextSequence(), 5));
		return sb.toString();
	}
	
	private String formatNumber(int number, int length) {
		String numberString = String.valueOf(number);
		if (numberString.length() == length) {
			return numberString;
		} else if (numberString.length() > length) {
			return numberString.substring(numberString.length() - length, numberString.length());
		} else {
			String zeroPaddings = Stream.generate(() -> "0")
					.limit(length - numberString.length())
					.collect(Collectors.joining());
			return zeroPaddings + numberString;
		}
	}
}
