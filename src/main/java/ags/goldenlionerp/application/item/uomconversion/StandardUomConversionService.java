package ags.goldenlionerp.application.item.uomconversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.math.BigDecimal.ONE;

@Service
public class StandardUomConversionService {

	@Autowired private StandardUomConversionRepository repo;
	
	public Optional<BigDecimal> findConversionValue(String from, String to) {
		return findConversionValueInner(from, to, BigDecimal.ONE, Arrays.asList(from));
	}
	
	private Optional<BigDecimal> findConversionValueInner(String from, String to, BigDecimal acc, Collection<String> alreadyTraversed) {
		Optional<BigDecimal> directFind = findDirectConversionValue(from, to);
		if (directFind.isPresent()) {
			BigDecimal result = directFind.get().multiply(acc);
			return Optional.of(result);
		}
		
		Collection<StandardUomConversion> col1 = repo.findByUomFrom(from);
		Collection<StandardUomConversion> col2 = repo.findByUomTo(from).stream()
													.map(StandardUomConversion::getReverseConversion)
													.collect(Collectors.toList());
		Collection<StandardUomConversion> col = Stream.concat(col1.stream(), col2.stream())
													.filter(suc -> !alreadyTraversed.contains(suc.getUomTo()))
													.collect(Collectors.toList());
		
		for (StandardUomConversion conv : col) {
			String nextFrom = conv.getUomTo();
			BigDecimal nextAcc = acc.multiply(conv.getConversionValue());
			
			Collection<String> nextAlrTra = new ArrayList<>();
			nextAlrTra.addAll(alreadyTraversed);
			nextAlrTra.add(nextFrom);

			Optional<BigDecimal> res = findConversionValueInner(nextFrom, to, nextAcc, nextAlrTra);
			if (res.isPresent())
				return res;
		}
		return Optional.empty();
	}
	
	private Optional<BigDecimal> findDirectConversionValue(String from, String to){
		if (from.equals(to))
			return Optional.of(ONE);
		
		Optional<StandardUomConversion> straight = repo.findById(new StandardUomConversionPK(from, to));
		if (straight.isPresent())
			return straight.map(suc -> suc.getConversionValue());
		
		Optional<StandardUomConversion> swapped = repo.findById(new StandardUomConversionPK(to, from));
		if (swapped.isPresent())
			return swapped
					.map(suc -> suc.getReverseConversion())
					.map(suc -> suc.getConversionValue());
		
		return Optional.empty();
	}
	
}
