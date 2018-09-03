package ags.goldenlionerp.application.setups.taxcode;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface TaxRuleRepository extends CrudRepository<TaxRule, TaxRulePK> {

	default Optional<TaxRule> findActiveTaxRule(String taxCode){
		return findActiveTaxRuleAt(taxCode, LocalDate.now());
	}
	
	
	default Optional<TaxRule> findActiveTaxRuleAt(String taxCode, LocalDate date){
		Pageable pageable =  PageRequest.of(0, 1, Direction.ASC, "pk.effectiveDate");
		List<TaxRule> ruleList = findPossibleActiveTaxRules(taxCode, date, pageable);
		
		Comparator<TaxRule> compareEfDate = Comparator.comparing(
				tax -> tax.getPk().getEffectiveDate()
			);
		return ruleList.stream().max(compareEfDate);
	}
	
	/**
	 * Not supposed to be called from any code except from inside findActiveTaxRuleAt method
	 */
	@RestResource(exported=false)
	@Query("SELECT tax FROM TaxRule tax WHERE tax.pk.taxCode = ?1 AND tax.pk.effectiveDate <= ?2 AND tax.expiredDate > ?2")
	List<TaxRule> findPossibleActiveTaxRules(String taxCode, LocalDate date, Pageable pageable);
}
