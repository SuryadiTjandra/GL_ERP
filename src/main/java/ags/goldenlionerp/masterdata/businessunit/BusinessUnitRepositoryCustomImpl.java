package ags.goldenlionerp.masterdata.businessunit;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitRepositoryCustomImpl implements BusinessUnitRepositoryCustom{

	@Autowired
	JdbcTemplate template;
	
	@Autowired
	EntityManager em;
	
	@Override
	public <S extends BusinessUnit> S save(S businessUnit) {
		if (businessUnit.getRelatedBusinessUnit().isPresent()) {
			return em.merge(businessUnit);
		}
		
		return em.contains(businessUnit) ? update(businessUnit) : insert(businessUnit);
	}
	
	private <S extends BusinessUnit> S update(S bu) {
		/*
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ")
			.append(tableName)
			.append("SET ")
			.append(str)*/
		return bu;
	}
	
	private <S extends BusinessUnit> S insert(S bu) {
		
		em.persist(bu);
		BusinessUnit newBu = em.find(BusinessUnit.class, bu.getBusinessUnitId());
		BusinessUnit nullBu = new BusinessUnit();
		nullBu.setBusinessUnitId("");
		em.createQuery("UPDATE BusinessUnit SET relatedBusinessUnit = :nullBu WHERE businessUnitId = :buId")
			.setParameter("nullBu", nullBu)
			.setParameter("buId", bu.getBusinessUnitId())
			.executeUpdate();
		
		return (S) em.find(BusinessUnit.class, bu.getBusinessUnitId());
		
		/*String tableName = BusinessUnit.class.getAnnotation(Table.class).name();
		
		String sql = new StringBuilder()
			.append("INSERT INTO ")
			.append(tableName)
			.append(" (BNBUID, BNDESB1, BNBUTY, BNANUM, BNCOID, BNBUID1, BNFMOD, BNCID, BNDTIN, BNTMIN, ) ")
			.append(" VALUES ")
			.append(" (?, ?, ?, ?, ?, ?, ?, ?) ")
			.toString();
		
		em.createNativeQuery(sql)
			.setParameter(1, bu.getBusinessUnitId())
			.setParameter(2, bu.getDescription())
			.setParameter(3, bu.getBusinessUnitType())
			.setParameter(4, bu.getIdNumber())
			.setParameter(5, bu.getCompany().getCompanyId())
			.setParameter(6, bu.getRelatedBusinessUnit().map(rbu -> rbu.getBusinessUnitId()).orElse(""))
			.setParameter(7, bu.getModelOrConsolidated())
			.setParameter(8, bu.getComputerId())
			.executeUpdate();
		
		em.flush();
		
		BusinessUnit newBu = em.find(bu.getClass(), bu.getBusinessUnitId());
		
		return bu;*/
		
	}

}
