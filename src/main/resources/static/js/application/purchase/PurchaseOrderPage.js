import CreateButton from "../../baseComponents/buttons/CreateButton.js";
//import VuetablePaginationBootstrap from "../../baseComponents/table/VuetablePaginationBootstrap.js";
import PurchaseOrderTable from "./PurchaseOrderTable.js"

var PurchaseOrderPage = {
	data: function(){
		return {
			apiUrl: "/api/purchaseOrders"
		}
	},
	components:{
		CreateButton,
		PurchaseOrderTable
	},
	template: `
		<b-container fluid>
			<CreateButton @create-click="createButtonClicked">
			</CreateButton>
			
			<PurchaseOrderTable :apiUrl="apiUrl" :loadOnCreate="true">
			</PurchaseOrderTable>
			
		</b-container>
	`,
	methods: {
		createButtonClicked: function(event){
			alert("Create Button cliked!");
		}
		
	}
}

export default PurchaseOrderPage;