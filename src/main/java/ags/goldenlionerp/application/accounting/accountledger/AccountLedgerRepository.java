package ags.goldenlionerp.application.accounting.accountledger;

import ags.goldenlionerp.entities.UndeleteableRepository;
import ags.goldenlionerp.entities.VoidableRepository;

public interface AccountLedgerRepository extends 
	VoidableRepository<AccountLedger, AccountLedgerPK>,
	UndeleteableRepository<AccountLedger, AccountLedgerPK>{

}
