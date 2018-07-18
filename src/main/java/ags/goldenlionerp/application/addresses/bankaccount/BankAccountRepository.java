package ags.goldenlionerp.application.addresses.bankaccount;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import ags.goldenlionerp.basecomponents.ChildEntityRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, BankAccountPK>, ChildEntityRepository<BankAccount, String> {

	@Override
	default Collection<BankAccount> findChildrenByParentId(String parentId) {
		return findByPk_AddressNumber(parentId);
	}
	
	Collection<BankAccount> findByPk_AddressNumber(String addressNumber);
}
