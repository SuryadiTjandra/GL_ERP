import Vue from 'vue';
import PurchaseOrderPage from './PurchaseOrderPage';
import BasicWireframe from 'baseComponents/wireframes/BasicWireframe';

var PurchaseOrderApp = {
	props: ['defaultItem'],
	components: { PurchaseOrderPage, BasicWireframe},
	template: `
		<BasicWireframe>
			<h4>Purchase Order</h4>
			<PurchaseOrderPage :default-item="defaultItem" />
		</BasicWireframe>
	`
}

export default PurchaseOrderApp;