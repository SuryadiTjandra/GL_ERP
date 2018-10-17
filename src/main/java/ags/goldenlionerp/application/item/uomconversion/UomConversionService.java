package ags.goldenlionerp.application.item.uomconversion;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ags.goldenlionerp.application.item.uomconversion.standard.StandardUomConversionService;

@Service
public class UomConversionService {
	
	@Autowired private StandardUomConversionService stServ;

	public Optional<BigDecimal> findConversionValueOpt(String itemNumber, String from, String to) {
		//TODO add item-sepcific conversion
		return stServ.findConversionValue(from, to);
	}
	
	public BigDecimal findConversionValue(String itemNumber, String from, String to) {
		return findConversionValueOpt(itemNumber, from, to)
				.orElseThrow(() -> new InvalidUomConversionException(from, to));
	}
}
