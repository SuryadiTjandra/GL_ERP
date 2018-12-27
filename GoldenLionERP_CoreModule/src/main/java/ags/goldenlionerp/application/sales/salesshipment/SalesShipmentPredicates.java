package ags.goldenlionerp.application.sales.salesshipment;

import com.querydsl.core.types.Path;

import ags.goldenlionerp.documents.DocumentDetailPredicates;

public class SalesShipmentPredicates extends DocumentDetailPredicates<SalesShipment, SalesShipmentPK> {

	private static SalesShipmentPredicates instance;
	
	private SalesShipmentPredicates(Path<SalesShipment> qPath) {
		super(qPath);
	}
	
	public static SalesShipmentPredicates getInstance() {
		if (instance == null)
			instance = new SalesShipmentPredicates(QSalesShipment.salesShipment);
		return instance;
	}

}
