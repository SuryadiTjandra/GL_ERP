package ags.goldenlionerp.application.setups.businessunit;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ags.goldenlionerp.entities.AbstractCustomRepository;
import ags.goldenlionerp.entities.DatabaseEntityUtil;

@Component
public class BusinessUnitCustomRepositoryImpl extends AbstractCustomRepository<BusinessUnit> implements BusinessUnitCustomRepository {

	@Autowired
	JdbcTemplate template;
	
	@Autowired
	EntityManager em;
	
	@Override
	public <S extends BusinessUnit> S save(S businessUnit) {
		if (businessUnit.getRelatedBusinessUnit().isPresent()) {
			if (em.contains(businessUnit)) {
				return em.merge(businessUnit);
			}else {
				em.persist(businessUnit);
				return businessUnit;
			}
		}
		
		return em.contains(businessUnit) ? update(businessUnit) : insert(businessUnit);
	}
	
	@SuppressWarnings("unchecked")
	private <S extends BusinessUnit> S update(S bu) {
		em.detach(bu);
		setUpdateInfo(bu);
		
		String sql = new StringBuilder()
			.append("UPDATE T0021 ")
			.append("SET BNDESB1 = ?, ")
			.append(" BNBUTY = ? ,")
			.append(" BNANUM = ? ,")
			.append(" BNCOID = ? ,")
			.append(" BNBUID1 = ? ,")
			.append(" BNFMOD = ? ,")
			.append(" BNDTLU = ? ,")
			.append(" BNTMLU = ? ,")
			.append(" BNUIDM = ? ,")
			.append(" BNCID = ? ")
			.append(" WHERE BNBUID = ?")
			.toString();

		Query query = em.createNativeQuery(sql)
			.setParameter(1, bu.getDescription())
			.setParameter(2, bu.getBusinessUnitType())
			.setParameter(3, bu.getIdNumber())
			.setParameter(4, bu.getCompany().getCompanyId())
			.setParameter(5, bu.getRelatedBusinessUnit().map(rbu -> rbu.getBusinessUnitId()).orElse(""))
			.setParameter(6, bu.getModelOrConsolidated())
			.setParameter(7, DatabaseEntityUtil.toDate(bu.getLastUpdateDateTime()))
			.setParameter(8, DatabaseEntityUtil.toTimeString(bu.getLastUpdateDateTime()))
			.setParameter(9, bu.getLastUpdateUserId())
			.setParameter(10, bu.getComputerId())
			.setParameter(11, bu.getBusinessUnitId());
		
		query.executeUpdate();
		
		em.flush();
		return (S) em.find(bu.getClass(), bu.getBusinessUnitId());
	}
	
	@SuppressWarnings("unchecked")
	private <S extends BusinessUnit> S insert(S bu) {
		setCreationInfo(bu);
		String tableName = BusinessUnit.class.getAnnotation(Table.class).name();

		String sql = new StringBuilder()
			.append("INSERT INTO ")
			.append(tableName)
			.append(" (BNBUID, BNDESB1, BNBUTY, BNANUM, BNCOID, BNBUID1, BNFMOD, BNCID, BNDTIN, BNTMIN, BNUID, BNDTLU, BNTMLU, BNUIDM) ")
			.append(" VALUES ")
			.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ")
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
			.setParameter(9, DatabaseEntityUtil.toDate(bu.getInputDateTime()))
			.setParameter(10, DatabaseEntityUtil.toTimeString(bu.getInputDateTime()))
			.setParameter(11, bu.getInputUserId())
			.setParameter(12, DatabaseEntityUtil.toDate(bu.getLastUpdateDateTime()))
			.setParameter(13, DatabaseEntityUtil.toTimeString(bu.getLastUpdateDateTime()))
			.setParameter(14, bu.getLastUpdateUserId())
			.executeUpdate();
		
		em.flush();
		
		//BusinessUnit newBu = em.find(bu.getClass(), bu.getBusinessUnitId());
		
		return (S) em.find(bu.getClass(), bu.getBusinessUnitId());
		
	}

}
