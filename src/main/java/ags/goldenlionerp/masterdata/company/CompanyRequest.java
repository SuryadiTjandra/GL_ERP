package ags.goldenlionerp.masterdata.company;

class CompanyRequest {
	 String companyId;
	 String description = "";
	 String currencyCodeBase = "";
	 String businessPartnerId = "";
	 String fiscalDatePattern = "";
	 int currentFiscalYear = 0;
	 int currentAccountingPeriod = 0;
	 int currentPayablePeriod = 0;		//hutang
	 int currentReceivablePeriod = 0;	//piutang
	 int currentInventoryPeriod = 0;
}
